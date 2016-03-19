package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.sdkapi.DeepLinkParamDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.DeepLink;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/3/19.
 */
public class DeepLinkParamDaoImpl extends BaseDao implements DeepLinkParamDao {
    private static final String ADD_DEEPLINK_PARAM = "ADD_DEEPLINK_PARAM";
    private static final String GET_DEEPLINK_PARAM = "GET_DEEPLINK_PARAM";

    public int addDeepLinkParam(DeepLink deepLink) {
        int result = 0;
        if (deepLink == null) {
            ApiLogger.error("DeepLinkDaoImpl.addDeepLink Deeplink is null, add failed");
        }
        long deepLinkId = deepLink.getDeeplinkId();
        String deepLinkParam = deepLink.getParams();
        TableChannel tableChannel = tableContainer.getTableChannel("deepLinkParam", ADD_DEEPLINK_PARAM, deepLinkId, deepLinkId);
        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {deepLinkId, deepLinkParam});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.info(new StringBuilder(128).append("Duplicate insert deepLinkParam, id=").append(deepLinkId));
            } else {
                throw e;
            }
        }
        return result;
    }

    public String getAddDeeplinkParam(long deepLinkId) {
        if (deepLinkId <= 0) {
            return null;
        }
        TableChannel tableChannel = tableContainer.getTableChannel("deepLinkParam", GET_DEEPLINK_PARAM, deepLinkId, deepLinkId);
        String param =
                (String) tableChannel.getJdbcTemplate().query(tableChannel.getSql(), new Object[] {deepLinkId}, new ResultSetExtractor() {
                    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            return rs.getString("param");
                        }
                        return null;
                    }
                });
        return param;
    }
}
