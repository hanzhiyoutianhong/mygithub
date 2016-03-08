package cc.linkedme.mcq;

import cc.linkedme.commons.mcq.writer.McqBaseWriter;
import cc.linkedme.data.model.DeepLink;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class DeepLinkMsgPusher {
    private McqBaseWriter apiMcqWriter;

    public void addDeepLink(DeepLink deepLink) {
        String deepLinkMsg = "";
        apiMcqWriter.writeMsg(deepLinkMsg);
    }
}
