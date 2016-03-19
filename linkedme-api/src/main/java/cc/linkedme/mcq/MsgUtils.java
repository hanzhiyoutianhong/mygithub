package cc.linkedme.mcq;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.DeepLink;
import net.sf.json.JSONObject;

import java.sql.Timestamp;

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



    public static boolean isDeeplinkMsgType(int type) {
        return (McqMsgType.ADD_DEEPLINK.getType() == type || McqMsgType.UPDATE_DEEPLINK.getType() == type
                || McqMsgType.DELETE_DEEPLINK.getType() == type);
    }

}
