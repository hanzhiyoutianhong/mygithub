package cc.linkedme.dao.sdkapi;

import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.AppAnalysisParams;

import java.util.List;

/**
 * Created by LinkedME07 on 16/7/27.
 */
public interface AppAnalysisDao {

    int addAppBundle(String appId, String appName, String appIcon, String genres, String company, String lastUpdateTime, int isOnline);

    List<String> getAppIds(Object[] params, String type);

    List<AppAnalysisParams> getApps(String company);

    int switchApp(String appId, String company, String flag);

    int updateStatus(String appId, String company, String status);

    List<AppAnalysisParams> getAppsWithSDK(String company, String startDate, String endDate);

    int count(String company, String startDate, String endDate, String type);

    List<UserInfo> getBundleIdAndUserInfo(Object[] params, String type);
}
