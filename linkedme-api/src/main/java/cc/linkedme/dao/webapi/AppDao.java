package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.UrlTagsInfo;
import cc.linkedme.data.model.params.AppParams;

import java.util.List;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public interface AppDao {
    int insertApp(AppInfo appInfo);

    List<AppInfo> getAppsByUserId(AppParams appParams);

    int delApp(AppParams appParams);

    AppInfo getAppByName(long userId, String appName);

    AppInfo getAppByAppId(long appId);

    int updateApp(final AppParams appParams);

    List<UrlTagsInfo> getUrlTagsByAppId(AppParams appParams);

    boolean configUrlTags(AppParams appParams);

    int uploadImg(AppParams appParams, byte[] picDatas);

    byte[] getAppImg(int appId);
}
