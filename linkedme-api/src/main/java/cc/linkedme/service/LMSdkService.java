package cc.linkedme.service;

import cc.linkedme.common.params.LMCloseParams;
import cc.linkedme.common.params.LMInstallParams;
import cc.linkedme.common.params.LMOpenParams;
import cc.linkedme.common.params.LMUrlParams;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public interface LMSdkService {

    String install(LMInstallParams lmInstallParams);

    String open(LMOpenParams lmOpenParams);

    String close(LMCloseParams lmCloseParams);

    String url(LMUrlParams lmUrlParams);

    String preInstall(String linkClickId);
}
