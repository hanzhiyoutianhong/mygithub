package cc.linkedme.dao.webapi.impl;

import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.DeepLinkDateCountDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.model.DeepLinkDateCount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/4/17.
 */
public class DeepLinkDateCountDaoImpl extends BaseDao implements DeepLinkDateCountDao {
    private static final String GET_DEEPLINK_DATE_COUNT = "GET_DEEPLINK_DATE_COUNT";

    @Override
    public List<DeepLinkDateCount> getDeepLinkDateCount(int appId, long deepLinkId, String startDate, String endDate) {
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", GET_DEEPLINK_DATE_COUNT, deepLinkId, deepLinkId);
        String sql = tableChannel.getSql();
        List<String> paramList = new ArrayList<String>();
        paramList.add(String.valueOf(deepLinkId));
        if (startDate != null) {
            sql += "and date >= ? ";
            paramList.add(startDate);
        }
        if (endDate != null) {
            sql += "and date <= ?";
            paramList.add(endDate);
        }
        List<DeepLinkDateCount> deepLinkDateCounts = new ArrayList<>();
        tableChannel.getJdbcTemplate().query(sql, paramList.toArray(), new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                DeepLinkDateCount count = new DeepLinkDateCount();
                count.setAppId(appId);
                count.setDeeplinkId(deepLinkId);
                count.setDate(resultSet.getString("date"));
                count.setClick(resultSet.getLong("click"));
                count.setOpen(resultSet.getLong("open"));
                count.setInstall(resultSet.getLong("install"));
                deepLinkDateCounts.add(count);
                return null;
            }
        });

        return deepLinkDateCounts;
    }
}
