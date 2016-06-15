package cc.linkedme.service.sdkapi;

import cc.linkedme.data.model.params.*;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public interface LMSdkService {

    String webinit(WebInitParams webInitParams);

    void webClose(WebCloseParams webCloseParams);

    String install(InstallParams installParams);

    String url(UrlParams urlParams);

    String open(OpenParams openParams);

    void close(CloseParams CloseParams);

    String preInstall(PreInstallParams preInstallParams);

}
