package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.DemoRequestParams;
import cc.linkedme.data.model.params.UserParams;

import java.util.Date;
import java.util.List;


public interface UserDao {

    /**
     * 向数据库中增加一条用户记录
     * 
     * @param userParams
     */
    void addUser(UserParams userParams);

    boolean isEmailRegistered(String email);

    int resetUserPwd(UserParams userParams);

    int changeUserPwd(UserParams userParams);

    int setLoginInfos(UserParams userParams); // 登录时更新lastLoginTime和token

    /**
     * 根据email查询用户信息
     * 
     * @param email 用户email
     * @return 用户信息
     */
    UserInfo getUserInfo(String email);


    /**
     * 校验邮箱是否存在
     * 
     * @param email 用户email
     * @return 如果邮箱已经注册过,返回true;否则返回false
     */
    boolean queryEmail(String email); // 验证邮箱是否存在

    boolean setRandomCode(String randomCode, String email); // 忘记密码后,重置密码前生成随机码

    int updateToken(UserParams userParams);

    String getToken(String email);

    int requestDemo(DemoRequestParams demoRequestParams);

    public List<UserInfo> getNewUsersByDay(Date start_date, Date end_date);

    public List<UserInfo> getUserInfoByBundleId(Date start_date, Date end_date);
}
