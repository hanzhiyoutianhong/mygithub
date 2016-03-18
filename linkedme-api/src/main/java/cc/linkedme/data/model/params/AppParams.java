package cc.linkedme.data.model.params;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppParams {
    public String appName;
    public long userId;

    public AppParams() {}

    public AppParams(String appName, long userId) {
        this.appName = appName;
        this.userId = userId;
    }

}
