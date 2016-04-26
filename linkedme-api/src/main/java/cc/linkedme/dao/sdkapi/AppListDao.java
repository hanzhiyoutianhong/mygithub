package cc.linkedme.dao.sdkapi;

import cc.linkedme.data.model.AppListInfo;

import java.util.ArrayList;

/**
 * Created by Vontroy on 16/4/24.
 */
public interface AppListDao {
    int addAppList(AppListInfo appListInfo);

    ArrayList<AppListInfo> queryAppList(long identityId );
}
