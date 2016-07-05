package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.sdkapi.FingerPrintDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.FingerPrintInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vontroy on 16-7-4.
 */
public class FingerPrintDaoImpl extends BaseDao implements FingerPrintDao {
    private static final String ADD_FINGER_PRINT_INFO = "ADD_FINGER_PRINT_INFO";
    private static final String DEL_FINGER_PRINT_INFO = "DEL_FINGER_PRINT_INFO";
    private static final String GET_FINGER_PRINT_INFO = "GET_FINGER_PRINT_INFO";

    public FingerPrintInfo getFingerPrint(FingerPrintInfo fingerPrintInfo) {
        long identityId = fingerPrintInfo.getIdentityId();
        String deviceId = fingerPrintInfo.getDeviceId();
        int deviceType = fingerPrintInfo.getDeviceType();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", GET_FINGER_PRINT_INFO, identityId, identityId);

        FingerPrintInfo resultInfo = new FingerPrintInfo();

        try {
            tableChannel.getJdbcTemplate().query(tableChannel.getSql(), new Object[] {identityId, deviceId, deviceType}, new RowMapper() {
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    resultInfo.setId(resultSet.getInt("id"));
                    resultInfo.setIdentityId(identityId);
                    resultInfo.setDeviceType(deviceType);
                    resultInfo.setDeviceId(deviceId);
                    return null;
                }
            });
        } catch (DataAccessException e) {
            ApiLogger.error("FingerPrintDaoImpl.delFingerPrint Database Access Error");
            throw e;
        }

        return resultInfo;
    }

    public int addFingerPrint(FingerPrintInfo fingerPrintInfo) {
        int result = 0;

        long identityId = fingerPrintInfo.getIdentityId();
        String deviceId = fingerPrintInfo.getDeviceId();
        int deviceType = fingerPrintInfo.getDeviceType();
        String currentTime = fingerPrintInfo.getCurrentTime();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", ADD_FINGER_PRINT_INFO, identityId, identityId);

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {deviceId, deviceType, identityId, currentTime, currentTime});
        } catch (DataAccessException e) {
            ApiLogger.error("FingerPrintDaoImpl.delFingerPrint Database Access Error");
            throw e;
        }
        return result;

    }

    public int delFingerPrint(FingerPrintInfo fingerPrintInfo) {
        int result = 0;

        long identityId = fingerPrintInfo.getIdentityId();
        String deviceId = fingerPrintInfo.getDeviceId();
        int deviceType = fingerPrintInfo.getDeviceType();
        String currentTime = fingerPrintInfo.getCurrentTime();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", DEL_FINGER_PRINT_INFO, identityId, identityId);

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {0, currentTime, identityId, deviceId, deviceType, 1});
        } catch (DataAccessException e) {
            ApiLogger.error("FingerPrintDaoImpl.delFingerPrint Database Access Error");
            throw e;
        }
        return result;
    }
}
