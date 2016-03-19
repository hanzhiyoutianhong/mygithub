package cc.linkedme.mcq;

import cc.linkedme.commons.mcq.writer.McqBaseWriter;
import cc.linkedme.data.model.DeepLink;
import jdk.nashorn.internal.ir.annotations.Reference;

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

    public void setApiMcqWriter(McqBaseWriter apiMcqWriter) {
        this.apiMcqWriter = apiMcqWriter;
    }
}
