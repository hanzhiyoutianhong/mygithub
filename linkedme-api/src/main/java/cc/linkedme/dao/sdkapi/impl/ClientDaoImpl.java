package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.model.ClientInfo;
import org.springframework.dao.DataAccessException;

/**
 * Created by LinkedME00 on 16/3/18.
 */
public class ClientDaoImpl extends BaseDao implements ClientDao {
    private static final String ADD_CLIENT = "ADD_CLIENT";

    public int addClient(ClientInfo clientInfo) {
        int result = 0;
        if (clientInfo == null) {
            ApiLogger.error("ClientDaoImpl.addClient clientInfo is null, add failed");
        }
        long identityId = clientInfo.getIdentityId();
        String linkedMEKey = clientInfo.getLinkedmeKey();
        String iMei = clientInfo.getiMei();
        String androidId = clientInfo.getAndroidId();
        String serialNumber = clientInfo.getSerialNumber();
        String deviceMac = clientInfo.getDeviceMac();
        String deviceFingerPrint = clientInfo.getDeviceFingerPrint();
        String deviceId = clientInfo.getDeviceId();
        int deviceType = clientInfo.getDeviceType();
        String deviceModel = clientInfo.getDeviceModel();
        String deviceBrand = clientInfo.getDeviceBrand();
        boolean hasBluetooth = clientInfo.getHasBlutooth();
        boolean hasNfc = clientInfo.getHasNfc();
        boolean hasSim = clientInfo.getHasSim();
        String os = clientInfo.getOs();
        int osVersionDetail = clientInfo.getosVersionDetail();
        String osVersion = clientInfo.getOsVersion();
        int screenDpi = clientInfo.getScreenDpi();
        int screenHeight = clientInfo.getScreenHeight();
        int screenWidth = clientInfo.getScreenWidth();
        boolean isWifi = clientInfo.getIsWifi();
        boolean isReferable = clientInfo.getIsReferable();
        boolean latVal = clientInfo.getLatVal();
        String carrier = clientInfo.getCarrier();
        String appVersion = clientInfo.getAppVersion();
        int sdk_update = clientInfo.getSdkUpdate();
        String sdkVersion = clientInfo.getSdkVersion();
        String iOSTeamId = clientInfo.getIosTeamId();
        String iOSBundle = clientInfo.getIosBundleId();

        TableChannel tableChannel = tableContainer.getTableChannel("clientInfo", ADD_CLIENT, deviceId, deviceId);

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(),
                    new Object[] {identityId, linkedMEKey, iMei, androidId, serialNumber, deviceMac, deviceFingerPrint, deviceId,
                            deviceType, deviceModel, deviceBrand, hasBluetooth, hasNfc, hasSim, os, osVersionDetail, osVersion, screenDpi,
                            screenHeight, screenWidth, isWifi, isReferable, latVal, carrier, appVersion, sdk_update, sdkVersion, iOSTeamId,
                            iOSBundle});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                // 如果identityId存在,则更新
                ApiLogger.info(new StringBuilder(128).append("Duplicate insert client, id=").append(identityId));
            } else {
                throw e;
            }
        }
        return result;

    }

}
