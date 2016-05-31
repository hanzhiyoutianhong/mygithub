package cc.linkedme.dao.webapi.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.google.api.client.repackaged.com.google.common.base.Strings;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.UserDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.DemoRequestParams;
import cc.linkedme.data.model.params.UserParams;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserDaoImpl extends BaseDao implements UserDao {
    public static final String REGISTER = "REGISTER";
    public static final String USER_INFO_QUERY = "USER_INFO_QUERY";
    public static final String EMAIL_EXISTENCE_QUERY = "EMAIL_EXISTENCE_QUERY";
    public static final String PWD_RESET = "PWD_RESET";
    public static final String CHANGE_PWD = "CHANGE_PWD";
    public static final String UPDATE_TOKEN = "UPDATE_TOKEN";
    public static final String GET_TOKEN = "GET_TOKEN";
    public static final String SET_RANDOM_CODE = "SET_RANDOM_CODE";
    public static final String SET_LOGIN_TIME_AND_TOKEN = "SET_LOGIN_TIME_AND_TOKEN";

    public static final String REQUEST_DEMO = "REQUEST_DEMO";

    public int updateUserInfo(UserParams userParams) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", REGISTER, 0L, 0L);
        String register_time = DateFormat.getDateTimeInstance().format(new Date());
        String last_login_time = register_time;

        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {userParams.email, userParams.pwd,
                    userParams.name, userParams.phone_number, userParams.company, userParams.role_id, register_time, last_login_time});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuilder(128).append("Duplicate insert user, userEmail=").append(userParams.email), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res;
    }

    public int changeUserPwd(UserParams userParams) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", CHANGE_PWD, 0L, 0L);

        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {userParams.new_pwd, userParams.email});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuffer(128).append("Duplicate insert user, userEmail=").append(userParams.email), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res;
    }

    public int resetUserPwd(UserParams userParams) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", PWD_RESET, 0L, 0L);

        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {userParams.new_pwd, userParams.token});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuffer(128).append("Duplicate insert user, userEmail=").append(userParams.email), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res;
    }

    public int setLoginInfos(UserParams userParams) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", SET_LOGIN_TIME_AND_TOKEN, 0L, 0L);
        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(),
                    new Object[] {userParams.last_logout_time, userParams.token, userParams.email});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuffer(128).append("Duplicate insert user, userEmail=").append(userParams.email), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res;
    }

    public UserInfo getUserInfo(final String email) {
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", USER_INFO_QUERY, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        Object[] values = {email};

        final List<UserInfo> userInfos = new ArrayList<UserInfo>();

        jdbcTemplate.query(tableChannel.getSql(), values, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                UserInfo user = new UserInfo();

                user.setId(resultSet.getInt("id"));
                user.setEmail(email);
                user.setName(resultSet.getString("name"));
                user.setPwd(resultSet.getString("pwd"));
                user.setCompany(resultSet.getString("company"));
                user.setRole_id(resultSet.getShort("role_id"));
                user.setRegister_time(resultSet.getString("register_time"));
                user.setLast_login_time(resultSet.getTimestamp("last_login_time").toString());

                userInfos.add(user);

                return null;

            }
        });

        if (userInfos.isEmpty())
            return null;
        else
            return userInfos.get(0);
    }

    public boolean queryEmail(String email) {
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", EMAIL_EXISTENCE_QUERY, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        Object[] values = {email};

        final List<UserInfo> userInfos = new ArrayList<UserInfo>();

        jdbcTemplate.query(tableChannel.getSql(), values, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                UserInfo user = new UserInfo();
                user.setEmail(resultSet.getString("email"));

                userInfos.add(user);
                return null;
            }
        });

        if (userInfos.isEmpty()) return false;
        return true;
    }

    @Override
    public boolean setRandomCode(String randomCode, String email) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", SET_RANDOM_CODE, 0L, 0L);
        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {randomCode, email});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res > 0;
    }

    @Override
    public int updateToken(UserParams userParams) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", UPDATE_TOKEN, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        try {
            res += jdbcTemplate.update(tableChannel.getSql(), new Object[] {userParams.token, userParams.email});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res;
    }

    @Override
    public String getToken(String user_id) {
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", GET_TOKEN, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<UserInfo> userInfos = new ArrayList<>();
        Object[] values = {user_id};
        jdbcTemplate.query(tableChannel.getSql(), values, new RowMapper() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                UserInfo user = new UserInfo();
                user.setToken(resultSet.getString("token"));
                userInfos.add(user);
                return null;
            }
        });
        if (!userInfos.isEmpty()) {
            String token = userInfos.get(0).getToken();
            return token;
        }
        return "403Forbidden";
    }

    @Override
    public int requestDemo(DemoRequestParams demoRequestParams) {
        int res = 0;
        String[] channelArr = null;
        if (!Strings.isNullOrEmpty(demoRequestParams.from_channel)) {
            channelArr = demoRequestParams.from_channel.split(",");
        }
        long channels = 0;
        if (channelArr != null) {
            // TODO 应该要去重
            for (String str : channelArr) {
                if (Integer.parseInt(str) > 60 || Integer.parseInt(str) < 0) {
                    continue;
                }
                channels = channels + (1 << Integer.parseInt(str));
            }
        }
        TableChannel tableChannel = tableContainer.getTableChannel("demoInfo", REQUEST_DEMO, 0L, 0L);
        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(),
                    new Object[] {demoRequestParams.name, demoRequestParams.email, demoRequestParams.mobile_phone,
                            demoRequestParams.company_product_name, channels, demoRequestParams.other_channel});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuilder(128).append("Duplicate insert DemoInfo, email=").append(demoRequestParams.email), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "email already exists");
        }
        return res;
    }
}
