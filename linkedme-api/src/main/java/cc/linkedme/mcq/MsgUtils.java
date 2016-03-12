package cc.linkedme.mcq;

import cc.linkedme.commons.json.JsonBuilder;
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
        info.append("deeplink_id", deepLink.getDeeplink_id());
        info.append("identity_id", deepLink.getIdentity_id());
        info.append("linkedme_key", deepLink.getLinkedme_key());
        info.append("deeplink_md5", deepLink.getDeeplink_md5());
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
        deepLink.setDeeplink_id(deepLinkMsg.getLong("deeplink_id"));
        deepLink.setIdentity_id(deepLinkMsg.getString("identity_id"));
        deepLink.setLinkedme_key(deepLinkMsg.getString("linkedme_key"));
        deepLink.setDeeplink_md5(deepLinkMsg.getString("deeplink_md5"));
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



    public static boolean isDeeplinkMsgType(int type) {
        return (McqMsgType.ADD_DEEPLINK.getType() == type || McqMsgType.UPDATE_DEEPLINK.getType() == type
                || McqMsgType.DELETE_DEEPLINK.getType() == type);
    }
}
