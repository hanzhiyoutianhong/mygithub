package cc.linkedme.dao.webapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.BtnCountDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.ButtonCount;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/4/12.
 */
public class BtnCountDaoImpl extends BaseDao implements BtnCountDao {
    private static final String GET_CONSUMER_APP_COUNT = "GET_CONSUMER_APP_COUNT";
    private static final String GET_CONSUMER_APP_INCOME = "GET_CONSUMER_APP_INCOME";
    private static final String ADD_BUTTON_COUNT = "ADD_BUTTON_COUNT";

    public int addButtonCount(ButtonCount buttonCount) {
        String countType = buttonCount.getCountType();

        String id = buttonCount.getBtnId() + "_" + buttonCount.getConsumerId() + "_" + buttonCount.getDate();
        TableChannel tableChannel = tableContainer.getTableChannel("btnCount", ADD_BUTTON_COUNT, buttonCount.getAppId(), Util.timeStrToDate(buttonCount.getDate()));

        String sql = tableChannel.getSql() + " (id, app_id, btn_id, consumer_id, date, " + countType + ") values(?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " + countType + " = " + countType + " + values("
                + countType + ")";
        int result = 0;
        try {
            result = tableChannel.getJdbcTemplate().update(sql,
                    new Object[] {id, buttonCount.getAppId(), buttonCount.getBtnId(), buttonCount.getConsumerId(), buttonCount.getDate(), buttonCount.getCountValue()});
        } catch (Exception e) {
            ApiLogger.error("DeepLinkDateCountDaoImpl.addDeepLinkDateCount add count failed!", e);
        }
        return result;
    }

    @Override
    public List<ButtonCount> getConsumerIncome(long appId, long consumerAppId, String startDate, String endDate) {
        TableChannel tableChannel = tableContainer.getTableChannel("btnCount", GET_CONSUMER_APP_INCOME, 0L, 0L);
        String sql = tableChannel.getSql();
        List<Object> paramList = new ArrayList<>();
        paramList.add(appId);
        paramList.add(consumerAppId);
        if (!Strings.isNullOrEmpty(startDate)) {
            sql = sql + " and date >= ?";
            paramList.add(startDate);
        }

        if (!Strings.isNullOrEmpty(endDate)) {
            sql = sql + " and date <= ?";
            paramList.add(endDate);
        }
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<ButtonCount> buttonCounts = new ArrayList<>();
        jdbcTemplate.query(sql, paramList.toArray(), new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                ButtonCount buttonCount = new ButtonCount();
                buttonCount.setAppId(appId);
                buttonCount.setBtnId(rs.getString("btn_id"));
                buttonCount.setDate(rs.getString("date"));
                buttonCount.setIosIncome(rs.getInt("ios_income"));
                buttonCount.setAndroidIncome(rs.getInt("android_income"));
                buttonCount.setWebIncome(rs.getInt("web_income"));
                buttonCount.setOtherIncome(rs.getInt("other_income"));
                buttonCount.setTotalIncome(buttonCount.getAndroidIncome() + buttonCount.getIosIncome() + buttonCount.getWebIncome() + buttonCount.getOtherIncome());
                buttonCounts.add(buttonCount);
                return null;
            }
        });
        return buttonCounts;
    }

    @Override
    public List<ButtonCount> getButtonCounts(long appId, String btnId, String startDate, String endDate) {
        TableChannel tableChannel = tableContainer.getTableChannel("btnCount", GET_CONSUMER_APP_COUNT, 0L, 0L);
        String sql = tableChannel.getSql();
        List<Object> paramList = new ArrayList<>();
        paramList.add(appId);
        paramList.add(btnId);
        if (!Strings.isNullOrEmpty(startDate)) {
            sql = sql + " and date >= ?";
            paramList.add(startDate);
        }

        if (!Strings.isNullOrEmpty(endDate)) {
            sql = sql + " and date <= ?";
            paramList.add(endDate);
        }
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<ButtonCount> buttonCounts = new ArrayList<>();
        jdbcTemplate.query(sql, paramList.toArray(), new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                ButtonCount buttonCount = new ButtonCount();
                buttonCount.setAppId(appId);
                buttonCount.setBtnId(rs.getString("btn_id"));
                buttonCount.setDate(rs.getString("date"));
                buttonCount.setIosViewCount(rs.getInt("ios_view_count"));
                buttonCount.setAndroidViewCount(rs.getInt("android_view_count"));
                buttonCount.setWebViewCount(rs.getInt("web_view_count"));
                buttonCount.setOtherViewCount(rs.getInt("other_view_count"));
                buttonCount.setIosClickCount(rs.getInt("ios_click_count"));
                buttonCount.setAndroidClickCount(rs.getInt("android_click_count"));
                buttonCount.setWebClickCount(rs.getInt("web_click_count"));
                buttonCount.setOtherClickCount(rs.getInt("other_click_count"));
                buttonCount.setIosOpenCount(rs.getInt("ios_open_count"));
                buttonCount.setAndroidOpenCount(rs.getInt("android_open_count"));
                buttonCount.setWebOpenCount(rs.getInt("web_open_count"));
                buttonCount.setOtherOpenCount(rs.getInt("other_open_count"));
                buttonCount.setIosOrderCount(rs.getInt("ios_order_count"));
                buttonCount.setAndroidOrderCount(rs.getInt("android_order_count"));
                buttonCount.setWebOrderCount(rs.getInt("web_order_count"));
                buttonCount.setOtherOrderCount(rs.getInt("other_order_count"));
                buttonCount.setIosIncome(rs.getInt("ios_income"));
                buttonCount.setAndroidIncome(rs.getInt("android_income"));
                buttonCount.setWebIncome(rs.getInt("web_income"));
                buttonCount.setOtherIncome(rs.getInt("other_income"));
                buttonCounts.add(buttonCount);
                return null;
            }
        });
        return buttonCounts;
    }
}
