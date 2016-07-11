package cc.linkedme.api.resources;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.DemoRequestParams;
import cc.linkedme.data.model.params.UserParams;
import cc.linkedme.service.webapi.UserService;

import com.google.api.client.repackaged.com.google.common.base.Strings;

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
        JSONArray jsonArray = new JSONArray();

        if (Strings.isNullOrEmpty(userParams.email)) {
            jsonArray.add(emailIsNull());
        }else if(userService.validateEmail(userParams)){
            jsonArray.add(emailExist());
        }else{
            userParams.email = userParams.email.toLowerCase();
        }

        if(jsonArray.size() > 0){
            return jsonArray.toString();
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
        JSONArray jsonArray = new JSONArray();
        boolean emailIsValid = false;

        if (Strings.isNullOrEmpty(userParams.email)) {
            jsonArray.add(emailIsNull());
        }else if(!userService.validateEmail(userParams)){
            jsonArray.add(emailNotExist());
        }else{
            emailIsValid = true;
            userParams.email = userParams.email.toLowerCase();
        }

        if(Strings.isNullOrEmpty(userParams.pwd)){
            jsonArray.add(passwordIsNull());
        }else if(emailIsValid && !userService.validatePassword(userParams.email,userParams.pwd)){
            jsonArray.add(passwordErr());
        }

        if(jsonArray.size() > 0){
            return jsonArray.toString();
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
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "Email should not be empty");
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

    public String validate_email(@QueryParam("email") String email, @QueryParam("token") String token) {
        if (email != null)
            email = email.toLowerCase();
        else {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "Email should not be empty");
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
        JSONArray jsonArray = new JSONArray();
        boolean emailIsValid = false;

        if (Strings.isNullOrEmpty(userParams.email)) {
            jsonArray.add(emailIsNull());
        }else if(!userService.validateEmail(userParams)){
            jsonArray.add(emailNotExist());
        }else{
            emailIsValid = true;
            userParams.email = userParams.email.toLowerCase();
        }

        if(Strings.isNullOrEmpty(userParams.old_pwd)){
            jsonArray.add(oldPasswordIsNull());
        }else if(emailIsValid && !userService.validatePassword(userParams.email,userParams.old_pwd)){
            jsonArray.add(oldPasswordErr());
        }

        if(Strings.isNullOrEmpty(userParams.new_pwd)){
            jsonArray.add(newPasswordIsNull());
        }

        if(jsonArray.size() > 0){
            return jsonArray.toString();
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

        JSONArray jsonArray = new JSONArray();
        if (userParams.email == null) {
            jsonArray.add(emailIsNull());
        }else if(!userService.validateEmail(userParams)){
            jsonArray.add(emailNotExist());
        }else{
            userParams.email = userParams.email.toLowerCase();
        }
        if(jsonArray.size() > 0){
            return jsonArray.toString();
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
        JSONArray jsonArray = new JSONArray();
        if (Strings.isNullOrEmpty(userParams.new_pwd)) {
            jsonArray.add(passwordIsNull());
        }
        if(jsonArray.size() > 0){
            return jsonArray.toString();
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
    public String requestDemo(DemoRequestParams demoRequestParams, @Context HttpServletRequest request) {
        boolean result = userService.requestDemo(demoRequestParams);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("ret", result);
        return resultJson.flip().toString();
    }

    public JSONObject emailIsNull(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40000");
        jsonObject.put("err_param","email");
        jsonObject.put("err_msg","未填写邮箱!");
        return jsonObject;
    }

    public JSONObject emailNotExist(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40005");
        jsonObject.put("err_param","email");
        jsonObject.put("err_msg","邮箱不存在!");
        return jsonObject;
    }

    public JSONObject emailExist(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40004");
        jsonObject.put("err_param","email");
        jsonObject.put("err_msg","邮箱已被注册");
        return jsonObject;
    }

    public JSONObject passwordErr(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40006");
        jsonObject.put("err_param","pwd");
        jsonObject.put("err_msg","密码错误!");
        return jsonObject;
    }

    public JSONObject passwordIsNull(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40006");
        jsonObject.put("err_param","pwd");
        jsonObject.put("err_msg","密码为空!");
        return jsonObject;
    }

    public JSONObject oldPasswordIsNull(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40006");
        jsonObject.put("err_param","pwd");
        jsonObject.put("err_msg","旧密码为空!");
        return jsonObject;
    }

    public JSONObject oldPasswordErr(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40006");
        jsonObject.put("err_param","pwd");
        jsonObject.put("err_msg","旧密码错误!");
        return jsonObject;
    }

    public JSONObject newPasswordIsNull(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code","40006");
        jsonObject.put("err_param","pwd");
        jsonObject.put("err_msg","新密码为空!");
        return jsonObject;
    }

}
