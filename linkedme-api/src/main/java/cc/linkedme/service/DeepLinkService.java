package cc.linkedme.service;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.commons.serialization.KryoSerializationUtil;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.data.model.params.UrlParams;
import com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by LinkedME01 on 16/3/10.
 */

@Service
public class DeepLinkService {
    @Resource
    private DeepLinkDao deepLinkDao;

    @Resource
    private MemCacheTemplate<byte[]> deepLinkParamMemCache;

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

    public String getUrlInfo( UrlParams urlParams ) {
        DeepLink deepLinkInfo = deepLinkDao.getUrlInfo(urlParams.deeplink_id, urlParams.app_id);

        if( deepLinkInfo == null )
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_REQUEST, "deep link id does not exist!");

        JSONObject resultJson = new JSONObject();

        resultJson.put("userid", urlParams.user_id);
        resultJson.put("app_id", urlParams.app_id);
        resultJson.put("link_label", deepLinkInfo.getLink_label());
        resultJson.put("ios_use_default", deepLinkInfo.isIos_use_default());
        resultJson.put("ios_custom_url", deepLinkInfo.getIos_custom_url());
        resultJson.put("android_use_default", deepLinkInfo.isAndroid_use_default() );
        resultJson.put("android_custom_url", deepLinkInfo.getAndroid_custom_url());
        resultJson.put("desktop_use_default", deepLinkInfo.isDesktop_use_default());
        resultJson.put("desktop_custom_url", deepLinkInfo.getDesktop_custom_url());

        String[] features = deepLinkInfo.getFeature().split(",");
        JSONArray featureArray = new JSONArray();
        for( int i = 0; i < features.length; i++ ) {
            featureArray.add( features[i] );
        }
        resultJson.put("feature", featureArray);

        String[] campaigns = deepLinkInfo.getCampaign().split(",");
        JSONArray campaignArray = new JSONArray();
        for( int i = 0; i < campaigns.length; i++ ) {
            campaignArray.add( features[i] );
        }
        resultJson.put("campaign", campaignArray);

        String[] stages = deepLinkInfo.getStage().split(",");
        JSONArray stageArray = new JSONArray();
        for( int i = 0; i < stages.length; i++ ) {
            stageArray.add( stages[i] );
        }
        resultJson.put("stage", stageArray);

        String[] channels = deepLinkInfo.getChannel().split(",");
        JSONArray channelArray = new JSONArray();
        for( int i = 0; i < channels.length; i++ ) {
            channelArray.add( channels[i] );
        }
        resultJson.put("channel", channelArray);

        String[] tags = deepLinkInfo.getTags().split(",");
        JSONArray tagArray = new JSONArray();
        for( int i = 0; i < tags.length; i++ ) {
            tagArray.add( tags[i] );
        }
        resultJson.put("tags", tagArray);// "tags":["tags1", "tags2"]

        resultJson.put("source", "dashboard");
        resultJson.put("params", deepLinkInfo.getParams());
        return resultJson.toString();
    }

    public boolean updateUrl( UrlParams urlParams ) {
        return deepLinkDao.updateUrlInfo(urlParams);
    }

}
