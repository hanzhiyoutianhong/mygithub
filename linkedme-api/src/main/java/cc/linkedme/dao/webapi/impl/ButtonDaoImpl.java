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
    private static final String DELETE_BUTTON_BY_BTNID = "DELETE_BUTTON_BY_BTNID";
    private static final String UPDATE_BUTTON_BY_BTNID_APPID = "UPDATE_BUTTON_BY_BTNID_APPID";
    private static final String UPDATE_CONSUMER_ONLINE_STATUS = "UPDATE_CONSUMER_ONLINE_STATUS";
    private static final int CONSUMER_ONLINE_STATUS = 1;
    private static final int CONSUMER_OFFLINE_STATUS = 0;

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
        List<ButtonInfo> buttonInfoList = getButtonListByBtnId(buttonInfo.getBtnId());
        boolean isAppIdExsit = false;
        int result = 0;
        for (ButtonInfo iter : buttonInfoList) {
            if(iter.getAppId() == buttonInfo.getAppId()) {
                isAppIdExsit = true;
                break;
            }
        }
        // app id 已存在，则根据app id和btn id来修改btn的信息
        // 若app id不存在，说明用户为btn新添加一个app id，将之前的btn的 online status设置为0，并插入一条新的记录
        if(isAppIdExsit) {
            TableChannel tableChannel = tableContainer.getTableChannel("btnInfo", UPDATE_BUTTON_BY_BTNID_APPID, 0L, 0L);
            JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
            try {
                result += jdbcTemplate.update(tableChannel.getSql(),
                        new Object[] {buttonInfo.getBtnName(), buttonInfo.getOnlineStatus(), buttonInfo.getBtnId(), buttonInfo.getAppId()});
            } catch (DataAccessException e) {
                throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
            }
        } else {
            //获取当前在线的button信息
            ButtonInfo currentButtonInfo = getButtonInfo(buttonInfo.getBtnId());

            //将当前在线的button改为下线
            TableChannel tableChannel = tableContainer.getTableChannel("btnInfo", UPDATE_CONSUMER_ONLINE_STATUS, 0L, 0L);
            JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
            try {
                result += jdbcTemplate.update(tableChannel.getSql(),
                        new Object[] {CONSUMER_OFFLINE_STATUS, currentButtonInfo.getBtnId(), CONSUMER_ONLINE_STATUS});
            } catch (DataAccessException e) {
                throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
            }

            //将新的appid的button插入表中
            currentButtonInfo.setAppId(buttonInfo.getAppId());
            currentButtonInfo.setBtnName(buttonInfo.getBtnName());
            currentButtonInfo.setOnlineStatus(buttonInfo.getOnlineStatus());
            result += insertButton(currentButtonInfo);
            //操作成功的话，result = 2
            result--;
        }
        return result > 0;
    }

    @Override
    public boolean deleteButton(String btnId) {
        TableChannel tableChannel = tableContainer.getTableChannel("btnInfo", DELETE_BUTTON_BY_BTNID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        int result = 0;
        try {
            result += jdbcTemplate.update(tableChannel.getSql(), new Object[] {btnId});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result > 0;
    }
}
