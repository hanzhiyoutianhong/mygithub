package cc.linkedme.mcq;

import cc.linkedme.commons.mcq.writer.McqBaseWriter;
import cc.linkedme.data.model.FingerPrintInfo;

import javax.annotation.Resource;

/**
 * Created by vontroy on 16-7-4.
 */
public class FingerPrintMsgPusher {

    @Resource
    private McqBaseWriter apiMcqWriter;

    public void addFingerPrint(FingerPrintInfo fingerPrintInfo) {
        String fingerPrintInfoMsg = MsgUtils.toFingerPrintMsgJson(fingerPrintInfo);
        apiMcqWriter.writeMsg(fingerPrintInfoMsg);
    }
}
