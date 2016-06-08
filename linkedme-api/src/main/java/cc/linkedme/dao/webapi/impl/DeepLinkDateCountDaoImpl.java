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
    private static final String GET_DEEPLINK_DATE_COUNT_BY_ID = "GET_DEEPLINK_DATE_COUNT_BY_ID";
    private static final String GET_DEEPLINKS_DATE_COUNTS_BY_APPID = "GET_DEEPLINKS_DATE_COUNTS_BY_APPID";

    @Override
    public List<DeepLinkDateCount> getDeepLinkDateCount(int appId, long deepLinkId, String startDate, String endDate) {
        TableChannel tableChannel = tableContainer.getTableChannel("deepLinkDateCount", GET_DEEPLINK_DATE_COUNT_BY_ID, deepLinkId, deepLinkId);
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
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                DeepLinkDateCount count = new DeepLinkDateCount();
                count.setAppId(appId);
                count.setDeeplinkId(deepLinkId);
                count.setDate(rs.getString("date"));
                count.setClick(rs.getLong("click"));
                count.setOpen(rs.getLong("open"));
                count.setInstall(rs.getLong("install"));

                count.setIosClick(rs.getLong("ios_click"));
                count.setIosOpen(rs.getLong("ios_open"));
                count.setIosInstall(rs.getLong("ios_install"));

                count.setAdrClick(rs.getLong("adr_click"));
                count.setAdrOpen(rs.getLong("adr_open"));
                count.setAdrInstall(rs.getLong("adr_install"));

                count.setPcClick(rs.getLong("pc_click"));
                count.setPcIosScan(rs.getLong("pc_ios_scan"));
                count.setPcAdrScan(rs.getLong("pc_adr_scan"));
                count.setPcIosOpen(rs.getLong("pc_ios_open"));
                count.setPcAdrOpen(rs.getLong("pc_adr_open"));
                count.setPcIosInstall(rs.getLong("pc_ios_install"));
                count.setPcAdrInstall(rs.getLong("pc_adr_install"));

                deepLinkDateCounts.add(count);
                return null;
            }
        });

        return deepLinkDateCounts;
    }

    @Override
    public List<DeepLinkDateCount> getDeepLinksDateCounts(int appId, String startDate, String endDate) {
        TableChannel tableChannel = tableContainer.getTableChannel("deepLinkDateCount", GET_DEEPLINKS_DATE_COUNTS_BY_APPID, 0L, 0L);
        String sql = tableChannel.getSql();
        List<String> paramList = new ArrayList<String>();
        paramList.add(String.valueOf(appId));
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
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                DeepLinkDateCount count = new DeepLinkDateCount();
                count.setAppId(appId);
                count.setDeeplinkId(rs.getLong("deeplink_id"));
                count.setDate(rs.getString("date"));
                count.setClick(rs.getLong("click"));
                count.setOpen(rs.getLong("open"));
                count.setInstall(rs.getLong("install"));

                count.setIosClick(rs.getLong("ios_click"));
                count.setIosOpen(rs.getLong("ios_open"));
                count.setIosInstall(rs.getLong("ios_install"));

                count.setAdrClick(rs.getLong("adr_click"));
                count.setAdrOpen(rs.getLong("adr_open"));
                count.setAdrInstall(rs.getLong("adr_install"));

                count.setPcClick(rs.getLong("pc_click"));
                count.setPcIosScan(rs.getLong("pc_ios_scan"));
                count.setPcAdrScan(rs.getLong("pc_adr_scan"));
                count.setPcIosOpen(rs.getLong("pc_ios_open"));
                count.setPcAdrOpen(rs.getLong("pc_adr_open"));
                count.setPcIosInstall(rs.getLong("pc_ios_install"));
                count.setPcAdrInstall(rs.getLong("pc_adr_install"));
                deepLinkDateCounts.add(count);
                return null;
            }
        });

        return deepLinkDateCounts;
    }
}
