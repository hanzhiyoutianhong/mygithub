package cc.linkedme.service.sdkapi;

import cc.linkedme.data.model.params.CloseParams;
import cc.linkedme.data.model.params.InstallParams;
import cc.linkedme.data.model.params.OpenParams;
import cc.linkedme.data.model.params.PreInstallParams;
import cc.linkedme.data.model.params.UrlParams;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public interface LMSdkService {

    String install(InstallParams installParams);

    String url(UrlParams urlParams);

    String open(OpenParams openParams);

    void close(CloseParams CloseParams);

    String preInstall(PreInstallParams preInstallParams);

}
