package cc.linkedme.service.userapi.impl;

import cc.linkedme.commons.mail.MailSender;
import cc.linkedme.dao.userapi.UserDao;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.userapi.UserService;

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
        if (userDao.queryEmail(userParams.email) == false) {
            userDao.updateUserInfo(userParams);
            return true;
        }
        return false;
    }

    public String userLogin(UserParams userParams) {
        UserInfo userInfo = userDao.getUserInfo(userParams.email);

        if (userInfo == null) return null;
        if (userParams.pwd.equals(userInfo.getPwd())) {
            String register_time = DateFormat.getDateTimeInstance().format(new Date());
            String current_login_time = register_time;
            userParams.current_login_time = current_login_time;

            userDao.resetLastLoginTime(userParams);

            return userInfo.getLast_login_time();
        } else
            return null;
    }

    public boolean validateEmail(UserParams userParams) {
        if (userDao.queryEmail(userParams.email))
            return true;
        else
            return false;
    }

    public boolean userLogout(UserParams userParams) {
        return userDao.queryEmail(userParams.email);
    }

    public boolean resetUserPwd(UserParams userParams) {
        UserInfo userInfo = userDao.getUserInfo(userParams.email);
        if (userParams.old_pwd.equals(userInfo.getPwd())) {
            userDao.resetUserPwd(userParams);
            return true;
        } else {
            return false;
        }
    }

    public boolean forgotPwd(UserParams userParams) {
        if (userDao.queryEmail(userParams.email)) {
            MailSender.sendTextMail(userParams.email, "Change you PWD", "this is a test mail from java program");
            return true;
        }
        return false;
    }

    public boolean resetForgottenPwd(UserParams userParams) {
        if (userDao.resetUserPwd(userParams) == 1)
            return true;
        else
            return false;
    }

}
