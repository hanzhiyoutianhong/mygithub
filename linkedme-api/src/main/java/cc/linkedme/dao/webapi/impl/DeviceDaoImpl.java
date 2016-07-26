package cc.linkedme.dao.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.DeviceDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.DeviceInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME07 on 16/7/17.
 */
public class DeviceDaoImpl extends BaseDao implements DeviceDao {

    private static final String ADD_DEVICE = "ADD_DEVICE";
    private static final String DEL_DEVICE_BY_APPID_AND_DEVICEID = "DEL_DEVICE_BY_APPID_AND_DEVICEID";
    private static final String UPDATE_DEVICE_BY_APPID_AND_DEVICEID = "UPDATE_DEVICE_BY_APPID_AND_DEVICEID";
    private static final String GET_DEVICE_BY_APPID_AND_DEVICEID = "GET_DEVICE_BY_APPID_AND_DEVICEID";
    private static final String GET_DEVICES_BY_APPID = "GET_DEVICES_BY_APPID";
    private static final String GET_ALL_DEVICES = "GET_ALL_DEVICES";
    private static final String ONLINE_DEVICE_BY_APPID_AND_DEVICEID = "ONLINE_DEVICE_BY_APPID_AND_DEVICEID";


    @Override
    public int addDevice(DeviceInfo deviceInfo) {
        int result = 0;
        TableChannel tableChannel;
        try {
            tableChannel = tableContainer.getTableChannel("device", ADD_DEVICE, 0L, 0L);

            Object[] values = {deviceInfo.getAppId() +"_"+ deviceInfo.getDeviceId(), deviceInfo.getAppId(), deviceInfo.getDeviceId(),
                    deviceInfo.getDeviceName(), deviceInfo.getPlatform(), deviceInfo.getDescription()};

            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), values);
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {

                tableChannel = tableContainer.getTableChannel("device", ONLINE_DEVICE_BY_APPID_AND_DEVICEID, 0L, 0L);

                try {
                    result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(),
                            new Object[] {deviceInfo.getAppId() +"_"+ deviceInfo.getDeviceId()});
                } catch (DataAccessException ea) {
                    throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to delete device" + deviceInfo.getDeviceId());
                }
                return result;
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to insert device" + deviceInfo.getDeviceId());
        }
        return result;
    }

    @Override
    public int delDevice(long appId, String deviceId) {
        int result = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("device", DEL_DEVICE_BY_APPID_AND_DEVICEID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        try {
            result += jdbcTemplate.update(tableChannel.getSql(), new Object[] {appId +"_"+ deviceId});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to delete device" + deviceId);
        }
        return result;
    }

    @Override
    public int updateDevice(DeviceInfo deviceInfo) {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        String updateTime = sdf.format(currentTime);

        TableChannel tableChannel = tableContainer.getTableChannel("device", UPDATE_DEVICE_BY_APPID_AND_DEVICEID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        Object[] values = new Object[] {deviceInfo.getDeviceName(), deviceInfo.getPlatform(), deviceInfo.getDescription(), updateTime,
                deviceInfo.getAppId() +"_"+ deviceInfo.getDeviceId()};

        try {
            result += jdbcTemplate.update(tableChannel.getSql(), values);
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update device" + deviceInfo.getDeviceId());
        }

        return result;
    }

    @Override
    public DeviceInfo getDevice(long appId, String deviceId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TableChannel tableChannel = tableContainer.getTableChannel("device", GET_DEVICE_BY_APPID_AND_DEVICEID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<DeviceInfo> deviceInfos = new ArrayList<DeviceInfo>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {appId +"_"+ deviceId}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setAppId(appId);
                deviceInfo.setDeviceId(deviceId);
                deviceInfo.setDeviceName(resultSet.getString("device_name"));
                deviceInfo.setPlatform(resultSet.getInt("platform"));
                deviceInfo.setDescription(resultSet.getString("description"));
                deviceInfo.setCreateTime(resultSet.getString("create_time").substring(0,19));
                deviceInfo.setLastUpateTime(resultSet.getString("last_update_time").substring(0,19));
                deviceInfos.add(deviceInfo);
                return null;
            }
        });

        if (!deviceInfos.isEmpty())
            return deviceInfos.get(0);
        else
            return null;
    }

    @Override
    public List<DeviceInfo> listDevices(long appId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TableChannel tableChannel = tableContainer.getTableChannel("device", GET_DEVICES_BY_APPID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<DeviceInfo> deviceInfos = new ArrayList<>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {appId}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setAppId(appId);
                deviceInfo.setDeviceId(resultSet.getString("device_id"));
                deviceInfo.setDeviceName(resultSet.getString("device_name"));
                deviceInfo.setPlatform(resultSet.getInt("platform"));
                deviceInfo.setDescription(resultSet.getString("description"));
                deviceInfo.setCreateTime(resultSet.getString("create_time").substring(0,19));
                deviceInfo.setLastUpateTime(resultSet.getString("last_update_time").substring(0,19));
                deviceInfos.add(deviceInfo);
                return null;
            }
        });
        return deviceInfos;
    }

    @Override
    public Map<String, List<Long>> getDeviceIdAndAppIdKVMap() {
        TableChannel tableChannel = tableContainer.getTableChannel("device", GET_ALL_DEVICES, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        final Map<String, List<Long>> whiteDeviceMap = new HashMap<>();

        jdbcTemplate.query(tableChannel.getSql(), new Object[] {}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {

                String deviceId = resultSet.getString("device_id");
                long appId = resultSet.getLong("app_id");

                List appList = whiteDeviceMap.get(deviceId);
                if (appList == null) {
                    appList = new ArrayList<Long>();
                }
                for (Object app : appList) {
                    if ((long) app == appId) {
                        return null;
                    }
                }
                appList.add(appId);
                whiteDeviceMap.put(deviceId, appList);

                return null;
            }
        });
        return whiteDeviceMap;
    }
}
