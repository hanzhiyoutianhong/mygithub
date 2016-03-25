package cc.linkedme.dao.sdkapi;

import cc.linkedme.data.model.DeepLink;

import java.util.List;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public interface DeepLinkDao {
    int addDeepLink(DeepLink deepLink);

    List<DeepLink> getDeepLinks(long appid, String start_date, String end_date, String feature, String campaign, String stage,
            String channel, String tag, boolean unique);
}
