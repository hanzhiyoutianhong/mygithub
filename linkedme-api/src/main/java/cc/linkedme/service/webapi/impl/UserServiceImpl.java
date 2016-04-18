package cc.linkedme.service.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.mail.MailSender;
import cc.linkedme.commons.utils.UUIDUtils;
import cc.linkedme.dao.webapi.UserDao;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.webapi.UserService;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public boolean userRegister(UserParams userParams) {
        if(userDao.queryEmail(userParams.email) == true)
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_ALREADY_REGISTERED);
        else
        {
            userDao.updateUserInfo( userParams );
            return true;
        }
    }

    public UserInfo userLogin(UserParams userParams) {
        UserInfo userInfo = userDao.getUserInfo(userParams.email);

        if (userInfo == null)
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        if (userParams.pwd.equals(userInfo.getPwd())) {
            String current_login_time = DateFormat.getDateTimeInstance().format(new Date());
            userParams.current_login_time = current_login_time;

            userDao.resetLastLoginTime(userParams);
            String token = UUIDUtils.createUUID();
            userParams.setToken(token);
            userDao.updateToken(userParams);
            return userInfo;
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_WRONG_PWD);
        }
    }

    public boolean validateEmail(UserParams userParams) {
        if (userDao.queryEmail(userParams.email))
            return true;
        else
            return false;
    }

    public boolean userLogout(UserParams userParams) {
        if(!userDao.queryEmail(userParams.email))
        {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        }
        else {
            userParams.setToken(null);
            userDao.updateToken(userParams);
            return true;
        }
    }

    public boolean resetUserPwd(UserParams userParams) {
        UserInfo userInfo = userDao.getUserInfo(userParams.email);
        if (userParams.old_pwd.equals(userInfo.getPwd())) {
            userDao.resetUserPwd(userParams);
            return true;
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_WRONG_PWD);
        }
    }

    public boolean forgotPwd(UserParams userParams) {
        if (userDao.queryEmail(userParams.email)) {
            MailSender.sendTextMail(userParams.email, "Change you PWD", "this is a test mail from java program");
            return true;
        }
        else
        {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        }
    }

    public boolean resetForgottenPwd(UserParams userParams) {
        if (userDao.resetUserPwd(userParams) == 1)
            return true;
        else
            return false;
    }

}
