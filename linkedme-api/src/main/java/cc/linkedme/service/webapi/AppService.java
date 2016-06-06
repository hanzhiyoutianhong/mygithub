package cc.linkedme.service.webapi;

import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.UrlTagsInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.data.model.params.DashboardUrlParams;
import cc.linkedme.data.model.params.UrlParams;
import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider;

import java.util.List;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public interface AppService {
    long createApp(AppParams appParams);

    boolean setAppInfoToCache(AppInfo appInfo);

    List<AppInfo> getAppsByUserId(AppParams appParams);

    int deleteApp(AppParams appParams);

    AppInfo getAppById(long appId);

    int updateApp(AppParams appParams);

    List<UrlTagsInfo> getUrlTags(AppParams appParams);

    boolean configUrlTags(AppParams appParams);

    void addUrlTags(DashboardUrlParams urlParams);

    String uploadImg(AppParams appParams, String imagePath);
}
