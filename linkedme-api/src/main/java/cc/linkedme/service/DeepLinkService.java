package cc.linkedme.service;

import cc.linkedme.commons.counter.component.CountComponent;
import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.commons.serialization.KryoSerializationUtil;
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
    private MemCacheTemplate<byte[]> deepLinkParamMemCache;

    @Resource
    private CountComponent deepLinkCountComponent;

    public int addDeepLink(DeepLink deepLink) {
        int result = 0;
        // insert deepLink table;
        result = deepLinkDao.addDeepLink(deepLink);
        return result;
    }

    public boolean addDeepLinkToCache(DeepLink deepLink) {
        byte[] b = KryoSerializationUtil.serializeObj(deepLink);
        boolean res = deepLinkParamMemCache.set(String.valueOf(deepLink.getDeeplinkId()), b);
        return res;
    }

    /**
     * 根据deepLinkId和appId获取deepLink的信息
     */
    public DeepLink getDeepLinkInfo(long deepLinkId, long appId) {
        return deepLinkDao.getDeepLinkInfo(deepLinkId, appId);
    }

    public DeepLink getDeepLinkParam(long deepLinkId, long appId) {
        // 先从mc里取,如果没有取到,则从mysql里取
        // 从mysql里取到后,回中到mc
        DeepLink deepLink;
        byte[] deepLinkByteArr = deepLinkParamMemCache.get(String.valueOf(deepLinkId));
        if (deepLinkByteArr != null && deepLinkByteArr.length > 0) {
            deepLink = KryoSerializationUtil.deserializeObj(deepLinkByteArr, DeepLink.class);
            return deepLink;
        }

        deepLink = deepLinkDao.getDeepLinkInfo(deepLinkId, appId);
        if (deepLink != null && deepLink.getDeeplinkId() > 0) {
            deepLinkParamMemCache.set(String.valueOf(deepLinkId), KryoSerializationUtil.serializeObj(deepLink));
            return deepLink;
        }
        return null;
    }

    public boolean deleteDeepLink(long deepLinkId, long appId) {
        boolean result = deepLinkDao.deleteDeepLink(deepLinkId, appId);
        return result;
    }
}
