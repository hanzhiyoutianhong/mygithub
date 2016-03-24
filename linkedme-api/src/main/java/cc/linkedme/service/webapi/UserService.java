package cc.linkedme.service.webapi;

import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;

/**
 * Created by Vontroy on 16/3/19.
 */
public interface UserService {


    UserInfo userLogin(UserParams userParams);

    boolean userRegister(UserParams userParams);

    boolean validateEmail(UserParams userParams);

    boolean userLogout(UserParams userParams);

    boolean resetUserPwd(UserParams userParams);

    boolean forgotPwd(UserParams userParams);

    boolean resetForgottenPwd(UserParams userParams);

}
