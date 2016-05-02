package cc.linkedme.service.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.mail.MailSender;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.utils.UUIDUtils;
import cc.linkedme.dao.webapi.UserDao;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.webapi.UserService;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public boolean userRegister(UserParams userParams) {
        userParams.email = MD5Utils.md5( userParams.email );
        userParams.pwd = MD5Utils.md5( userParams.pwd );

        if (userDao.queryEmail(userParams.email))
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_ALREADY_REGISTERED);
        else {
            userDao.updateUserInfo(userParams);
            return true;
        }
    }

    public UserInfo userLogin(UserParams userParams) {
        userParams.email = MD5Utils.md5( userParams.email );
        userParams.pwd = MD5Utils.md5( userParams.pwd );
        UserInfo userInfo = userDao.getUserInfo(userParams.email);

        if (userInfo == null) throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        if (userParams.pwd.equals(userInfo.getPwd())) {
            String current_login_time = DateFormat.getDateTimeInstance().format(new Date());
            userParams.current_login_time = current_login_time;

            userDao.resetLastLoginTime(userParams);
            String token = UUIDUtils.createUUID();
            userParams.setToken(token);
            userDao.updateToken(userParams);
            userInfo.setToken(token);
            return userInfo;
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_WRONG_PWD);
        }
    }

    public boolean validateEmail(UserParams userParams) {
        userParams.email = MD5Utils.md5( userParams.email );
        if (userDao.queryEmail(userParams.email))
            return true;
        else
            return false;
    }

    public boolean userLogout(UserParams userParams) {
        userParams.email = MD5Utils.md5( userParams.email );
        if (!userDao.queryEmail(userParams.email)) {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        } else {
            userParams.setToken(null);
            userDao.updateToken(userParams);
            return true;
        }
    }

    public boolean resetUserPwd(UserParams userParams) {
        userParams.email = MD5Utils.md5( userParams.email );
        userParams.old_pwd = MD5Utils.md5( userParams.old_pwd );
        userParams.new_pwd = MD5Utils.md5( userParams.new_pwd );

        UserInfo userInfo = userDao.getUserInfo(userParams.email);
        if (userParams.old_pwd.equals(userInfo.getPwd())) {
            userDao.resetUserPwd(userParams);
            return true;
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_WRONG_PWD);
        }
    }

    public boolean forgotPwd(UserParams userParams) {
        userParams.email = MD5Utils.md5( userParams.email );
        if (userDao.queryEmail(userParams.email)) {
            String randomCode = MD5Utils.md5(new Random().nextInt() + "_" + userParams.email);
            boolean result = userDao.setRandomCode(randomCode, userParams.email);
            String resetPwdUrl = "https://www.linkedme.cc/dashboard/index.html#/access/resetpwd/" + randomCode;
            if (result) {
                MailSender.sendHtmlMail(userParams.email, "Change you PWD", "点击下面的链接重新设置密码.</br> " + resetPwdUrl);
                return true;
            }
            return false;
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        }
    }

    public boolean resetForgottenPwd(UserParams userParams) {
        userParams.email = MD5Utils.md5( userParams.email );
        userParams.new_pwd = MD5Utils.md5( userParams.new_pwd );
        if (userDao.resetUserPwd(userParams) == 1)
            return true;
        else
            return false;
    }

}
