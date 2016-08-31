package cc.linkedme.dao.webapi.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    public static final String GET_NEW_REGISTERED_USER_BY_DAY = "GET_NEW_REGISTERED_USER_BY_DAY";
    public static final String GET_USER_INFO_BY_BUNDLE_ID = "GET_USER_INFO_BY_BUNDLE_IDGET_USER_INFO_BY_BUNDLE_ID";

    public static final String REQUEST_DEMO = "REQUEST_DEMO";

    public int updateUserInfo(UserParams userParams) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", REGISTER, 0L, 0L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String register_time = sdf.format(new Date());
        String last_login_time = register_time;

        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {userParams.email, userParams.pwd,
                    userParams.name, userParams.phone_number, userParams.company, userParams.role_id, register_time, last_login_time});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuilder(128).append("Duplicate insert user, userEmail=").append(userParams.email), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update user info");
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
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update user password");
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
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update user password");
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
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update login infos");
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
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to set random code");
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
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update token");
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

    public List<UserInfo> getNewUsersByDay(Date start_date, Date end_date) {

        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", GET_NEW_REGISTERED_USER_BY_DAY, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        List<UserInfo> userInfos = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start_time = sdf.format(start_date) + " 00:00:00";
        String end_time = sdf.format(end_date) + " 23:59:59";

        jdbcTemplate.query(tableChannel.getSql(), new Object[] {start_time, end_time}, new RowMapper() {

            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                UserInfo userInfo = new UserInfo();
                userInfo.setEmail(resultSet.getString("email"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setPhone_number(resultSet.getString("phone_number"));
                userInfo.setCompany(resultSet.getString("company"));
                userInfo.setRegister_time(resultSet.getString("register_time"));

                userInfos.add(userInfo);
                return null;
            }
        });

        return userInfos;
    }

    public List<UserInfo> getUserInfoByBundleId(Date start_date, Date end_date) {
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", GET_USER_INFO_BY_BUNDLE_ID, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        List<UserInfo> userInfos = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start_time = sdf.format(start_date) + " 00:00:00";
        String end_time = sdf.format(end_date) + " 23:59:59";
        String dbName = "dashboard_0";
        String userTbName = "user_info_0";
        String appInfoTbName = "app_info_0";
        String bundleTbName = "app_bundles_0";
        String sql =
                "select bundle.app_id, bundle.online_date, user.email, user.name, user.phone_number, user.company, user.register_time from "
                        + dbName + "." + userTbName + " as user inner join " + dbName + "." + appInfoTbName
                        + " as appinfo on user.id = appinfo.user_id right join " + dbName + "." + bundleTbName
                        + " as bundle on bundle.app_id = appinfo.ios_bundle_id where bundle.online_date between " + "\'" + start_time + "\'"
                        + " and " + "\'" + end_time + "\'"
                        + " and user.valid_status = 1 and appinfo.valid_status = 1 and bundle.valid_status = 1 order by bundle.online_date;";
        jdbcTemplate.query(sql, new RowMapper() {

            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                UserInfo userInfo = new UserInfo();
                userInfo.setIos_bundle_id(resultSet.getString("app_id"));
                userInfo.setBundle_id_online_date(resultSet.getString("online_date"));
                userInfo.setEmail(resultSet.getString("email"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setPhone_number(resultSet.getString("phone_number"));
                userInfo.setCompany(resultSet.getString("company"));
                userInfo.setRegister_time(resultSet.getString("register_time"));

                userInfos.add(userInfo);
                return null;
            }
        });

        return userInfos;
    }
    
}

