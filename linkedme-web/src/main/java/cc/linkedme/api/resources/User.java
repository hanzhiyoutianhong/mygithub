package cc.linkedme.api.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.utils.UUIDUtils;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.DemoRequestParams;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.webapi.UserService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vontroy on 16/3/19.
 */
@Path("user")
@Component
public class User {

    @Resource
    private UserService userService;

    @Path("/register")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String register(UserParams userParams, @Context HttpServletRequest request) {
        if (userParams != null)
            userParams.email = userParams.email.toLowerCase();
        else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "email should not be null");
        }
        if (userService.userRegister(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
    }
    
    @Path("/login")
    @POST
    @Produces({MediaType.APPLICATION_JSON})

    public String login(UserParams userParams, @Context HttpServletRequest request) {
        if (userParams.email != null)
            userParams.email = userParams.email.toLowerCase();
        else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "email should not be null");
        }
        String email = userParams.email;
        UserInfo userInfo = userService.userLogin(userParams);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("user_id", userInfo.getId());
        resultJson.append("email", email);
        resultJson.append("name", userInfo.getName());
        resultJson.append("company", userInfo.getCompany());
        resultJson.append("role_id", userInfo.getRole_id());
        resultJson.append("register_time", userInfo.getRegister_time());
        resultJson.append("last_login_time", userInfo.getLast_login_time());
        resultJson.append("token", userInfo.getToken());
        return resultJson.flip().toString();
    }

    @Path("/logout")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String logout(UserParams userParams, @Context HttpServletRequest request) {
        if (userParams.email != null)
            userParams.email = userParams.email.toLowerCase();
        else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "email should not be null");
        }

        if (userService.userLogout(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
    }

    @Path("/validate_email")
    @GET
    @Produces({MediaType.APPLICATION_JSON})

    public String validate_email(@QueryParam("email") String email,
                                 @QueryParam("token") String token) {
        if (email != null)
            email = email.toLowerCase();
        else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "email should not be null");
        }

        UserParams userParams = new UserParams();
        userParams.email = email;
        if (!userService.validateEmail(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_USER_EMAIL_ALREADY_REGISTERED);
        }
    }

    @Path("/change_password")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String change_password(UserParams userParams, @Context HttpServletRequest request) {
        if (userParams.email != null)
            userParams.email = userParams.email.toLowerCase();
        else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "email should not be null");
        }

        if (userService.resetUserPwd(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
    }

    @Path("/forgot_password")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String forgot_password(UserParams userParams, @Context HttpServletRequest request) {
        if (userParams.email != null)
            userParams.email = userParams.email.toLowerCase();
        else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "email should not be null");
        }

        if (userService.forgotPwd(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
    }

    @Path("/set_password")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String set_password(UserParams userParams, @Context HttpServletRequest request) {
        if (Strings.isNullOrEmpty(userParams.new_pwd)) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "password is null");
        }

        if (userService.resetForgottenPwd(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
    }

    @Path("/request_demo")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String getDemo(DemoRequestParams demoRequestParams, @Context HttpServletRequest request) {
        boolean result = userService.getDemo(demoRequestParams);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("ret", result);
        return resultJson.flip().toString();
    }

}
