package cc.linkedme.dao.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.ButtonDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.ButtonInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/4/7.
 */
public class ButtonDaoImpl extends BaseDao implements ButtonDao {
    private static final String ADD_BUTTON = "ADD_BUTTON";
    private static final String GET_BUTTON_INFO = "GET_BUTTON_INFO";
    private static final String GET_BUTTONS_BY_APPID = "GET_BUTTONS_BY_APPID";
    private static final String GET_BUTTONS_BY_BTNID = "GET_BUTTONS_BY_BTNID";
    private static final int CONSUMER_ONLINE_STATUS = 1;

    @Override
    public int insertButton(ButtonInfo buttonInfo) {
        TableChannel tableChannel = tableContainer.getTableChannel("btnInfo", ADD_BUTTON, 0L, 0L);
        int result = 0;
        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(),
                    new Object[] {buttonInfo.getBtnId(), buttonInfo.getBtnName(), buttonInfo.getAppId(), buttonInfo.getConsumerAppId(),
                            buttonInfo.getBtnCategory(), buttonInfo.getCheckStatus(), buttonInfo.getOnlineStatus(),
                            CONSUMER_ONLINE_STATUS});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.info(new StringBuilder(128).append("Duplicate insert button, button_name=").append("button_name"));
                throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP,
                        "duplicate insert button table, button_name=" + buttonInfo.getBtnName());
            } else {
                throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
            }
        }
        return result;
    }

    @Override
    public ButtonInfo getButtonInfo(String btnId) {
        TableChannel tableChannel = tableContainer.getTableChannel("btnInfo", GET_BUTTON_INFO, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        ButtonInfo button = new ButtonInfo();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {btnId, CONSUMER_ONLINE_STATUS}, new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                button.setBtnId(btnId);
                button.setAppId(rs.getInt("app_id"));
                button.setBtnName(rs.getString("btn_name"));
                button.setConsumerAppId(rs.getInt("consumer_app_id"));
                button.setBtnCategory(rs.getString("btn_category"));
                button.setCreationTime(rs.getString("creation_time"));
                button.setCheckStatus(rs.getInt("check_status"));
                button.setOnlineStatus(rs.getInt("online_status"));
                return null;
            }
        });
        return button;
    }

    @Override
    public List<ButtonInfo> getButtonListByBtnId(String btnId) {
        TableChannel tableChannel = tableContainer.getTableChannel("btnInfo", GET_BUTTONS_BY_BTNID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<ButtonInfo> buttons = new ArrayList<>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {btnId}, new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                ButtonInfo button = new ButtonInfo();
                button.setAppId(rs.getLong("app_id"));
                button.setBtnId(rs.getString("btn_id"));
                button.setBtnName(rs.getString("btn_name"));
                button.setConsumerAppId(rs.getInt("consumer_app_id"));
                button.setBtnCategory(rs.getString("btn_category"));
                button.setCreationTime(rs.getString("creation_time"));
                button.setCheckStatus(rs.getInt("check_status"));
                button.setOnlineStatus(rs.getInt("online_status"));
                button.setConsumerOnlineStatus(rs.getInt("consumer_online_status"));
                buttons.add(button);
                return null;
            }
        });
        return buttons;
    }

    @Override
    public List<ButtonInfo> getButtonListByAppId(long appId, boolean isAll) {
        TableChannel tableChannel = tableContainer.getTableChannel("btnInfo", GET_BUTTONS_BY_APPID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        String sql = tableChannel.getSql();
        Object[] params = new Object[] {appId};
        if (!isAll) {
            sql = sql + " and consumer_online_status = ? ";
            params = new Object[] {appId, CONSUMER_ONLINE_STATUS};
        }
        final List<ButtonInfo> buttons = new ArrayList<>();
        jdbcTemplate.query(sql, params, new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                ButtonInfo button = new ButtonInfo();
                button.setAppId(appId);
                button.setBtnId(rs.getString("btn_id"));
                button.setBtnName(rs.getString("btn_name"));
                button.setConsumerAppId(rs.getInt("consumer_app_id"));
                button.setBtnCategory(rs.getString("btn_category"));
                button.setCreationTime(rs.getString("creation_time"));
                button.setCheckStatus(rs.getInt("check_status"));
                button.setOnlineStatus(rs.getInt("online_status"));
                buttons.add(button);
                return null;
            }
        });
        return buttons;
    }

    @Override
    public boolean updateButton(ButtonInfo buttonInfo) {
        return false;
    }

    @Override
    public boolean deleteButton(String btnId) {
        return false;
    }
}
