package cc.linkedme.service.webapi;

import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.DemoRequestParams;
import cc.linkedme.data.model.params.UserParams;

import java.util.Date;
import java.util.List;

/**
 * Created by Vontroy on 16/3/19.
 */
public interface UserService {


    UserInfo userLogin(UserParams userParams);

    /**
     * 注册用户
     * @param userParams
     */
    void register(UserParams userParams);


    /**
     * 检查邮箱是否已经被注册
     * @return 如果邮箱已经被注册,返回true;否则返回false
     */
    boolean isEmailRegistered(String email);

    boolean validateEmail(UserParams userParams);

    boolean userLogout(UserParams userParams);

    boolean resetUserPwd(UserParams userParams);

    boolean forgotPwd(UserParams userParams);

    boolean resetForgottenPwd(UserParams userParams);

    boolean requestDemo(DemoRequestParams demoRequestParams);

    boolean validatePassword(String email ,String password);

    List<UserInfo> getNewUsersByDay(Date start_date, Date end_date);

    List<UserInfo> getUserInfoByBundleId(Date start_date, Date end_date);
}
