package cc.linkedme.dao.sdkapi;

import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.params.DashboardUrlParams;

import java.util.List;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public interface DeepLinkDao {
    int addDeepLink(DeepLink deepLink);

    DeepLink getDeepLinkInfo(long deepLinkId, long appId);

    List<DeepLink> getDeepLinks(long appid, String start_date, String end_date, String feature, String campaign, String stage,
            String channel, String tag, String promotionName, String source, boolean unique, String type);

    boolean deleteDeepLink(long deepLinkId, long appId);

    DeepLink getUrlInfo( long deepLinkId, long appid);

    boolean updateUrlInfo(DashboardUrlParams dashboardUrlParams);

}
