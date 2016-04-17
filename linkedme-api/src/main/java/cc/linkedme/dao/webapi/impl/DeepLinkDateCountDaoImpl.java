package cc.linkedme.dao.webapi.impl;

import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.DeepLinkDateCountDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.model.DeepLinkDateCount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LinkedME01 on 16/4/17.
 */
public class DeepLinkDateCountDaoImpl extends BaseDao implements DeepLinkDateCountDao {
    private static final String GET_DEEPLINK_DATE_COUNT = "GET_DEEPLINK_DATE_COUNT";

    @Override
    public DeepLinkDateCount getDeepLinkDateCount(int appId, long deepLinkId) {
        DeepLinkDateCount count = new DeepLinkDateCount();
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", GET_DEEPLINK_DATE_COUNT, deepLinkId, deepLinkId);
        tableChannel.getJdbcTemplate().query(tableChannel.getSql(), new Object[] {deepLinkId}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                count.setAppId(appId);
                count.setDeeplinkId(deepLinkId);
                count.setDate(resultSet.getDate("date"));
                count.setClick(resultSet.getLong("click"));
                count.setOpen(resultSet.getLong("open"));
                count.setInstall(resultSet.getLong("install"));
                return null;
            }
        });
        if (count.getDeeplinkId() == 0) {
            return null;
        }
        return count;
    }
}
