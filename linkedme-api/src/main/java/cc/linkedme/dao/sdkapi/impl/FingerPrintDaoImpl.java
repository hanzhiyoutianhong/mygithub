package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.sdkapi.FingerPrintDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.model.FingerPrintInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vontroy on 16-7-4.
 */
public class FingerPrintDaoImpl extends BaseDao implements FingerPrintDao {
    private static final String ADD_FINGER_PRINT_INFO = "ADD_FINGER_PRINT_INFO";
    private static final String GET_FINGER_PRINT_INFO = "GET_FINGER_PRINT_INFO";
    private static final String SET_VALID_STATUS = "SET_VALID_STATUS";

    public FingerPrintInfo getFingerPrint(FingerPrintInfo fingerPrintInfo) {
        long identityId = fingerPrintInfo.getIdentityId();
        String deviceId = fingerPrintInfo.getDeviceId();
        int deviceType = fingerPrintInfo.getDeviceType();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", GET_FINGER_PRINT_INFO, deviceId, deviceId);

        FingerPrintInfo resultInfo = new FingerPrintInfo();

        try {
            tableChannel.getJdbcTemplate().query(tableChannel.getSql(), new Object[] {identityId, deviceId, deviceType}, new RowMapper() {
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    resultInfo.setId(resultSet.getInt("id"));
                    resultInfo.setIdentityId(identityId);
                    resultInfo.setDeviceType(deviceType);
                    resultInfo.setDeviceId(deviceId);
                    resultInfo.setValid_status(resultSet.getInt("valid_status"));
                    return null;
                }
            });
        } catch (DataAccessException e) {
            ApiLogger.error("FingerPrintDaoImpl.getFingerPrint Database Access Error", e);
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }

        return resultInfo;
    }

    public int setValidStatusById( FingerPrintInfo fingerPrintInfo, int val ) {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        String updateTime = sdf.format(currentTime);

        long identityId = fingerPrintInfo.getIdentityId();
        int deviceType = fingerPrintInfo.getDeviceType();
        String deviceId = fingerPrintInfo.getDeviceId();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", SET_VALID_STATUS, deviceId, deviceId);

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {val, updateTime, identityId, deviceId, deviceType});
        } catch (DataAccessException e) {
            ApiLogger.error("FingerPrintDaoImpl.setValidStatusById Database Access Error", e);
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result;
    }

    public int addFingerPrint(FingerPrintInfo fingerPrintInfo, int val) {
        int result = 0;

        long identityId = fingerPrintInfo.getIdentityId();
        String deviceId = fingerPrintInfo.getDeviceId();
        int deviceType = fingerPrintInfo.getDeviceType();
        String currentTime = fingerPrintInfo.getCurrentTime();

        TableChannel tableChannel = tableContainer.getTableChannel("fingerPrintInfo", ADD_FINGER_PRINT_INFO, deviceId, deviceId);

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {deviceId, deviceType, identityId, currentTime, currentTime, val});
        } catch (DataAccessException e) {
            ApiLogger.error("FingerPrintDaoImpl.addFingerPrint Database Access Error", e);
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result;
    }
}
