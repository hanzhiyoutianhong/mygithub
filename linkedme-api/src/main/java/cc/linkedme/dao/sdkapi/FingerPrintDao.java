package cc.linkedme.dao.sdkapi;

import cc.linkedme.data.model.FingerPrintInfo;

/**
 * Created by vontroy on 16-7-4.
 */
public interface FingerPrintDao {
    int addFingerPrint(FingerPrintInfo fingerPrintInfo);

    int delFingerPrint(FingerPrintInfo fingerPrintInfo);

    FingerPrintInfo getFingerPrint(FingerPrintInfo fingerPrintInfo);
}
