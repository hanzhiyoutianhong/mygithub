package cc.linkedme.dao.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.DeepLinkDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.model.DeepLink;
import org.springframework.dao.DataAccessException;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class DeepLinkDaoImpl extends BaseDao implements DeepLinkDao {

    public int addDeepLink(DeepLink deepLink) {
        int result = 0;
        if (deepLink == null) {
            ApiLogger.error("can't save null attitude, with null source status or null auther.");
        }
        long deepLink_id = deepLink.getDeeplink_id();
        TableChannel channel = tableContainer.getTableChannel("", "", deepLink_id, deepLink_id);
        try {
            result += channel.getJdbcTemplate().update(channel.getSql(),
                    new Object[]{deepLink_id});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.info(new StringBuilder(128).append("Duplicate insert deepLink, id=").append(deepLink_id));
            } else {
                throw e;
            }
        }
        return result;
    }
}
