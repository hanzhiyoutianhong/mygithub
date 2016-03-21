package cc.linkedme.data.model.params;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppParams {
    public long appId;
    public long userId;
    public String appName;
    public String appLiveKey;
    public String appTestKey;

    public String iosUriScheme;
    public String iosNotUrl;
    public String iosStoreUrl;
    public String iosCustomUrl;
    public String iosBundleId;
    public String iosPrefix;
    public String iosTeamId;

    public String androidUriScheme;
    public String androidNotUrl;
    public String googlePlayUrl;
    public String androidCustomUrl;
    public String androidPackageName;
    public String androidPrefix;
    public int iosAndroidFlag;

    public String desktopUrl;

    public AppParams() {}

    public AppParams(String appName, long userId)
    {
        this.appName = appName;
        this.userId = userId;
    }


}
