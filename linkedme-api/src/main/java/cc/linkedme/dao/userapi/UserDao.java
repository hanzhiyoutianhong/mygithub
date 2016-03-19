package cc.linkedme.dao.userapi;

import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.UserParams;

/**
 * Created by Vontroy on 16/3/19.
 */
public interface UserDao {

    int updateRegisterInfo(UserParams userParams);

    int pwdReset( UserParams userParams );

    UserInfo getUserInfo( String email );

    boolean emailExistenceQuery( String email );
}
