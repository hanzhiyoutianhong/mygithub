package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.dao.sdkapi.FingerPrintDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.FingerPrintInfo;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.sdkapi.FingerPrintService;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vontroy on 16-7-4.
 */
public class FingerPrintServiceImpl implements FingerPrintService {
    @Resource
    private FingerPrintDao fingerPrintDao;

    public int addFingerPrint(FingerPrintInfo fingerPrintInfo) {
        int result = fingerPrintDao.addFingerPrint(fingerPrintInfo);
        return result;
    }

    public int delFingerPrint(FingerPrintInfo fingerPrintInfo) {
        int result = fingerPrintDao.delFingerPrint(fingerPrintInfo);
        return result;
    }
}
