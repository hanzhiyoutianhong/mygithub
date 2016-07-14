package cc.linkedme.mcq;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.ButtonCount;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.FingerPrintInfo;
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

        info.append("link_label", deepLink.getLink_label());
        info.append("ios_use_default", deepLink.isIos_use_default());
        info.append("ios_custom_url", deepLink.getIos_custom_url());
        info.append("android_use_default", deepLink.isAndroid_use_default());
        info.append("android_custom_url", deepLink.getAndroid_custom_url());
        info.append("desktop_use_default", deepLink.isDesktop_use_default());
        info.append("desktop_custom_url", deepLink.getDesktop_custom_url());

        deepLinkMsg.append("info", info.flip());
        return deepLinkMsg.flip().toString();
    }

    public static String updateFingerPrintMsgJson(FingerPrintInfo fingerPrintInfo) {
        JsonBuilder fingerPrintMsg = new JsonBuilder();
        fingerPrintMsg.append("type", 41);
        JsonBuilder info = new JsonBuilder();

        info.append("device_id", fingerPrintInfo.getDeviceId());
        info.append("device_type", fingerPrintInfo.getDeviceType());
        info.append("identity_id", fingerPrintInfo.getIdentityId());
        info.append("operation_type", fingerPrintInfo.getOperationType().toString());
        info.append("current_time", fingerPrintInfo.getCurrentTime());
        if (fingerPrintInfo.getOperationType() == FingerPrintInfo.OperationType.ADD) {
            info.append("new_identity_id", fingerPrintInfo.getNewIdentityId());
        }

        fingerPrintMsg.append("info", info.flip());
        return fingerPrintMsg.flip().toString();
    }

    public static String buttonCountMsgJson(ButtonCount buttonCount) {
        JsonBuilder buttonCountMsg = new JsonBuilder();
        buttonCountMsg.append("type", 51);
        JsonBuilder info = new JsonBuilder();

        info.append("app_id", buttonCount.getAppId());
        info.append("button_id", buttonCount.getBtnId());
        info.append("consumer_id", buttonCount.getConsumerId());
        info.append("date", buttonCount.getDate());

        String countType = buttonCount.getCountType();
        info.append("count_type", countType);
        info.append("count_value", buttonCount.getCountValue());
//        if(countType.equals("ios_view")) {
//            info.append("ios_view", buttonCount.getIosViewCount());
//        } else if( countType.equals("android_view")) {
//            info.append("android_view", buttonCount.getAndroidViewCount());
//        } else if(countType.equals("web_view")) {
//            info.append("web_view", buttonCount.getWebViewCount());
//        } else if(countType.equals("other_view")) {
//            info.append("other_view", buttonCount.getOtherViewCount());
//        } else if(countType.equals("ios_click")) {
//            info.append("ios_click", buttonCount.getIosClickCount());
//        } else if(countType.equals("android_click")) {
//            info.append("android_click", buttonCount.getAndroidClickCount());
//        } else if(countType.equals("web_click")) {
//            info.append("web_click", buttonCount.getWebClickCount());
//        } else if(countType.equals("other_click")) {
//            info.append("other_click", buttonCount.getOtherClickCount());
//        } else if(countType.equals("ios_open")) {
//            info.append("ios_open", buttonCount.getIosOpenCount());
//        } else if(countType.equals("android_open")) {
//            info.append("android_open", buttonCount.getAndroidOpenCount());
//        } else if(countType.equals("web_open")) {
//            info.append("web_open", buttonCount.getWebOpenCount());
//        } else if(countType.equals("other_open")) {
//            info.append("other_open", buttonCount.getOtherOpenCount());
//        } else if(countType.equals("ios_order")) {
//            info.append("ios_order", buttonCount.getIosOrderCount());
//        } else if(countType.equals("android_order")) {
//            info.append("android_order", buttonCount.getAndroidOrderCount());
//        } else if(countType.equals("web_order")) {
//            info.append("web_order", buttonCount.getWebOrderCount());
//        } else if(countType.equals("other_order")) {
//            info.append("other_order", buttonCount.getOtherOrderCount());
//        } else if(countType.equals("ios_income")) {
//            info.append("ios_income", buttonCount.getIosIncome());
//        } else if(countType.equals("android_income")) {
//            info.append("android_income", buttonCount.getAndroidIncome());
//        } else if(countType.equals("web_income")) {
//            info.append("web_income", buttonCount.getWebIncome());
//        } else if(countType.equals("other_income")) {
//            info.append("other_income", buttonCount.getOtherIncome());
//        }

        buttonCountMsg.append("info", info.flip());
        return buttonCountMsg.flip().toString();
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

        deepLink.setLink_label(deepLinkMsg.getString("link_label"));
        deepLink.setIos_use_default(deepLinkMsg.getBoolean("ios_use_default"));
        deepLink.setIos_custom_url(deepLinkMsg.getString("ios_custom_url"));
        deepLink.setAndroid_use_default(deepLinkMsg.getBoolean("android_use_default"));
        deepLink.setAndroid_custom_url(deepLinkMsg.getString("android_custom_url"));
        deepLink.setDesktop_use_default(deepLinkMsg.getBoolean("desktop_use_default"));
        deepLink.setDesktop_custom_url(deepLinkMsg.getString("desktop_custom_url"));
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
        info.append("sdkUpdate", clientInfo.getSdkUpdate());
        info.append("sdkVersion", clientInfo.getSdkUpdate());
        info.append("iOSTeamId", clientInfo.getIosTeamId());
        info.append("iOSBundleId", clientInfo.getIosBundleId());
        info.append("linkedmeKey", clientInfo.getLinkedmeKey());
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
        clientInfo.setLatVal(clientMsg.getBoolean("latVal"));
        clientInfo.setCarrier(clientMsg.getString("carrier"));
        clientInfo.setAppVersion(clientMsg.getString("appVersion"));
        clientInfo.setSdkUpdate(clientMsg.getInt("sdkUpdate"));
        clientInfo.setIosTeamId(clientMsg.getString("iOSTeamId"));
        clientInfo.setIosBundleId(clientMsg.getString("iOSBundleId"));
        clientInfo.setLinkedmeKey(clientMsg.getString("linkedmeKey"));
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

    public static boolean isCountType(int type) {
        return McqMsgType.ADD_DEEPLINK_COUNT.getType() == type;
    }

    public static boolean isFingerPrintType(int type) {
        return (McqMsgType.UPDATE_FINGER_PRINT.getType() == type);
    }

    public static boolean isAddButtonType(int type) {
        return (McqMsgType.ADD_BUTTON_COUNT.getType() == type);
    }

}
