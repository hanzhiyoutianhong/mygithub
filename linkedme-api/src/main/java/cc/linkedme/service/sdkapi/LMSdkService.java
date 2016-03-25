package cc.linkedme.service.sdkapi;

import cc.linkedme.data.model.params.CloseParams;
import cc.linkedme.data.model.params.InstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.UrlParams;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public interface LMSdkService {

    String install(InstallParams installParams);

    String url(UrlParams urlParams);

    String open(LMOpenParams lmOpenParams);

    void close(CloseParams CloseParams);

    String preInstall(String linkClickId);

}
