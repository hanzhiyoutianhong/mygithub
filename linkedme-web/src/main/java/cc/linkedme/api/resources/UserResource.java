package cc.linkedme.api.resources;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.data.model.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vontroy on 16/3/19.
 */
@Path("user")
@Component
public class UserResource {

    @Resource
    private UserService userService;

    private String emailRE = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private String telephoneRE = "(^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)|(\\d{3}-\\d{8}|\\d{4}-\\d{7})";

    private Pattern emailPattern = Pattern.compile(emailRE);
    private Pattern telephonePattern = Pattern.compile(telephoneRE);

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(User user) {

        JSONArray errorMsgs = new JSONArray();
        if (StringUtils.isBlank(user.getPwd())) {
            errorMsgs.add(getErrorMsg("40000", "pwd", "密码不能为空"));
        }
        if (StringUtils.isBlank(user.getEmail())) {
            errorMsgs.add(getErrorMsg("40000", "email", "邮箱不能为空"));
        } else if (!emailPattern.matcher(user.getEmail()).matches()) {
            errorMsgs.add(getErrorMsg("40000", "email", "邮箱格式非法"));
        } else if (userService.isEmailRegistered(user.getEmail())) {
            errorMsgs.add(getErrorMsg("40004", "email", "邮箱已被注册"));
        }
        if (!telephonePattern.matcher(user.getPhoneNumber()).matches()) {
            errorMsgs.add(getErrorMsg("40000", "phone_number", "电话号码有误, 如果是座机请用\"-\"区分区号"));
        }

        if (errorMsgs.size() > 0) {
            return errorMsgs.toString();
        }

        user.setEmail(user.getEmail().toLowerCase());
        userService.register(user);

        return "{\"ret\": \"true\"}";
    }


    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String login(UserParams user) {

        boolean emailIsRegistered = false;

        JSONArray errorMsgs = new JSONArray();
        if(StringUtils.isBlank(user.getEmail())){
            errorMsgs.add(getErrorMsg("40000", "email", "邮箱不能为空"));
        } else if(!userService.isEmailRegistered(user.getEmail())){
            errorMsgs.add(getErrorMsg("40005", "email", "邮箱未注册"));
        } else {
            emailIsRegistered = true;
        }

        if(StringUtils.isBlank(user.getPwd())){
            errorMsgs.add(getErrorMsg("40006", "pwd", "密码不能为空"));
        } else if(emailIsRegistered && userService.validatePassword(user.getEmail(), user.getPwd())){
            errorMsgs.add(getErrorMsg("40006", "pwd", "密码有误"));
        }

        if (errorMsgs.size() > 0) {
            return errorMsgs.toString();
        }

        user.setEmail(user.getEmail().toLowerCase());

        UserInfo userInfo = userService.userLogin(user);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("user_id", userInfo.getId());
        resultJson.append("email", user.getEmail());
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

    public String validate_email(@QueryParam("email") String email,
                                 @QueryParam("token") String token) {
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
            jsonArray.add(getErrorMsg("40000", "email", "未填写邮箱!"));
        } else if (!userService.validateEmail(userParams)) {
            jsonArray.add(getErrorMsg("40005", "email", "邮箱不存在!"));
        } else {
            emailIsValid = true;
            userParams.email = userParams.email.toLowerCase();
        }

        if (Strings.isNullOrEmpty(userParams.old_pwd)) {
            jsonArray.add(getErrorMsg("40006", "old_pwd", "旧密码为空!"));
        } else if (emailIsValid && !userService.validatePassword(userParams.email, userParams.old_pwd)) {
            jsonArray.add(getErrorMsg("40006", "old_pwd", "旧密码错误!"));
        }

        if (Strings.isNullOrEmpty(userParams.new_pwd)) {
            jsonArray.add(getErrorMsg("40006", "new_pwd", "新密码为空!"));
        }

        if (jsonArray.size() > 0) {
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
            jsonArray.add(getErrorMsg("40000", "email", "未填写邮箱!"));
        } else if (!userService.validateEmail(userParams)) {
            jsonArray.add(getErrorMsg("40005", "email", "邮箱不存在!"));
        } else {
            userParams.email = userParams.email.toLowerCase();
        }
        if (jsonArray.size() > 0) {
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
            jsonArray.add(getErrorMsg("40006", "new_pwd", "新密码为空!"));
        }
        if (jsonArray.size() > 0) {
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

    public JSONObject getErrorMsg(String errCode, String errParam, String errMsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code", errCode);
        jsonObject.put("err_param", errParam);
        jsonObject.put("err_msg", errMsg);
        return jsonObject;
    }

    @Path("new_user_by_day")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String newUserByDay(@FormParam("date") String date,
                               @FormParam("interval") int interval,
                               @Context HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (Strings.isNullOrEmpty(date)) {
            date = sdf.format(new Date());
        }

        Date end_date;
        Date start_date;
        List<UserInfo> userInfos;

        try {
            end_date = sdf.parse(date);
            start_date = new Date(sdf.parse(date).getTime() - 24 * 60 * 60 * 1000 * interval);
        } catch (Exception e) {
            ApiLogger.error("User.newUserByDay parse date format error", e);
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "parse date format error");
        }

        userInfos = userService.getNewUsersByDay(start_date, end_date);

        JSONObject result = new JSONObject();
        JSONArray userArray = new JSONArray();

        int newUserCount = 0;
        if (userInfos != null) {
            newUserCount = userInfos.size();
        }

        result.put("count", newUserCount);

        for (UserInfo userInfo : userInfos) {
            JSONObject user = new JSONObject();
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

    @Path("new_user_today")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String newUserToday(@Context HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return newUserByDay(date, 0, request);
    }

    @Path("user_info_by_bundle_id")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String getUserInfoByBundleId(@FormParam("date") String date,
                                        @FormParam("interval") int interval,
                                        @Context HttpServletRequest request) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (Strings.isNullOrEmpty(date)) {
            date = sdf.format(new Date());
        }

        Date end_date;
        Date start_date;
        List<UserInfo> userInfos;
        try {
            end_date = sdf.parse(date);
            start_date = new Date(sdf.parse(date).getTime() - 24 * 60 * 60 * 1000 * interval);
        } catch (Exception e) {
            ApiLogger.error("User.newUserByDay parse date format error", e);
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "parse date format error");
        }

        userInfos = userService.getUserInfoByBundleId(start_date, end_date);

        JSONObject result = new JSONObject();
        JSONArray userArray = new JSONArray();

        int newUserCount = 0;
        if (userInfos != null) {
            newUserCount = userInfos.size();
        }

        result.put("count", newUserCount);

        for (UserInfo userInfo : userInfos) {
            JSONObject user = new JSONObject();
            user.put("bundle_id", userInfo.getIos_bundle_id());
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
