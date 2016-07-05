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

    public void updateFingerPrint(FingerPrintInfo oldFingerPrintInfo, FingerPrintInfo newFingerPrintInfo) {
        String fingerPrintInfoMsg;
        if(newFingerPrintInfo.getIdentityId() == 0 ) {
            fingerPrintInfoMsg = MsgUtils.addFingerPrintMsgJson(oldFingerPrintInfo);
        } else {
            fingerPrintInfoMsg = MsgUtils.updateFingerPrintMsgJson(oldFingerPrintInfo, newFingerPrintInfo);
        }
        apiMcqWriter.writeMsg(fingerPrintInfoMsg);
    }
}
