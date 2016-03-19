package cc.linkedme.service.userapi;

import cc.linkedme.data.model.params.UserParams;

import java.util.Date;

/**
 * Created by Vontroy on 16/3/19.
 */
public interface UserService {
    /**
     * 登录验证
     * @param lmUserParams
     * @return
     */
    boolean isValidLogin(UserParams userParams);

    /**
     * 注册信息验证
     * @param lmUserParams
     * @return
     */
    boolean isValidRegister(UserParams userParams);

    /**
     * 邮箱是否存在
     * @param
     * @return
     */
    boolean isValidEmail(UserParams userParams);

    /**
     * 不存在返回false
     * 存在返回true
     * @param lmUserParams
     * @return
     */
    boolean isValidLogout(UserParams userParams);

    /**
     * passwd reset
     * @param lmUserParams
     * @return
     */
    boolean isValidChange(UserParams userParams);

    boolean isSetPwdSuccess( UserParams userParams );

    String getLastLoginTime( UserParams userParams );


}
