package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.DeepLinkDateCount;

import java.util.List;

/**
 * Created by LinkedME01 on 16/4/17.
 */
public interface DeepLinkDateCountDao {
    List<DeepLinkDateCount> getDeepLinkDateCount(int appId, long deepLinkId, String startDate, String endDate);

    List<DeepLinkDateCount> getDeepLinksDateCounts(int appId, String startDate, String endDate);

    int addDeepLinksDateCounts(String date, DeepLinkDateCount[] deepLinksDateCounts);

    int addDeepLinkDateCount(DeepLinkDateCount deepLinkDateCount, String countType);

    boolean deleteDeepLinkDateCounts(long appId,long deepLinkId);
}
