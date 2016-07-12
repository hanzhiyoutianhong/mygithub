package cc.linkedme.service.sdkapi;


import cc.linkedme.data.model.params.CloseParams;
import cc.linkedme.data.model.params.InstallParams;
import cc.linkedme.data.model.params.OpenParams;
import cc.linkedme.data.model.params.PreInstallParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.data.model.params.WebCloseParams;
import cc.linkedme.data.model.params.WebInitParams;

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

}
