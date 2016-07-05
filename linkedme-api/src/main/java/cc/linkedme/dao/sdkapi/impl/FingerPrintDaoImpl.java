package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.sdkapi.FingerPrintDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.FingerPrintInfo;
import org.springframework.dao.DataAccessException;

/**
 * Created by vontroy on 16-7-4.
 */
public class FingerPrintDaoImpl extends BaseDao implements FingerPrintDao {
    private static final String ADD_FINGER_PRINT_INFO = "ADD_FINGER_PRINT_INFO";
    private static final String DEL_FINGER_PRINT_INFO = "DEL_FINGER_PRINT_INFO";

    public int addFingerPrint(FingerPrintInfo fingerPrintInfo) {
        int result = 0;
        if (fingerPrintInfo == null) {
            ApiLogger.error("FingerPrintDaoImpl.addFingerPrint fingerPrintInfo is null, add failed");
        }

        long identityId = fingerPrintInfo.getIdentityId();
        String deviceId = fingerPrintInfo.getDeviceId();
        int deviceType = fingerPrintInfo.getDeviceType();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", ADD_FINGER_PRINT_INFO, identityId, identityId);

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {deviceId, deviceType, identityId});
        } catch (DataAccessException e) {
            throw e;
        }
        return result;

    }

    public int delFingerPrint(FingerPrintInfo fingerPrintInfo) {
        int result = 0;
        if (fingerPrintInfo == null) {
            ApiLogger.error("FingerPrintDaoImpl.addFingerPrint fingerPrintInfo is null, add failed");
        }

        long identityId = fingerPrintInfo.getIdentityId();
        String deviceId = fingerPrintInfo.getDeviceId();
        int deviceType = fingerPrintInfo.getDeviceType();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", DEL_FINGER_PRINT_INFO, identityId, identityId);

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {0, identityId, deviceId, deviceType, 1});
        } catch (DataAccessException e) {
            throw e;
        }
        return result;
    }
}
