package cc.linkedme.mcq;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.mcq.writer.McqBaseWriter;
import cc.linkedme.data.model.DeepLink;

import javax.annotation.Resource;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class DeepLinkMsgPusher {

    @Resource
    private McqBaseWriter apiMcqWriter;

    public void addDeepLink(DeepLink deepLink) {
        String deepLinkMsg = MsgUtils.toDeepLinkMsgJson(deepLink);
        apiMcqWriter.writeMsg(deepLinkMsg);
    }

    public void addDeepLinkCount(long deepLinkId, int appId, String date, String countType, String deepLinkType) {
        JsonBuilder deepLinkCountJson = new JsonBuilder();
        deepLinkCountJson.append("type", 31);

        JsonBuilder info = new JsonBuilder();
        info.append("deeplink_id", deepLinkId);
        info.append("app_id", appId);
        info.append("date", date);
        info.append("type", deepLinkType);
        info.append("count_type", countType);
        deepLinkCountJson.append("info", info.flip());

        String deepLinkCountMsg = deepLinkCountJson.flip().toString();
        apiMcqWriter.writeMsg(deepLinkCountMsg);
    }
}
