package cc.linkedme.service;


import cc.linkedme.data.model.params.LMCloseParams;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;

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
