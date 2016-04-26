package cc.linkedme.service.sdkapi;

import cc.linkedme.data.model.AppListInfo;

import java.util.ArrayList;

/**
 * Created by Vontroy on 16/4/24.
 */
public interface AppListService {
    int addAppList(ArrayList<AppListInfo> appListInfos);
}
