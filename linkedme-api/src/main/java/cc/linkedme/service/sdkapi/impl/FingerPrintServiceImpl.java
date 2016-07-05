package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.dao.sdkapi.FingerPrintDao;
import cc.linkedme.data.dao.strategy.TableChannel;
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

    public int getFingerPrint(FingerPrintInfo fingerPrintInfo) {
        if(fingerPrintInfo == null ) {
            ApiLogger.error("FingerPrintDaoImpl.addFingerPrint fingerPrintInfo is null, get failed");
        }

        FingerPrintInfo resultInfo = fingerPrintDao.getFingerPrint(fingerPrintInfo);
        if( resultInfo.getId() >= 1 )
            return resultInfo.getId();
        return -1;
    }

    public int updateFingerPrint(FingerPrintInfo fingerPrintInfo) {
        int result = 0;
        if( getFingerPrint(fingerPrintInfo) == -1 )
            result += addFingerPrint(fingerPrintInfo);
        return result;
    }

    public int addFingerPrint(FingerPrintInfo fingerPrintInfo) {
        if (fingerPrintInfo == null) {
            ApiLogger.error("FingerPrintDaoImpl.addFingerPrint fingerPrintInfo is null, add failed");
        }

        int result = fingerPrintDao.addFingerPrint(fingerPrintInfo);
        return result;
    }

    public int delFingerPrint(FingerPrintInfo fingerPrintInfo) {
        if (fingerPrintInfo == null) {
            ApiLogger.error("FingerPrintDaoImpl.delFingerPrint fingerPrintInfo is null, add failed");
        }
        int result = fingerPrintDao.delFingerPrint(fingerPrintInfo);
        return result;
    }
}
