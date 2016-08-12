package cc.linkedme.api.resources;

import javax.annotation.Resource;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import cc.linkedme.commons.log.ApiLogger;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            jsonArray.add(getErrorMsg("40000","email","未填写邮箱!"));
        }else if(userService.validateEmail(userParams)){
            jsonArray.add(getErrorMsg("40004","email","邮箱已被注册"));
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
            jsonArray.add(getErrorMsg("40000","email","未填写邮箱!"));
        }else if(!userService.validateEmail(userParams)){
            jsonArray.add(getErrorMsg("40005","email","邮箱不存在!"));
        }else{
            emailIsValid = true;
            userParams.email = userParams.email.toLowerCase();
        }

        if(Strings.isNullOrEmpty(userParams.pwd)){
            jsonArray.add(getErrorMsg("40006","pwd","密码为空!"));
        }else if(emailIsValid && !userService.validatePassword(userParams.email,userParams.pwd)){
            jsonArray.add(getErrorMsg("40006","pwd","密码错误!"));
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
            jsonArray.add(getErrorMsg("40000","email","未填写邮箱!"));
        }else if(!userService.validateEmail(userParams)){
            jsonArray.add(getErrorMsg("40005","email","邮箱不存在!"));
        }else{
            emailIsValid = true;
            userParams.email = userParams.email.toLowerCase();
        }

        if(Strings.isNullOrEmpty(userParams.old_pwd)){
            jsonArray.add(getErrorMsg("40006","old_pwd","旧密码为空!"));
        }else if(emailIsValid && !userService.validatePassword(userParams.email,userParams.old_pwd)){
            jsonArray.add(getErrorMsg("40006","old_pwd","旧密码错误!"));
        }

        if(Strings.isNullOrEmpty(userParams.new_pwd)){
            jsonArray.add(getErrorMsg("40006","new_pwd","新密码为空!"));
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
            jsonArray.add(getErrorMsg("40000","email","未填写邮箱!"));
        }else if(!userService.validateEmail(userParams)){
            jsonArray.add(getErrorMsg("40005","email","邮箱不存在!"));
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
            jsonArray.add(getErrorMsg("40006","new_pwd","新密码为空!"));
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

    public JSONObject getErrorMsg(String errCode,String errParam,String errMsg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code",errCode);
        jsonObject.put("err_param",errParam);
        jsonObject.put("err_msg",errMsg);
        return jsonObject;
    }

    @Path("new_user_today")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String newUserToday() {
        List<UserInfo> userInfos = userService.getNewUsersByDay();
        JSONObject result = new JSONObject();
        JSONArray userArray = new JSONArray();
        int newUserCount = userInfos.size();
        result.put("count", newUserCount);

        for (int i = 0; userInfos != null && i < newUserCount; i++) {
            JSONObject user = new JSONObject();
            UserInfo userInfo = userInfos.get(i);
            user.put("email", userInfo.getEmail());
            user.put("name", userInfo.getName());
            user.put("phone_number", userInfo.getPhone_number());
            user.put("company", userInfo.getCompany());
            user.put("register_time", userInfo.getRegister_time());
            userArray.add(user);
        }

        result.put("user_list", userArray);

        return result.toJSONString();
    }

    @Path("new_user_by_day")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String newUserByDay(@FormParam("date") String date, @Context HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt;
        List<UserInfo> userInfos = new ArrayList<>();

        try {
            dt = sdf.parse(date);
            userInfos = userService.getNewUsersByDay(dt);
        } catch (Exception e) {
            ApiLogger.error("User.newUserByDay parse date format error", e);
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "parse date format error");
        }

        JSONObject result = new JSONObject();
        JSONArray userArray = new JSONArray();
        int newUserCount = userInfos.size();
        result.put("count", newUserCount);

        for (int i = 0; userInfos != null && i < newUserCount; i++) {
            JSONObject user = new JSONObject();
            UserInfo userInfo = userInfos.get(i);
            user.put("email", userInfo.getEmail());
            user.put("name", userInfo.getName());
            user.put("phone_number", userInfo.getPhone_number());
            user.put("company", userInfo.getCompany());
            user.put("register_time", userInfo.getRegister_time());
            userArray.add(user);
        }

        result.put("user_list", userArray);

        return result.toJSONString();
    }

}
