package cc.linkedme.dao.userapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.userapi.UserDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.hibernate.annotations.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.ObjectError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserDaoImpl extends BaseDao implements UserDao
{
    public static final String REGISTER = "REGISTER";
    public static final String USER_INFO_QUERY = "USER_INFO_QUERY";
    public static final String EMAIL_EXISTENCE_QUERY = "EMAIL_EXISTENCE_QUERY";
    public static final String PWD_RESET = "PWD_RESET";
    public static final String LAST_LOGIN_TIME_RESET = "LAST_LOGIN_TIME_RESET";
    public int updateUserInfo(UserParams userParams)
    {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel( "userInfo", REGISTER, 0L, 0L );
        String register_time = DateFormat.getDateTimeInstance().format( new Date() );
        String last_login_time = register_time;

        try {
            res += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {userParams.email, userParams.pwd, userParams.name, userParams.company, userParams.role_id, register_time, last_login_time});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuilder(128).append("Duplicate insert user, userEmail=").append(userParams.email), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res;
    }

    public int resetUserPwd( UserParams userParams )
    {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("userInfo", PWD_RESET, 0L, 0L);

        try
        {
            res += tableChannel.getJdbcTemplate().update( tableChannel.getSql(), new Object[] {userParams.new_pwd, userParams.email});
        } catch( DataAccessException e ) {
            if( DaoUtil.isDuplicateInsert( e ) )
            {
                ApiLogger.warn( new StringBuffer(128).append( "Duplicate insert user, userEmail=" ).append( userParams.email ), e );
            }
            throw new LMException( LMExceptionFactor.LM_FAILURE_DB_OP );
        }
        return res;
    }

    public int resetLastLoginTime( UserParams userParams )
    {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel( "userInfo", LAST_LOGIN_TIME_RESET, 0L, 0L );
        try
        {
            res += tableChannel.getJdbcTemplate().update( tableChannel.getSql(), new Object[] {userParams.last_logout_time, userParams.email});
        } catch( DataAccessException e ) {
            if( DaoUtil.isDuplicateInsert( e ) )
            {
                ApiLogger.warn( new StringBuffer(128).append( "Duplicate insert user, userEmail=" ).append( userParams.email ), e );
            }
            throw new LMException( LMExceptionFactor.LM_FAILURE_DB_OP );
        }
        return res;
    }

    public UserInfo getUserInfo( final String email )
    {
        TableChannel tableChannel = tableContainer.getTableChannel( "userInfo", USER_INFO_QUERY, 0L, 0L );
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        Object[] values = {email};

        final List<UserInfo> userInfos = new ArrayList<UserInfo>();

        jdbcTemplate.query(tableChannel.getSql(), values, new RowMapper()
        {
            public Object mapRow( ResultSet resultSet, int i ) throws SQLException
            {
                UserInfo user = new UserInfo();

                user.setId( resultSet.getInt( "id" ) );
                user.setEmail( email );
                user.setPwd( resultSet.getString( "pwd" ) );
                user.setCompany( resultSet.getString( "company" ) );
                user.setRole_id( resultSet.getShort( "role_id" ) );

                user.setLast_login_time( resultSet.getTimestamp( "last_login_time" ).toString() );

                userInfos.add( user );

                return null;

            }
        });

        if( userInfos.isEmpty() )
            return null;
        else
            return userInfos.get( 0 );
    }

    public boolean queryEmail( String email )
    {
        TableChannel tableChannel = tableContainer.getTableChannel( "userInfo", EMAIL_EXISTENCE_QUERY, 0L, 0L );
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        Object[] values = {email};

        final List<UserInfo> userInfos = new ArrayList<UserInfo>();

        jdbcTemplate.query( tableChannel.getSql(), values, new RowMapper()
        {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException
            {
                UserInfo user = new UserInfo();
                user.setEmail( resultSet.getString("email") );

                userInfos.add(user);
                return null;
            }
        });

        if( userInfos.isEmpty() )
            return false;
        return true;
    }

}
