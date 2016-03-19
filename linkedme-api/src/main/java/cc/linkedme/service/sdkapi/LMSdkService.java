package cc.linkedme.service.sdkapi;

import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public interface LMSdkService {

    String install(LMInstallParams lmInstallParams);

    String url(LMUrlParams lmUrlParams);

    String open(LMOpenParams lmOpenParams);

    String preInstall(String linkClickId);

}
