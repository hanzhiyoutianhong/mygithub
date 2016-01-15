package cc.linkedme.cache;

import cc.linkedme.model.LMApplicationEntity;
import cc.linkedme.service.LMApplicationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qipo on 15/9/3.
 */
public class LMApplicationCache {

    public LMApplicationService applicationService;
    public static Map<String, LMApplicationEntity> applicationMap = new HashMap<String, LMApplicationEntity>();

    /**
     * spring bean init function
     */

    public void init() {
        List<LMApplicationEntity> rowsList = applicationService.getAllApplication();
        for (int i = 0; i < rowsList.size(); i++) {
            LMApplicationEntity lmApplicationEntity = rowsList.get(i);
            String appKeyLive = lmApplicationEntity.getAppKeyLive();
            String appTestLive = lmApplicationEntity.getAppKeyTest();
            applicationMap.put(appTestLive, lmApplicationEntity);
            applicationMap.put(appKeyLive, lmApplicationEntity);
        }
    }

    /**
     * construction function
     */

    public LMApplicationCache() {
    }

    /**
     * get and set function
     */

    public void setApplicationService(LMApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public Map<String, LMApplicationEntity> getApplicationMap() {
        return applicationMap;
    }

    public static void setApplicationMap(Map<String, LMApplicationEntity> applicationMap) {
        LMApplicationCache.applicationMap = applicationMap;
    }
}