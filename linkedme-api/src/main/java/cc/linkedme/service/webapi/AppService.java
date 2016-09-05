package cc.linkedme.service.webapi;

import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.UrlTagsInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.data.model.params.DashboardUrlParams;
import cc.linkedme.data.model.params.UrlParams;

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

    String uploadImg(AppParams appParams);

    byte[] getAppImg(int appId, String type);

    boolean isAppNameExist(AppParams appParams);

    
    /**
     * 校验android uri scheme是否为预留或者注册过
     * @return　如果为uri scheme为预留或者被注册过，返回true；否则，返回false
     */
    public boolean isAndroidUriSchemeExsit(String androidUriScheme, long appId);


    /**
     * 校验ios uri scheme是否为预留或者注册过 
     * @return　如果为uri scheme为预留或者被注册过，返回true；否则，返回false
     */
    public boolean isIosUriSchemeExsit(String iosUriScheme, long appId);


    /**
     * 校验android　package name是否为预留或者注册过
     * @return　如果为预留或者被注册过，返回true；否则，返回false
     */
    public boolean isAndroidPackageNameExist(String androidPackageName, long appId);


    /**
     * 校验ios bundle id是否被注册过
     * @return　如果被注册过，返回true；否则，返回false
     */
    public boolean isIosBundleIdExist(String iosBundleId, long appId);

    /**
     *校验推广名称是否已经存在
     * @return 如果已经存在，返回true；否则返回false; 推广名称为空返回false
     */
    public boolean validPromotionName(UrlParams urlParams);

}
