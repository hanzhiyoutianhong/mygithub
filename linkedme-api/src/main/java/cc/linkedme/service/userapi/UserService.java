package cc.linkedme.service.userapi;

import cc.linkedme.data.model.params.UserParams;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.util.Date;

/**
 * Created by Vontroy on 16/3/19.
 */
public interface UserService {

    boolean userLogin(UserParams userParams);

    boolean userRegister(UserParams userParams);

    boolean validateEmail(UserParams userParams);

    boolean userLogout(UserParams userParams);

    boolean resetUserPwd(UserParams userParams);

    boolean forgotPwd( UserParams userParams );

    boolean resetForgottenPwd( UserParams userParams );

    String getLastLoginTime( UserParams userParams );


}
