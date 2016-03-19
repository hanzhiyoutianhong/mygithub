package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;

import java.util.List;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public interface AppDao {
    public int insertApp(AppInfo appInfo);
    public List<AppInfo> getAppsByUserId(long userId);
    int delApp(AppParams appParams);
}
