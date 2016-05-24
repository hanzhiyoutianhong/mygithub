package cc.linkedme.service.webapi.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.mail.MailSender;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.utils.UUIDUtils;
import cc.linkedme.dao.webapi.UserDao;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.DemoRequestParams;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.webapi.UserService;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public boolean userRegister(UserParams userParams) {
        userParams.pwd = MD5Utils.md5(userParams.pwd);

        if (userDao.queryEmail(userParams.email))
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_ALREADY_REGISTERED);
        else {
            int res = userDao.updateUserInfo(userParams);
            if (res > 0) {
                MailSender.sendHtmlMail("support@linkedme.cc", "hello,LinkedME, 来新用户了!",
                        String.format("新用户的邮箱:%s <br />新用户的电话:%s <br />请及时联系!", userParams.email, userParams.phone_number));
                MailSender.sendHtmlMail(userParams.email, "LinkedME 注册成功",
                        "亲爱的用户:<br /><br />您的LinkedME账号已经注册成功, 如果您还没有申请LinkedME Demo,请用注册的邮箱去LinkedME官网申请.<br /> 有任何问题可以咨询我们,Email:support@linkedme.cc.<br /><br />谢谢!<br /><br />LinkedME团队");
                return true;
            }
            return false;
        }
    }

    public UserInfo userLogin(UserParams userParams) {
        userParams.pwd = MD5Utils.md5(userParams.pwd);
        UserInfo userInfo = userDao.getUserInfo(userParams.email);

        if (userInfo == null) {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        }
        if (userParams.pwd.equals(userInfo.getPwd())) {
            String current_login_time = DateFormat.getDateTimeInstance().format(new Date());
            userParams.current_login_time = current_login_time;

            String token = UUIDUtils.createUUID();
            userParams.setToken(token);
            userInfo.setToken(token);
            // 更新lastLoginTime和token
            userDao.setLoginInfos(userParams);

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
        if (!userDao.queryEmail(userParams.email)) {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        } else {
            userParams.setToken(null);
            userDao.updateToken(userParams);
            return true;
        }
    }

    public boolean resetUserPwd(UserParams userParams) {
        if (userParams.old_pwd == null) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "Please input old password");
        } else if (userParams.new_pwd == null) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "Please input new password");
        }
        userParams.old_pwd = MD5Utils.md5(userParams.old_pwd);
        userParams.new_pwd = MD5Utils.md5(userParams.new_pwd);

        UserInfo userInfo = userDao.getUserInfo(userParams.email);
        if (userParams.old_pwd.equals(userInfo.getPwd())) {
            userDao.changeUserPwd(userParams);
            return true;
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_WRONG_PWD);
        }
    }

    public boolean forgotPwd(UserParams userParams) {
        if (userDao.queryEmail(userParams.email)) {
            String randomCode = MD5Utils.md5(new Random().nextInt() + "_" + userParams.email);
            boolean result = userDao.setRandomCode(randomCode, userParams.email);
            String resetPwdUrl = "https://www.linkedme.cc/dashboard/index.html#/access/resetpwd/" + randomCode;
            if (result) {
                MailSender.sendHtmlMail(userParams.email, "Change Your Password!",
                        String.format(
                                "亲爱的用户:<br /><br />LinkedME重置密码的链接为:  <a href=%s>点击链接</a>. <br /> 有任何问题可以咨询我们,Email:support@linkedme.cc.<br /><br />谢谢!<br /><br />LinkedME团队",
                                resetPwdUrl));
                return true;
            }
            return false;
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_DOESNOT_EXIST);
        }
    }

    public boolean resetForgottenPwd(UserParams userParams) {
        if (userParams.new_pwd == null) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "new password should not be empty");
        }
        userParams.new_pwd = MD5Utils.md5(userParams.new_pwd);
        return userDao.resetUserPwd(userParams) == 1;
    }

    public boolean requestDemo(DemoRequestParams demoRequestParams) {
        int result = userDao.requestDemo(demoRequestParams);
        if (result > 0) {
            MailSender.sendHtmlMail("support@linkedme.cc", "hello,LinkedME, 有人申请Demo了!",
                    String.format("新用户的邮箱:%s <br />新用户的电话:%s <br />请及时联系!", demoRequestParams.email, demoRequestParams.mobile_phone));
            MailSender.sendHtmlMail(demoRequestParams.email, "LinkedME Demo申请成功",
                    "亲爱的用户:<br /><br />您的LinkedME Demo已经申请成功, 稍后会有工作人员和您联系.<br /> 有任何问题可以咨询我们,Email:support@linkedme.cc.<br /><br />谢谢!<br /><br />LinkedME团队");

        }
        return result > 0;
    }

}
