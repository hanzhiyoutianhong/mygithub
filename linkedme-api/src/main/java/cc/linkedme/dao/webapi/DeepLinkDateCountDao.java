package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.DeepLinkDateCount;

import java.util.List;

/**
 * Created by LinkedME01 on 16/4/17.
 */
public interface DeepLinkDateCountDao {
    DeepLinkDateCount getDeepLinkDateCount(int appId, long deepLinkId);
}
