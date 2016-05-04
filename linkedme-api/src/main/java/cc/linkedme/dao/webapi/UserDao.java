package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.DemoRequestParams;
import cc.linkedme.data.model.params.UserParams;

/**
 * Created by Vontroy on 16/3/19.
 */
public interface UserDao {

    int updateUserInfo(UserParams userParams);

    int resetUserPwd(UserParams userParams);

    int changeUserPwd(UserParams userParams);

    int resetLastLoginTime(UserParams userParams);

    UserInfo getUserInfo(String email);

    boolean queryEmail(String email); // 验证邮箱是否存在

    boolean setRandomCode(String randomCode, String email);   //忘记密码后,重置密码前生成随机码

    int updateToken(UserParams userParams);

    String getToken(String email);

    int getDemo(DemoRequestParams demoRequestParams);
}
