package cc.linkedme.dao.webapi.impl;

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
                buttonCount.setIncome(rs.getFloat("income"));
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
                buttonCount.setViewCount(rs.getInt("view_count"));
                buttonCount.setClickCount(rs.getInt("click_count"));
                buttonCount.setOpenAppCount(rs.getInt("open_app_count"));
                buttonCount.setOpenWebCount(rs.getInt("open_web_count"));
                buttonCount.setOpenOtherCount(rs.getInt("open_other_count"));
                buttonCount.setOrderCount(rs.getInt("order_count"));
                buttonCount.setIncome(rs.getFloat("income"));
                buttonCounts.add(buttonCount);
                return null;
            }
        });
        return buttonCounts;
    }
}
