package cc.linkedme.mcq;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import net.sf.json.JSONObject;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class MsgUtils {
    public static String toDeepLinkMsgJson(DeepLink deepLink) {
        JsonBuilder deepLinkMsg = new JsonBuilder();
        deepLinkMsg.append("type", 11);
        JsonBuilder info = new JsonBuilder();
        info.append("deeplink_id", deepLink.getDeeplinkId());
        info.append("identity_id", deepLink.getIdentityId());
        info.append("appid", deepLink.getAppId());
        info.append("linkedme_key", deepLink.getLinkedmeKey());
        info.append("deeplink_md5", deepLink.getDeeplinkMd5());
        info.append("create_time", deepLink.getCreateTime());
        info.append("tags", deepLink.getTags());
        info.append("alias", deepLink.getAlias());
        info.append("channel", deepLink.getChannel());
        info.append("feature", deepLink.getFeature());
        info.append("stage", deepLink.getStage());
        info.append("campaign", deepLink.getCampaign());
        info.append("params", deepLink.getParams());
        info.append("source", deepLink.getSource());
        deepLinkMsg.append("info", info.flip());
        return deepLinkMsg.flip().toString();
    }

    public static DeepLink toDeepLinkObj(JSONObject deepLinkMsg) {
        DeepLink deepLink = new DeepLink();
        deepLink.setDeeplinkId(deepLinkMsg.getLong("deeplink_id"));
        deepLink.setIdentityId(deepLinkMsg.getLong("identity_id"));
        deepLink.setAppId(deepLinkMsg.getLong("appid"));
        deepLink.setLinkedmeKey(deepLinkMsg.getString("linkedme_key"));
        deepLink.setDeeplinkMd5(deepLinkMsg.getString("deeplink_md5"));
        deepLink.setCreateTime(deepLinkMsg.getString("create_time"));
        deepLink.setTags(deepLinkMsg.getString("tags"));
        deepLink.setAlias(deepLinkMsg.getString("alias"));
        deepLink.setChannel(deepLinkMsg.getString("channel"));
        deepLink.setFeature((deepLinkMsg.getString("feature")));
        deepLink.setStage((deepLinkMsg.getString("stage")));
        deepLink.setCampaign((deepLinkMsg.getString("campaign")));
        deepLink.setParams((deepLinkMsg.getString("params")));
        deepLink.setSource((deepLinkMsg.getString("source")));
        return deepLink;
    }


    public static JsonBuilder toClientMsgJson(ClientInfo clientInfo) {
        JsonBuilder info = new JsonBuilder();
        info.append("identityId", clientInfo.getIdentityId());
        info.append("deviceId", clientInfo.getDeviceId());
        info.append("deviceType", clientInfo.getDeviceType());
        info.append("deviceModel", clientInfo.getDeviceModel());
        info.append("deviceBrand", clientInfo.getDeviceBrand());
        info.append("hasBluetooth", clientInfo.getHasBlutooth());
        info.append("hasNfc", clientInfo.getHasNfc());
        info.append("hasSim", clientInfo.getHasSim());
        info.append("os", clientInfo.getOs());
        info.append("osVersion", clientInfo.getOsVersion());
        info.append("screenDpi", clientInfo.getScreenDpi());
        info.append("screenHeight", clientInfo.getScreenHeight());
        info.append("screenWidth", clientInfo.getScreenWidth());
        info.append("isWifi", clientInfo.getIsWifi());
        info.append("isReferable", clientInfo.getIsReferable());
        info.append("latVal", clientInfo.getLatVal());
        info.append("carrier", clientInfo.getCarrier());
        info.append("appVersion", clientInfo.getAppVersion());
        info.append("sdkVersion", clientInfo.getSdkUpdate());
        info.append("iOSTeamId", clientInfo.getIosTeamId());
        info.append("iOSBundleId", clientInfo.getIosBundleId());
        return info;
    }

    public static ClientInfo toClientInfoObj(JSONObject clientMsg) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setIdentityId(clientMsg.getLong("identityId"));
        clientInfo.setDeviceId(clientMsg.getString("deviceId"));
        clientInfo.setDeviceType(clientMsg.getInt("deviceType"));
        clientInfo.setDeviceModel(clientMsg.getString("deviceModel"));
        clientInfo.setDeviceBrand(clientMsg.getString("deviceBrand"));
        clientInfo.setHasBlutooth(clientMsg.getBoolean("hasBluetooth"));
        clientInfo.setHasNfc(clientMsg.getBoolean("hasNfc"));
        clientInfo.setHasSim(clientMsg.getBoolean("hasSim"));
        clientInfo.setOs(clientMsg.getString("os"));
        clientInfo.setOsVersion(clientMsg.getString("osVersion"));
        clientInfo.setScreenDpi(clientMsg.getInt("screenDpi"));
        clientInfo.setScreenHeight(clientMsg.getInt("screenHeight"));
        clientInfo.setScreenWidth(clientMsg.getInt("screenWidth"));
        clientInfo.setIsWifi(clientMsg.getBoolean("isWifi"));
        clientInfo.setIsReferable(clientMsg.getBoolean("isReferable"));
        clientInfo.setLatVal(clientMsg.getString("latVal"));
        clientInfo.setCarrier(clientMsg.getString("carrier"));
        clientInfo.setAppVersion(clientMsg.getString("appVersion"));
        clientInfo.setIosTeamId(clientMsg.getString("iOSTeamId"));
        clientInfo.setIosBundleId(clientMsg.getString("iOSBundleId"));
        return clientInfo;
    }

    public static boolean isDeeplinkMsgType(int type) {
        return (McqMsgType.ADD_DEEPLINK.getType() == type || McqMsgType.UPDATE_DEEPLINK.getType() == type
                || McqMsgType.DELETE_DEEPLINK.getType() == type);
    }

    public static boolean isClientMsgType(int type) {
        return (McqMsgType.ADD_CLIENT.getType() == type || McqMsgType.UPDATE_CLIENT.getType() == type
                || McqMsgType.DELETE_CLIENT.getType() == type);
    }

}
