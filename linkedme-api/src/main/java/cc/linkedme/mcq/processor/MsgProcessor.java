package cc.linkedme.mcq.processor;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.mcq.reader.McqProcessor;
import cc.linkedme.commons.util.ApiUtil;
import cc.linkedme.commons.util.UseTimeStasticsMonitor;
import net.sf.json.JSONObject;

import java.util.LinkedList;

/**
 * process packet from mcq
 */
public class MsgProcessor extends McqProcessor {
    public static final UseTimeStasticsMonitor McqProcMonitor = new UseTimeStasticsMonitor(
            "McqProcMonitor"); // mcq处理统计
    /**
     * 是否更新db
     */
    private boolean updateDb = true;

    /**
     * 是否更新mc
     */
    private boolean updateMc = true;

    public static final boolean debug = false;

    //处理收到的消息
    @Override
    protected void handleMsq(final String msg) {
        //System.out.println("handleMsq msg:" + msg);
        String logMsg = new StringBuilder(256).append("key:").append(getReadKey()).append(" updateDb:")
            .append(updateDb).append(" updateMc:").append(updateMc).append(" MsgProcessor recv:").append(msg).toString();
        ApiLogger.info(logMsg);
        LinkedList<Long> stamps = McqProcMonitor.start(null, debug);
        int result = ApiUtil.MQ_PROCESS_SUCCESS; 
        if (updateMc || updateDb) {
            try {
                result = process(msg);
            } catch (Exception e) {
                result = ApiUtil.MQ_PROCESS_RETRY;
                ApiLogger.warn(new StringBuilder(256).append("MblogMsgProcessor process error! msg:").append(msg), e);
            }
        }
        // 将处理不完整的消息写入失败队列 
        if (result == ApiUtil.MQ_PROCESS_RETRY) {
            saveErrorMsg(msg);
        } else if (result == ApiUtil.MQ_PROCESS_DEGRADATION) {
            saveDegradationMsg(msg);
        } else if (result == ApiUtil.MQ_PROCESS_ABORT) {
            ApiLogger.warn(new StringBuilder(256).append("[MblogMsgProcessor] Abort msg:").append(msg));
        }
        // 结束统计
        McqProcMonitor.end(stamps, false, debug);
    }

    public int process(String mcqMsg) throws Exception {
      //根据不同的消息作不同的处理
        JSONObject msgJson = JSONObject.fromObject(mcqMsg);
        int type = msgJson.getInt("type");
        JSONObject info = msgJson.getJSONObject("info");
        if(type == 1) {
            //deepLink消息
            processDeepLinkMsg(type, info);
        } else {
            //
        }
        return 0;
    }

    private int processDeepLinkMsg(int type, JSONObject info) {
        int result = ApiUtil.MQ_PROCESS_ABORT; // 默认为丢弃
        if(type == 10) {
            //add deepLink
        } else if(type == 11) {
            //delete deepLink
        } else if(type == 12) {
            //update deepLink
        }
        return result;
    }

    protected int addDeepLink(JSONObject info) {
        if(updateDb) {
            
        }
        if(updateMc) {
            
        }
        return 0;
    }
    
    public void saveErrorMsg(String msg) {
        ApiLogger.info("Retyr failed msg:" + msg);
    }
    
    public void saveDegradationMsg(String msg) {
        ApiLogger.info("DegradationMsg:" + msg);
    }
    
    @Override
    protected String getStatMQReadFlag() {
        return "all_mq_read_MSG_PROCESS";
    }

    @Override
    protected String getStatMQReadStatFlag() {
        return "all_mq_read_stat_MSG_PROCESS";
    }

    @Override
    protected void startExtWork() {
        // 调整为需要时加载的方式
    }

}