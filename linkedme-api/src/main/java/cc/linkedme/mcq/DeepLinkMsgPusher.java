package cc.linkedme.mcq;

import cc.linkedme.commons.mcq.writer.McqBaseWriter;
import cc.linkedme.data.model.DeepLink_bak;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class DeepLinkMsgPusher {

    private McqBaseWriter apiMcqWriter;

    public void addDeepLink(DeepLink_bak deepLink) {
        String deepLinkMsg = MsgUtils.toDeepLinkMsgJson(deepLink);
        apiMcqWriter.writeMsg(deepLinkMsg);
    }

    public void setApiMcqWriter(McqBaseWriter apiMcqWriter) {
        this.apiMcqWriter = apiMcqWriter;
    }
}
