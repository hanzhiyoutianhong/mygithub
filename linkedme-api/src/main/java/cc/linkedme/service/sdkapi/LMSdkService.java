package cc.linkedme.service.sdkapi;

import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.params.LMUrlParams;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public interface LMSdkService {

    int install(ClientInfo clientInfo);

    String url(LMUrlParams lmUrlParams);

    String preInstall(String linkClickId);

}
