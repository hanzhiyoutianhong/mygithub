package cc.linkedme.service.userapi.impl;

import cc.linkedme.commons.mail.MailSender;
import cc.linkedme.dao.userapi.UserDao;
import cc.linkedme.dao.userapi.impl.UserDaoImpl;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.userapi.UserService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public boolean isValidRegister(UserParams userParams)
    {
        if( userDao.emailExistenceQuery( userParams.email ) == false )
        {
            userDao.updateRegisterInfo( userParams );
            return true;
        }
        return false;
    }

    public boolean isValidLogin(UserParams userParams)
    {
        UserInfo userInfo = userDao.getUserInfo( userParams.email );


        if( userInfo.getPwd().equals( userParams.pwd ) )
            return true;
        else
            return false;

    }

    public boolean isValidEmail( UserParams userParams )
    {
        if(userDao.emailExistenceQuery( userParams.email ))
        {
            MailSender.sendTextMail("7306530@qq.com", "Change you PWD", "this is a test mail from java program");
            return true;
        }
        else
            return false;
    }

    public boolean isValidLogout( UserParams userParams )
    {
        return userDao.emailExistenceQuery( userParams.email );
    }

    public boolean isValidChange( UserParams userParams )
    {
        UserInfo userInfo = userDao.getUserInfo( userParams.email );
        if( userParams.old_pwd.equals( userInfo.getPwd() ) )
        {
            userDao.pwdReset( userParams );
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isSetPwdSuccess( UserParams userParams )
    {
        if( userDao.emailExistenceQuery( userParams.email ) )
        {
            userDao.pwdReset( userParams );
            return true;
        }
        return false;
    }

    public String getLastLoginTime(UserParams userParams )
    {
        UserInfo userInfo = userDao.getUserInfo( userParams.email );
        String last_login_time = userInfo.getLast_login_time();
        return last_login_time;
    }

}
