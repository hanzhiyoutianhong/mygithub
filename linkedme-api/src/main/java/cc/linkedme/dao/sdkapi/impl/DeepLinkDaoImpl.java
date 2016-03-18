package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.dao.sdkapi.DeepLinkDao;
import org.springframework.dao.DataAccessException;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.model.DeepLink;

import java.util.Date;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class DeepLinkDaoImpl extends BaseDao implements DeepLinkDao {
    private static final String ADD_DEEPLINK = "ADD_DEEPLINK";

    public int addDeepLink(DeepLink deepLink) {
        int result = 0;
        if (deepLink == null) {
            ApiLogger.error("DeepLinkDaoImpl.addDeepLink Deeplink is null, add failed");
        }
        long deeplink_id = deepLink.getDeeplink_id();
        String deeplink_md5 = deepLink.getDeeplink_md5();
        long appid = deepLink.getAppid();
        String linkedme_key = deepLink.getLinkedme_key();
        String identity_id = deepLink.getIdentity_id();
        String tags = deepLink.getTags();
        String alias = deepLink.getAlias();
        String channel = deepLink.getChannel();
        String feature = deepLink.getFeature();
        String stage = deepLink.getStage();
        String campaign = deepLink.getCampaign();
        String params = deepLink.getParams();
        String source = deepLink.getSource();
        String sdk_version = deepLink.getSdk_version();
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", ADD_DEEPLINK, appid, new Date());
        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {deeplink_id, deeplink_md5, linkedme_key,
                    identity_id, tags, alias, channel, feature, stage, campaign, params, source, sdk_version});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.info(new StringBuilder(128).append("Duplicate insert deepLink, id=").append(deeplink_id));
            } else {
                throw e;
            }
        }
        return result;
    }
}
