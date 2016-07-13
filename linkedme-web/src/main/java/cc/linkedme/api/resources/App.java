package cc.linkedme.api.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.UrlTagsInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.service.webapi.AppService;

import com.google.api.client.repackaged.com.google.common.base.Strings;

/**
 * Created by LinkedME01 on 16/3/17.
 */

@Path("app")
@Component
public class App {
    @Resource
    private AppService appService;

    @Path("/create_app")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createApp(AppParams appParam, @Context HttpServletRequest request) {
        if (appParam.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid user id");
        }
        if (appParam.app_name == null) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM, "App name should not be empty");
        }

        long app_id = appService.createApp(appParam);

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("app_id", app_id);
        return resultJson.flip().toString();
    }

    @Path("/get_apps")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getApps(@QueryParam("user_id") long user_id,
                          @QueryParam("token") String token,
                          @Context HttpServletRequest request) {
        if (user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid user id");
        }

        AppParams appParams = new AppParams();
        appParams.user_id = user_id;
        List<AppInfo> apps = appService.getAppsByUserId(appParams);
        List<String> appJsons = new ArrayList<String>(apps.size());
        for (AppInfo app : apps) {
            if (app != null) {
                appJsons.add(app.toJson());
            }
        }

        return new StringBuilder().append("[").append(StringUtils.join(appJsons, ",")).append("]").toString();
    }

    @Path("/delete_app")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteApp(AppParams appParams, @Context HttpServletRequest request) {
        if (appParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid user id");
        }

        if (appParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid app id");
        }

        int result = appService.deleteApp(appParams);

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("ret", result > 0);
        return resultJson.flip().toString();
    }

    @Path("/query_app")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String queryApp(@QueryParam("app_id") long app_id,
                           @QueryParam("user_id") long user_id,
                           @QueryParam("type") String type,
                           @QueryParam("token") String token,
                           @Context HttpServletRequest request) {

        AppParams appParams = new AppParams();
        appParams.app_id = app_id;
        appParams.user_id = user_id;
        appParams.type = type;

        AppInfo appInfo = appService.getAppById(appParams.app_id);
        if (appInfo == null) {
            return "{}";
        }

        return appInfo.toJson().toString();
    }

    @Path("/update_app")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateApp(AppParams appParams, @Context HttpServletRequest request) {

        if(!Strings.isNullOrEmpty(appParams.lkme_key)){
            appParams.lkme_key = appParams.lkme_key.substring(appParams.lkme_key.length()-32,appParams.lkme_key.length());
        }
        if(!Strings.isNullOrEmpty(appParams.lkme_secret)){
            appParams.lkme_secret = appParams.lkme_secret.substring(appParams.lkme_secret.length()-32,appParams.lkme_secret.length());
        }
        
        JSONObject linkSettingJson = appParams.link_setting;
        JSONObject iosJson = linkSettingJson.getJSONObject("ios");
        JSONObject adrJson = linkSettingJson.getJSONObject("android");
        JSONObject desktopJson = linkSettingJson.getJSONObject("desktop");

        appParams.has_ios = iosJson.getBoolean("has_ios");
        appParams.ios_not_url = iosJson.getString("ios_not_url");
        appParams.ios_uri_scheme = iosJson.getString("ios_uri_scheme");
        appParams.ios_search_option = iosJson.getString("ios_search_option");
        appParams.ios_store_url = iosJson.getString("ios_store_url");
        appParams.ios_custom_url = iosJson.getString("ios_custom_url");
        appParams.ios_enable_ulink = true;
        appParams.ios_bundle_id = iosJson.getString("ios_bundle_id");
        appParams.ios_app_prefix = iosJson.getString("ios_app_prefix");

        appParams.has_android = adrJson.getBoolean("has_android");
        appParams.android_not_url = adrJson.getString("android_not_url");
        appParams.android_uri_scheme = adrJson.getString("android_uri_scheme");
        appParams.android_search_option = adrJson.getString("android_search_option");
        appParams.google_play_url = adrJson.getString("google_play_url");
        appParams.android_custom_url = adrJson.getString("android_custom_url");
        appParams.android_package_name = adrJson.getString("android_package_name");
        appParams.android_enable_applinks = true;
        appParams.android_sha256_fingerprints = adrJson.getString("android_sha256_fingerprints");

        appParams.use_default_landing_page = desktopJson.getBoolean("use_default_landing_page");
        appParams.custom_landing_page = desktopJson.getString("custom_landing_page");


        int ios_android_flag = ((appParams.is_yyb_available ? 1 : 0) << 4) + ((appParams.has_ios ? 1 : 0) << 3)
                + ((appParams.ios_enable_ulink ? 1 : 0) << 2) + ((appParams.has_android ? 1 : 0) << 1)
                + (appParams.android_enable_applinks ? 1 : 0);

        appParams.ios_android_flag = ios_android_flag;

     // dashboard　uri_scheme、package_name、bundle_id、App Prefix判重
        JSONArray errors = new JSONArray();
        if (appParams.has_android && StringUtils.isBlank(appParams.getAndroid_uri_scheme())) {
            errors.add(getErrorJson("android_uri_scheme", "请配置URI Scheme"));
        } else if (appParams.has_android && appParams.getAndroid_uri_scheme().endsWith("://")){
            errors.add(getErrorJson("android_uri_scheme", "URI Scheme的结尾不需要\"://\""));
        } else if (appParams.has_android && appService.isAndroidUriSchemeExsit(appParams.getAndroid_uri_scheme(), appParams.getApp_id())) {
            errors.add(getErrorJson("android_uri_scheme", "该URI Scheme已被占用，请重新配置"));
        }

        if (appParams.has_ios && StringUtils.isBlank(appParams.getIos_uri_scheme())) {
            errors.add(getErrorJson("ios_uri_scheme", "请配置URI Scheme"));
        } else if (appParams.has_ios && appParams.getIos_uri_scheme().endsWith("://")){
            errors.add(getErrorJson("ios_uri_scheme", "URI Scheme的结尾不需要\"://\""));
        } else if (appParams.has_ios && appService.isIosUriSchemeExsit(appParams.getIos_uri_scheme(), appParams.getApp_id())) {
            errors.add(getErrorJson("ios_uri_scheme", "该URI Scheme已被占用，请重新配置"));
        } else if (appParams.has_android && appParams.has_ios && !appParams.getIos_uri_scheme().equals(appParams.getAndroid_uri_scheme())) {
            errors.add(getErrorJson("ios_uri_scheme", "andorid和ios的URI Scheme应该保持一致"));
        }

        if (appParams.has_ios && StringUtils.isBlank(appParams.getIos_bundle_id())) {
            errors.add(getErrorJson("ios_bundle_id", "请配置Bundle ID"));
        } else if (appParams.has_ios && appService.isIosBundleIdExist(appParams.getIos_bundle_id(), appParams.getApp_id())) {
            errors.add(getErrorJson("ios_bundle_id", "该Bundle ID已被占用，请重新配置"));
        }
       
        if (appParams.has_android && StringUtils.isBlank(appParams.getAndroid_package_name())) {
            errors.add(getErrorJson("android_package_name", "请配置Package Name"));
        } else if (appParams.has_android && appService.isAndroidPackageNameExist(appParams.getAndroid_package_name(), appParams.getApp_id())) {
            errors.add(getErrorJson("android_package_name", "该Package Name已存在，请重新配置"));
        }

        if (appParams.has_ios && StringUtils.isBlank(appParams.getIos_app_prefix())) {
            errors.add(getErrorJson("ios_app_prefix", "请配置Apple App Prefix"));
        }

        if (appParams.has_android && StringUtils.isBlank(appParams.getAndroid_sha256_fingerprints())) {
            errors.add(getErrorJson("android_sha256_fingerprints", "请配置Android的SHA256证书"));
        }

        if ( appParams.ios_search_option.equals("apple_store") && StringUtils.isBlank(appParams.getIos_store_url())) {
            errors.add(getErrorJson("ios_store_url", "请配置您的App在Apple Store中的下载链接"));
        } else if( appParams.ios_search_option.equals("custom_url") && StringUtils.isBlank(appParams.getIos_custom_url())) {
            errors.add(getErrorJson("ios_custom_url", "请配置您的iOS App的自定义下载链接"));
        }

        if( appParams.android_search_option.equals("google_play") && StringUtils.isBlank(appParams.getGoogle_play_url())) {
            errors.add(getErrorJson("google_play_url", "请配置您的App在Android应用商店的下载链接"));
        } else if( appParams.android_search_option.equals("custom_url") && StringUtils.isBlank(appParams.getAndroid_custom_url())) {
            errors.add(getErrorJson("android_custom_url", "请配置您的Android App的自定义下载链接"));
        }

        if( !appParams.use_default_landing_page && StringUtils.isBlank(appParams.getCustom_landing_page())) {
            errors.add(getErrorJson("custom_landing_page", "请配置PC端的自定义跳转链接"));
        }

        if (appService.isAppNameExist(appParams)) {
            errors.add(getErrorJson("app_name", "您的账号下已存在相同的App名称，请重新配置"));
        }
        if(errors.size() > 0){
            return errors.toString();
        }

        int result = appService.updateApp(appParams);

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("ret", result > 0);
        return resultJson.flip().toString();
    }

    @Path("/url_tags")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String urlTags(@QueryParam("user_id") long user_id, @QueryParam("app_id") long app_id, @QueryParam("token") String token) {
        AppParams appParams = new AppParams();
        appParams.user_id = user_id;
        appParams.app_id = app_id;

        if (appParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid user id");
        }

        List<UrlTagsInfo> result = appService.getUrlTags(appParams);

        JSONObject resultJson = new JSONObject();
        JSONArray feature = new JSONArray();
        JSONArray campaign = new JSONArray();
        JSONArray stage = new JSONArray();
        JSONArray channel = new JSONArray();
        JSONArray tag = new JSONArray();

        for (int i = 0; result != null && i < result.size(); i++) {
            UrlTagsInfo tmp = result.get(i);
            if (tmp.getTag_type().equals("feature"))
                feature.add(tmp.getTag_content());
            else if (tmp.getTag_type().equals("campaign"))
                campaign.add(tmp.getTag_content());
            else if (tmp.getTag_type().equals("stage"))
                stage.add(tmp.getTag_content());
            else if (tmp.getTag_type().equals("channel"))
                channel.add(tmp.getTag_content());
            else if (tmp.getTag_type().equals("tag")) tag.add(tmp.getTag_content());
        }

        resultJson.put("feature", feature);
        resultJson.put("campaign", campaign);
        resultJson.put("stage", stage);
        resultJson.put("channel", channel);
        resultJson.put("tag", tag);

        return resultJson.toString();
    }

    @Path("/config")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String urlTagsConfig(AppParams appParams, @Context HttpServletRequest request) {

        boolean result = appService.configUrlTags(appParams);

        JSONObject resultJson = new JSONObject();
        resultJson.put("ret", result);
        return resultJson.toString();
    }

    @Path("/uploadimg")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String uploadImg(AppParams appParams, @Context HttpServletRequest request) {
        if (appParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid user id");
        }

        if (appParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid app id");
        }

        if (Strings.isNullOrEmpty(appParams.img_data)) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "img is null");
        }

        if (Strings.isNullOrEmpty(appParams.img_encoding)) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "img encoding is null");
        }

        String basePath = Constants.LOGO_HTTPS_BASE_URL;
        String imageName = appService.uploadImg(appParams);
        if (Strings.isNullOrEmpty(imageName)) {
            throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "upload img failed");
        }
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("img_url", basePath + imageName + "?v=" + new Random().nextInt());
        return resultJson.flip().toString();
    }
    
    
    private JSONObject getErrorJson(String errorParam, String errorMsg){
        JSONObject error = new JSONObject();
        error.put("err_code", 40001);
        error.put("err_param", errorParam);
        error.put("err_msg", errorMsg);
        
        return error;
    }

    
}
