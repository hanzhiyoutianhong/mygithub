package cc.linkedme.mcq;

import cc.linkedme.commons.mcq.writer.McqBaseWriter;
import cc.linkedme.data.model.ButtonCount;

import javax.annotation.Resource;

/**
 * Created by vontroy on 7/14/16.
 */
public class ButtonCountMsgPusher {
    @Resource
    private McqBaseWriter apiMcqWriter;

    public void addButtonCount(ButtonCount buttonCount) {
        String buttonCountMsg = MsgUtils.buttonCountMsgJson(buttonCount);
        apiMcqWriter.writeMsg(buttonCountMsg);
    }
}
