package cc.linkedme.dao.sdkapi;

import cc.linkedme.data.model.FingerPrintInfo;

/**
 * Created by vontroy on 16-7-4.
 */
public interface FingerPrintDao {
    int addFingerPrint(FingerPrintInfo fingerPrintInfo, int val);

    FingerPrintInfo getFingerPrint(FingerPrintInfo fingerPrintInfo);

    int setValidStatusById(FingerPrintInfo fingerPrintInfo, int val);
}
