package cc.linkedme.service.webapi;

import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;

import java.util.List;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public interface AppService {
    long createApp(AppParams appParams);
    List<AppInfo> getAppsByUserId(AppParams appParams);
    int deleteApp(AppParams appParams);
    AppInfo queryApp( AppParams appParams );
    int updateApp( AppParams appParams );
}
