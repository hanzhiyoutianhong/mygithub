package cc.linkedme.api.user.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.mail.MailAuthenticator;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.userapi.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.*;
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
    public String register(@FormParam("email") String email, @FormParam("pwd") String pwd, @FormParam("name") String name,
            @FormParam("company") String company, @FormParam("token") String token) {
        UserParams userParams = new UserParams(email, pwd, name, company, token);

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
    public String login(@FormParam("email") String email, @FormParam("pwd") String pwd, @FormParam("token") String token) {
        UserParams userParams = new UserParams();
        userParams.email = email;
        userParams.pwd = pwd;

        String last_login_time = userService.userLogin(userParams);

        if (last_login_time != null) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("last_login_time", last_login_time);
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
    }

    @Path("/logout")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String logout(@FormParam("email") String email, @FormParam("last_logout_time") String last_logout_time,
            @FormParam("token") String token) {
        UserParams userParams = new UserParams(email, last_logout_time, token);

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
    public String validate_email(@QueryParam("email") String email, @QueryParam("token") String token) {
        UserParams userParams = new UserParams(email, token);
        if (userService.validateEmail(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
    }

    @Path("/change_password")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String change_password(@FormParam("email") String email, @FormParam("old_pwd") String old_pwd,
            @FormParam("new_pwd") String new_pwd, @FormParam("token") String token) {
        UserParams userParams = new UserParams(email, old_pwd, new_pwd, token);

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
    public String forgot_password(@FormParam("email") String email, @FormParam("token") String token) {
        UserParams userParams = new UserParams(email, token);
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
    public String set_password(@FormParam("email") String email, @FormParam("new_pwd") String new_pwd, @FormParam("token") String token) {
        UserParams userParams = new UserParams(email, null, new_pwd, token);
        if (userService.resetForgottenPwd(userParams)) {
            JsonBuilder resultJson = new JsonBuilder();
            resultJson.append("ret", "true");
            return resultJson.flip().toString();
        } else {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
    }
}
