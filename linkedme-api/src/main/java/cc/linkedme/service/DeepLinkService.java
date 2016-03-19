package cc.linkedme.service;

import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.dao.sdkapi.DeepLinkParamDao;
import cc.linkedme.data.model.DeepLink;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by LinkedME01 on 16/3/10.
 */

@Service
public class DeepLinkService {
    @Resource
    private DeepLinkDao deepLinkDao;

    @Resource
    private MemCacheTemplate<String> deepLinkParamMemCache;

    @Resource
    private DeepLinkParamDao deepLinkParamDao;

    public int addDeepLink(DeepLink deepLink) {
        int result = 0;
        // insert deepLink table;
        result = deepLinkDao.addDeepLink(deepLink);

        // insert deeplinkparam table
        result = deepLinkParamDao.addDeepLinkParam(deepLink);
        // counter
        return result;
    }

    public boolean addDeepLinkToCache(DeepLink deepLink) {
        boolean res = deepLinkParamMemCache.set(String.valueOf(deepLink.getDeeplinkId()), deepLink.getParams());
        return res;
    }

    public String getDeepLinkParam(long deepLinkId) {
        // 先从mc里取,如果没有取到,则从mysql里取
        // 从mysql里取到后,回中到mc
        String deepLinkParam = deepLinkParamMemCache.get(String.valueOf(deepLinkId));
        if (deepLinkParam == null) {
            deepLinkParam = deepLinkParamDao.getAddDeeplinkParam(deepLinkId);
            if (!Strings.isNullOrEmpty(deepLinkParam)) {
                deepLinkParamMemCache.set(String.valueOf(deepLinkId), deepLinkParam);
            }
        }
        return deepLinkParam;
    }
}
