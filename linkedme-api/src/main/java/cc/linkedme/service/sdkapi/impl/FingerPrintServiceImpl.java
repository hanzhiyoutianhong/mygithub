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

    public FingerPrintInfo getFingerPrint(FingerPrintInfo fingerPrintInfo) {
        if (fingerPrintInfo == null) {
            ApiLogger.error("FingerPrintDaoImpl.addFingerPrint fingerPrintInfo is null, get failed");
        }

        FingerPrintInfo resultInfo = fingerPrintDao.getFingerPrint(fingerPrintInfo);

        return resultInfo;
    }

    public int setValidStatus(FingerPrintInfo fingerPrintInfo, int val) {
        if (fingerPrintInfo.getId() <= 1) {
            ApiLogger.error("Invalid FingerPrint id, update valid status failed");
        }
        return fingerPrintDao.setValidStatusById(fingerPrintInfo, val);
    }

    public int addFingerPrint(FingerPrintInfo fingerPrintInfo) {
        if (fingerPrintInfo == null) {
            ApiLogger.error("FingerPrintDaoImpl.addFingerPrint fingerPrintInfo is null, add failed");
        }
        int result = 0;

        if (fingerPrintInfo.getOperationType() == FingerPrintInfo.OperationType.UPDATE) {
            FingerPrintInfo existedFingerPrintInfo = getFingerPrint(fingerPrintInfo);
            if (existedFingerPrintInfo.getId() == -1) {
                result += fingerPrintDao.addFingerPrint(fingerPrintInfo, 0);
            } else {
                if (existedFingerPrintInfo.getValid_status() != 0) {
                    result += setValidStatus(existedFingerPrintInfo, 0);
                }
            }
            fingerPrintInfo.setIdentityId(fingerPrintInfo.getNewIdentityId());
        }
        result += fingerPrintDao.addFingerPrint(fingerPrintInfo, 1);

        return result;
    }
}
