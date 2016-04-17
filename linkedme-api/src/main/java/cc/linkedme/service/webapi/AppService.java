package cc.linkedme.service.webapi;

import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.UrlTagsInfo;
import cc.linkedme.data.model.params.AppParams;
import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider;

import java.util.List;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public interface AppService {
    long createApp(AppParams appParams);

    List<AppInfo> getAppsByUserId(AppParams appParams);

    int deleteApp(AppParams appParams);

    AppInfo queryApp(AppParams appParams);

    AppInfo getAppById(long appId);

    int updateApp(AppParams appParams);

    UrlTagsInfo getUrlTags(AppParams appParams);
    
    String uploadImg(AppParams appParams, String imagePath);
}
