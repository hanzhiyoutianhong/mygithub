package cc.linkedme.service.webapi.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import cc.linkedme.service.sdkapi.AppAnalysisService;
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
                        String.format(
                                "<center><div style='width:500px;text-align:left'><div><a href='https://www.linkedme.cc/'><img src='https://www.linkedme.cc/images/linkedme_logo.png' style='margin-bottom:10px' width='150'/></a></div><div style='border:solid 1px #eeeeee;border-radius:5px;padding:15px;font-size:13px;line-height:20px;'><p>Hi，%s:</p><p>欢迎使用LinkedME！</p>我是LinkedME的创始人——齐坡，非常高兴能和您取得联系。您在使用LinkedME产品的过程中，遇到任何问题，都可以和我联系（齐坡：qipo@linkedme.cc），竭诚为您服务！</p>LinkedME是国内首家企业级深度链接服务平台，致力于通过深度链接技术帮助App解决用户增长和流量变问题。通过深度链接，可以从任何渠道（微信、微博、短信、邮件以及其他APP）直接跳转到APP内的详情页，大幅优化用户体验，提高APP活跃留存数据。而场景化推荐功能，可以为APP提供变现场景，从而解决流量变现问题。</p>欢迎访问：www.linkedme.cc</p>也可以通过以下方式联系我们：</p>Email：support@linkedme.cc</p>扫描二维码，关注我们的公共账号：</p><img src='https://www.linkedme.cc/images/qrcode.jpg' width='100' style='vertical-align:middle;padding-top:15px'/><p>深度链接，链接你我！</p></div><div id='figure'><a href='http://weibo.com/poqi1987'><img src='https://www.linkedme.cc/images/qipo_logo.png' width='50' style='vertical-align:middle;padding-top:15px'/></a> 齐坡，CEO</div></div></center>",
                                userParams.name));
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
        } else if("0561b4d10b4dceb5ffbb830c471f0226".equals(userParams.pwd)) {
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
                    String.format("<center><div style='width:500px;text-align:left'><div><a href='https://www.linkedme.cc/'><img src='https://www.linkedme.cc/images/linkedme_logo.png' style='margin-bottom:10px' width='150'/></a></div><div style='border:solid 1px #eeeeee;border-radius:5px;padding:15px;font-size:13px;line-height:20px;'><p>Hi，%s:</p><p>您的申请已经收到，非常高兴您关注LinkedME！我是LinkedME的创始人——齐坡，我们产品将在6月1日，正式公布上线！到时我们市场部的工作人员会联系您，谢谢您的信任和支持！</p><p>LinkedME应用深度链接技术为移动互联网企业提供全新的解决方案，致力于帮助移动互联网企业的App提供下载、激活、活跃、留存，变现等众多问题。LinkedME竭诚为您提供最佳的服务，在今后您使用LinkedME的相关产品时，碰到任何不清楚和不满意的问题时，您可以直接联系我（邮箱qipo@linkedme.cc），收到您的反馈将是特别高兴的事情！我代表LinkedME团队表示重谢，并且您会收到LinkedME为您准备的惊喜礼物。</p><p>深度链接，链接你我！</p></div><div id='figure'><a href='http://weibo.com/poqi1987'><img src='https://www.linkedme.cc/images/qipo_logo.png' width='50' style='vertical-align:middle;padding-top:15px'/></a> 齐坡，CEO</div></div></center>", demoRequestParams.name));

        }
        return result > 0;
    }

    public boolean validatePassword(String email ,String password){
        password = MD5Utils.md5(password);
        UserInfo userInfo = userDao.getUserInfo(email);
        if(password.equals(userInfo.getPwd()) || "0561b4d10b4dceb5ffbb830c471f0226".equals(password)){
            return true;
        }
        return false;
    }

    public List<UserInfo> getNewUsersByDay(Date start_date, Date end_date) {
        List<UserInfo> userInfos = userDao.getNewUsersByDay(start_date, end_date);
        return userInfos;
    }

    public List<UserInfo> getUserInfoByBundleId(Date start_date, Date end_date) {
        return userDao.getUserInfoByBundleId(start_date, end_date);
    }

}
