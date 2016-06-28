package cc.linkedme.dao.webapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.DeepLinkDateCountDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.model.DeepLinkDateCount;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LinkedME01 on 16/4/17.
 */
public class DeepLinkDateCountDaoImpl extends BaseDao implements DeepLinkDateCountDao {
    private static final String GET_DEEPLINK_DATE_COUNT_BY_ID = "GET_DEEPLINK_DATE_COUNT_BY_ID";
    private static final String GET_DEEPLINKS_DATE_COUNTS_BY_APPID = "GET_DEEPLINKS_DATE_COUNTS_BY_APPID";
    private static final String ADD_DEEPLINKS_DATE_COUNTS = "ADD_DEEPLINKS_DATE_COUNTS";

    @Override
    public List<DeepLinkDateCount> getDeepLinkDateCount(int appId, long deepLinkId, String startDate, String endDate) {
        TableChannel tableChannel =
                tableContainer.getTableChannel("deepLinkDateCount", GET_DEEPLINK_DATE_COUNT_BY_ID, deepLinkId, deepLinkId);
        String sql = tableChannel.getSql();
        List<String> paramList = new ArrayList<>();
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

    @Override
    public int addDeepLinksDateCounts(String date, DeepLinkDateCount[] deepLinksDateCounts) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date indexDate;
        try {
            indexDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            ApiLogger.error("date is invalid, date = " + date);
            return 0;
        }

        String countDate = simpleDateFormat2.format(indexDate);

        TableChannel tableChannel = tableContainer.getTableChannel("deepLinkDateCount", ADD_DEEPLINKS_DATE_COUNTS, 0L, 0L);
        String sql = tableChannel.getSql();

        int[] multiResult = tableChannel.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                DeepLinkDateCount deepLinkDateCount = deepLinksDateCounts[i];
                preparedStatement.setInt(1, deepLinkDateCount.getAppId());
                preparedStatement.setLong(2, deepLinkDateCount.getDeeplinkId());
                preparedStatement.setString(3, countDate);
                preparedStatement.setLong(4, deepLinkDateCount.getClick());
                preparedStatement.setLong(5, deepLinkDateCount.getOpen());
                preparedStatement.setLong(6, deepLinkDateCount.getInstall());
                preparedStatement.setLong(7, deepLinkDateCount.getIosClick());
                preparedStatement.setLong(8, deepLinkDateCount.getIosOpen());
                preparedStatement.setLong(9, deepLinkDateCount.getIosInstall());
                preparedStatement.setLong(10, deepLinkDateCount.getAdrClick());
                preparedStatement.setLong(11, deepLinkDateCount.getAdrOpen());
                preparedStatement.setLong(12, deepLinkDateCount.getAdrInstall());
                preparedStatement.setLong(13, deepLinkDateCount.getPcClick());
                preparedStatement.setLong(14, deepLinkDateCount.getPcIosScan());
                preparedStatement.setLong(15, deepLinkDateCount.getPcAdrScan());
                preparedStatement.setLong(16, deepLinkDateCount.getPcIosOpen());
                preparedStatement.setLong(17, deepLinkDateCount.getPcAdrOpen());
                preparedStatement.setLong(18, deepLinkDateCount.getPcIosInstall());
                preparedStatement.setLong(19, deepLinkDateCount.getPcAdrInstall());
                preparedStatement.setString(20, date + "_" + deepLinkDateCount.getDeeplinkId());
            }

            @Override
            public int getBatchSize() {
                return deepLinksDateCounts.length;
            }
        });

        int result = 0;
        for (int i : multiResult) {
            if (i > 0) {
                result++;
            }
        }
        return result;
    }

    public int addDeepLinkDateCount(DeepLinkDateCount deepLinkDateCount, String countType) {
        String totalCountType;
        String[] arr = countType.split("_");
        if ("click".equals(arr[arr.length - 1]) || "scan".equals(arr[arr.length - 1])) {
            totalCountType = "click";
        } else {
            totalCountType = arr[arr.length - 1];
        }

        String date = deepLinkDateCount.getDate().replace("-", "");
        String id = date + "_" + deepLinkDateCount.getDeeplinkId();

        String sql = "insert into count_0.url_count_1606 (id, app_id, deeplink_id, date, " + totalCountType + ", " + countType
                + ") values(?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " + totalCountType + " = " + totalCountType + " + values("
                + totalCountType + "), " + countType + " = " + countType + " + values(" + countType + ")";
        TableChannel tableChannel = tableContainer.getTableChannel("deepLinkDateCount", ADD_DEEPLINKS_DATE_COUNTS, 0L, 0L);
        int result = 0;
        try {
            result = tableChannel.getJdbcTemplate().update(sql,
                    new Object[] {id, deepLinkDateCount.getAppId(), deepLinkDateCount.getDeeplinkId(), deepLinkDateCount.getDate(), 1, 1});
        } catch (Exception e) {
            ApiLogger.error("DeepLinkDateCountDaoImpl.addDeepLinkDateCount add count failed!", e);
        }
        return result;
    }
}
