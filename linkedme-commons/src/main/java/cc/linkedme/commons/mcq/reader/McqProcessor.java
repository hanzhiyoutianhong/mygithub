package com.wrshine.commons.mcq.reader;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.wrshine.commons.log.ApiLogger;
import com.wrshine.commons.log.StatLog;
import com.wrshine.commons.mcq.McqBaseManager;
import com.wrshine.commons.mcq.ReaderMcqClientList;
import com.wrshine.commons.mcq.McqClientList.ClientStatus;
import com.wrshine.commons.mcq.forward.MsgForwarder;
import com.wrshine.commons.memcache.MemCacheStorage;
import com.wrshine.commons.memcache.VikaCacheClient;
import com.wrshine.commons.profile.ProfileType;
import com.wrshine.commons.profile.ProfileUtil;
import com.wrshine.commons.util.McqUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * mcq 处理mcq，为每个mcq建立独立线程进行读取。写mcq，随机选择mcq写入，如果写失败，轮询下一个，直到尝试完所有的mcq
 */
public abstract class McqProcessor implements StartReadingAble {
    private static Logger logger = Logger.getLogger(McqProcessor.class);
    private ReaderMcqClientList readerMcqClientList = null;
    private Map<String, Boolean> stopClient = new ConcurrentHashMap<String, Boolean>();


    private static final String OPENAPI_MCQ_WRITE_KEY = "openapi_bj";

    protected Map<String, List<VikaCacheClient>> mcqReaders;
    protected List<VikaCacheClient> mcqWriters;

    // 读取的线程数
    protected int readThreadCountEachMcq = 3;

    // 连续读取的数量
    protected int readCountOnce = 100;

    // 连续读取若干数量 or 没有读取到后的等待时间间隔
    protected int waitTimeOnce = 100;

    protected List<Thread> readThreads = new ArrayList<Thread>();

    protected List<String> apiWriteKeys = null;
    protected String readKey = null;

    // private List<VikaCacheClient> mqBjWriters;

    /**
     * 消息分发
     */
    protected MsgForwarder beforeMsgForwarder;
    protected MsgForwarder afterMsgForwarder;


    protected Random random = new Random();

    public void startReading() {
        // tell MemCacheStorage that we are in processor
        MemCacheStorage.isInProcessor = true;

        for (List<VikaCacheClient> mcqrs : mcqReaders.values()) {
            for (VikaCacheClient mcqr : mcqrs) {
                int i = 0;
                while (i++ < readThreadCountEachMcq) {
                    Thread t = createReadThread(mcqr);
                    t.start();
                    readThreads.add(t);
                }
            }
        }

        startExtWork();
    }


    protected void writeMsgForApi(String msg) {
        if (ApiLogger.isDebugEnabled()) {
            ApiLogger.debug(new StringBuilder(128).append(getRawWriteKey()).append(" will write:").append(msg));
        }

        for (String key : this.apiWriteKeys) {
            writeMsg(key, msg);
        }
    }

    protected void writeMsgForBJ(String msg) {
        writeMsg(OPENAPI_MCQ_WRITE_KEY, msg);
    }

    protected void writeMsg(String msg) {
        ApiLogger.info(new StringBuilder(128).append(getRawWriteKey()).append(" will write:").append(msg));

        writeMsg(getRawWriteKey(), msg);
    }

    protected void writeMsg(String key, String msg) {
        writeMsg(mcqWriters, key, msg);
    }

    protected void writeMsgbak(List<VikaCacheClient> writers, String key, Object msg) {


        if (writers == null || writers.size() == 0) {
            return;
        }

        int rd = random.nextInt(writers.size());
        /*
         * 1、对每条消息轮询所有的mcq，如果处理成功则直接返回。 2、如果处理失败，则尝试写入下一个mcq。 3、如果所有的mcq均写入失败，则不做处理。
         */
        boolean writeRs = false;
        for (int i = 0; i < writers.size(); i++) {
            int index = (i + rd) % writers.size();
            VikaCacheClient mqWriter = writers.get(index);

            try {
                if (mqWriter.set(key, msg)) {
                    writeRs = true;
                    StatLog.inc(getMQWriteKey(mqWriter.getServerPort(), key));
                    ApiLogger.info(new StringBuilder(256).append("mcq=").append(mqWriter.getServerPort()).append(", key=").append(key)
                            .append(", mq=").append(msg));
                    break;
                }
            } catch (Exception e) {
                ApiLogger.warn(new StringBuilder(128).append("Warn: save msg to one mq false [try next], key=").append(key).append(", mq=")
                        .append(mqWriter.getServerPort()).append(",msg").append(msg), e);
            }
            StatLog.inc(getMQWriteErrorKey(mqWriter.getServerPort(), key));
            ApiLogger.info(new StringBuilder(128).append("Info: save msg to mq false, key=").append(key).append(",mq=")
                    .append(mqWriter.getServerPort()).append(",msg").append(msg));
        }
        if (!writeRs) {
            ApiLogger.error(new StringBuilder(128).append("Write mcq false, key=").append(key).append(", msg=").append(msg));
            throw new IllegalArgumentException(new StringBuilder(128).append("Write mcq false, key=").append(key).append(", msg=")
                    .append(msg).toString());
        }
    }

    protected void writeMsg(List<VikaCacheClient> writers, String key, Object msg) {
        if (writers == null || writers.size() == 0) {
            return;
        }
        Object[] writer_objects = writers.toArray();

        int rd = random.nextInt(writer_objects.length);
        /*
         * 1、对每条消息轮询所有的mcq，如果处理成功则直接返回。 2、如果处理失败，则尝试写入下一个mcq。 3、如果所有的mcq均写入失败，则不做处理。
         */
        boolean writeRs = false;
        for (int i = 0; i < writer_objects.length; i++) {
            int index = (i + rd) % writer_objects.length;
            VikaCacheClient mqWriter = (VikaCacheClient) writer_objects[index];

            try {
                if (mqWriter.set(key, msg)) {
                    writeRs = true;
                    StatLog.inc(getMQWriteKey(mqWriter.getServerPort(), key));
                    ApiLogger.info(new StringBuilder(256).append("mcq=").append(mqWriter.getServerPort()).append(", key=").append(key)
                            .append(", mq=").append(msg));
                    break;
                }
            } catch (Exception e) {
                ApiLogger.warn(new StringBuilder(128).append("Warn: save msg to one mq false [try next], key=").append(key).append(", mq=")
                        .append(mqWriter.getServerPort()).append(",msg").append(msg), e);
            }
            StatLog.inc(getMQWriteErrorKey(mqWriter.getServerPort(), key));
            ApiLogger.info(new StringBuilder(128).append("Info: save msg to mq false, key=").append(key).append(",mq=")
                    .append(mqWriter.getServerPort()).append(",msg").append(msg));
        }
        if (!writeRs) {
            ApiLogger.error(new StringBuilder(128).append("Write mcq false, key=").append(key).append(", msg=").append(msg));
            throw new IllegalArgumentException(new StringBuilder(128).append("Write mcq false, key=").append(key).append(", msg=")
                    .append(msg).toString());
        }
    }

    protected void startExtWork() {
    }

    protected abstract String getRawReadKey();

    protected abstract String getRawWriteKey();

    protected abstract void handleMsq(String msg);

    protected abstract String getStatMQReadFlag();

    protected abstract String getStatMQReadStatFlag();

    protected abstract String getStatMQWriteErrorFlag();

    protected Thread createReadThread(final VikaCacheClient mqr) {
        Thread t = new Thread("thread_" + McqUtil.processorId.addAndGet(1) + "_mq_" + mqr.getServerPort()) {
            @Override
            public void run() {
                readFrmMQ(mqr);
            }
        };
        t.setDaemon(true);
        return t;
    }

    protected void readFrmMQ(VikaCacheClient mqReader) {
        // wait a moment for system init.
        McqUtil.waitForInit("[Mcq Process]");

        String portInfo =
                new StringBuilder(64).append("KEY:").append(getReadKey()).append("\tServer:").append(mqReader.getServerPort()).toString();
        ApiLogger.info("Start mq reader!" + portInfo);
        AtomicInteger continueReadCount = new AtomicInteger(0);
        while (true) {
            try {
                String msg = null;
                while (McqBaseManager.IS_ALL_READ.get() && (msg = (String) mqReader.get(getReadKey())) != null) {
                    StatLog.inc(getStatMQReadFlag());
                    StatLog.inc(getStatMQReadStatFlag());
                    if (ApiLogger.isTraceEnabled()) {
                        StatLog.inc(getMQReadDataKey(mqReader.getServerPort(), getReadKey()));
                    }
                    long start = System.currentTimeMillis();
                    try {
                        handleMsq(msg);
                        if (continueReadCount.addAndGet(1) % readCountOnce == 0) {
                            McqUtil.safeSleep(waitTimeOnce);
                            continueReadCount.set(0);
                            // StatLog.inc(getMQReadSleepKey(mqReader.getServerPort(),
                            // getReadKey()), waitTimeOnce);
                        }

                    } catch (Exception e) {
                        ApiLogger.warn(
                                new StringBuilder(128).append("Error: processing the msg frm mq error, ").append(portInfo).append(", msg=")
                                        .append(msg), e);
                    } finally {
                        if (readKey != null) {
                            long end = System.currentTimeMillis();
                            long cost = end - start;
                            ProfileUtil.accessStatistic(ProfileType.API.value(), readKey, end, cost);
                        }
                    }
                }

                if (!McqBaseManager.IS_ALL_READ.get()) {
                    ApiLogger.info("McqProcessor is alive but not read message.");
                }

                McqUtil.safeSleep(waitTimeOnce);
                StatLog.inc(getStatMQReadStatFlag());

                // should response thread interrupted
                if (Thread.interrupted()) {
                    ApiLogger.warn(new StringBuilder(32).append("Thread interrupted :").append(Thread.currentThread().getName()));
                    break;
                }

                // allenshen add readVikaCacheClientFactory
                if (this.readerMcqClientList != null) {
                    String serverPort = mqReader.getServerPort();
                    if (this.stopClient.get(serverPort)) {
                        ApiLogger.info("stop thread serverPort." + serverPort);
                        return;
                    }
                }
            } catch (Exception e) {
                ApiLogger.error(new StringBuilder("Error: when reship mq. key:").append(getRawReadKey()), e);
            }
        }
    }

    public void setMcqReaders(Map<String, List<VikaCacheClient>> mcqReaders) {
        this.mcqReaders = mcqReaders;
        for (List<VikaCacheClient> mcqrs : mcqReaders.values()) {
            for (VikaCacheClient mcqr : mcqrs) {
                mcqr.getClient().setPrimitiveAsString(true);
            }
        }
    }

    public void setMcqWriters(List<VikaCacheClient> mcqWriters) {
        this.mcqWriters = mcqWriters;

        for (VikaCacheClient mcq : mcqWriters) {
            mcq.getClient().setPrimitiveAsString(true);
        }

    }

    public void setReadThreadCountEachMcq(int readThreadCountEachMcq) {
        this.readThreadCountEachMcq = readThreadCountEachMcq;
    }

    public void setReadCountOnce(int readCountOnce) {
        this.readCountOnce = readCountOnce;
    }

    public void setWaitTimeOnce(int waitTimeOnce) {
        this.waitTimeOnce = waitTimeOnce;
    }

    public void setLocalReadKeyPrefix(String localReadKeyPrefix) {
        if (localReadKeyPrefix != null && localReadKeyPrefix.trim().length() > 0) {
            this.readKey = localReadKeyPrefix + "_" + getRawReadKey();
        } else {
            this.readKey = getRawReadKey();
        }
    }

    public String getReadKey() {
        if (readKey == null) {
            this.readKey = getRawReadKey();
        }
        return readKey;
    }

    public void setApiWriteKeysPrefix(List<String> apiWriteKeysPrefix) {
        this.apiWriteKeys = new ArrayList<String>(apiWriteKeysPrefix.size());
        for (String prefix : apiWriteKeysPrefix) {
            String key = prefix + "_" + getRawWriteKey();
            this.apiWriteKeys.add(key);
        }
    }

    // public void setMqBjWriters(List<VikaCacheClient> mqBjWriters) {
    // this.mqBjWriters = mqBjWriters;
    // }

    private String getMQWriteErrorKey(String serverPort, String key) {
        return getStatMQWriteErrorFlag() + "_" + serverPort + "_" + key;
    }

    private String getMQWriteKey(String serverPort, String key) {
        return "write_mq_" + serverPort + "_" + key;
    }

    private String getMQReadDataKey(String serverPort, String key) {
        return "read_mq_data_" + serverPort + "_" + key;
    }

    // private String getMQReadEmptyKey(String serverPort, String key){
    // return "read_mq_empty_" + serverPort + "_" + key;
    // }

    // private String getMQReadSleepKey(String serverPort, String key){
    // return "read_mq_sleep_" + serverPort + "_" + key;
    // }

    public void setBeforeMsgForwarder(MsgForwarder beforeMsgForwarder) {
        this.beforeMsgForwarder = beforeMsgForwarder;
    }

    public void setAfterMsgForwarder(MsgForwarder afterMsgForwarder) {
        this.afterMsgForwarder = afterMsgForwarder;
    }

    /**
     * 设置系统初始化成功状态
     */
    public static void setSystemInitSuccess() {
        McqUtil.setSystemInitSuccess();
    }


    public ReaderMcqClientList getReaderMcqClientList() {
        return readerMcqClientList;
    }


    public void setReaderMcqClientList(ReaderMcqClientList readerMcqClientList) {
        this.readerMcqClientList = readerMcqClientList;
    }

    // support domain parse ips
    public void startReadingDomain() {
        // tell MemCacheStorage that we are in processor, by fulin
        boolean isStartExtWork = false;
        MemCacheStorage.isInProcessor = true;

        if (this.readerMcqClientList != null) {
            while (true) {
                try {
                    if (this.readerMcqClientList.refresh()) {
                        Map<String, ClientStatus> clientStatus = this.readerMcqClientList.getExistMcqClientStatusMap();
                        Map<String, VikaCacheClient> vikaCacheClientMap = this.readerMcqClientList.getExistMcqClientMap();
                        if (MapUtils.isNotEmpty(clientStatus) && MapUtils.isNotEmpty(vikaCacheClientMap)) {
                            for (String serverPort : clientStatus.keySet()) {
                                ClientStatus status = clientStatus.get(serverPort);
                                VikaCacheClient client = vikaCacheClientMap.get(serverPort);
                                if (client != null) {
                                    switch (status) {
                                        case old: // 已经存在的
                                            break;
                                        case add: // 新添加的
                                            startReadThreaad(serverPort, client);
                                            break;
                                        case remove: // 下线的
                                            stopClient.put(serverPort, Boolean.TRUE);
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    logger.error("don't find VikaCacheClient by server port " + serverPort);
                                }
                            }
                        } else {
                            logger.error("clientStatus map is empty or null, vikaCacheClientMap is empty or null");
                            return;
                        }
                    } else {
                        logger.error("readerMcqClientlist refresh return false");
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }

                if (!isStartExtWork) {
                    this.startExtWork();
                    isStartExtWork = true;
                }
                if (this.readerMcqClientList.getRefreshRate() > 0) {
                    McqUtil.safeSleep(Integer.valueOf(this.readerMcqClientList.getRefreshRate().toString()));
                } else {
                    return;
                }
            }

        } else {
            logger.error("please set readerMcqClients property");
            return;
        }

    }

    /**
     * @param serverPort
     * @param client
     */
    private void startReadThreaad(String serverPort, VikaCacheClient client) {
        int i = 0;
        stopClient.remove(serverPort);
        while (i++ < readThreadCountEachMcq) {
            Thread t = createReadThread(client);
            t.start();
            readThreads.add(t);
        }
    }
}
