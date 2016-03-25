package cc.linkedme.dao.userapi;

import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;

/**
 * Created by Vontroy on 16/3/19.
 */
public interface UserDao {

    int updateUserInfo(UserParams userParams);

    int resetUserPwd(UserParams userParams);

    int resetLastLoginTime(UserParams userParams);

    UserInfo getUserInfo(String email);

    boolean queryEmail(String email); // 验证邮箱是否存在
}
