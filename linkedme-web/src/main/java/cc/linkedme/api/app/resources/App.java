package cc.linkedme.api.app.resources;


import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.service.webapi.AppService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

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
//重名
        if (appParam.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
        if (appParam.app_name == null) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM);
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
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
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

        //test user agent information
        String userAgent = request.getHeader("user-agent");
        System.out.println(userAgent);


        return new StringBuilder().append("[").append(StringUtils.join(appJsons, ",")).append("]").toString();
    }

    @Path("/delete_app")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteApp(AppParams appParams, @Context HttpServletRequest request) {
        if (appParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }

        if (appParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
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
                           @QueryParam("type") String type,
                           @QueryParam("token") String token,
                           @Context HttpServletRequest request) {

        if (app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }

        AppParams appParams = new AppParams();
        appParams.app_id = app_id;
        appParams.type = type;

        AppInfo appInfo = appService.queryApp(appParams);
        if(appInfo == null) {
            return "{}";
        }

        return appInfo.toJson().toString();
    }

    @Path("/update_app")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateApp(AppParams appParams, @Context HttpServletRequest request) {

        JSONObject linkSettingJson = appParams.link_setting;
        JSONObject iosJson = linkSettingJson.getJSONObject("ios");
        JSONObject adrJson = linkSettingJson.getJSONObject("android");
        JSONObject desktopJson = linkSettingJson.getJSONObject("desktop");

        appParams.has_ios = iosJson.getBoolean("has_ios");
        appParams.ios_not_url = iosJson.getString("ios_not_url");
        appParams.ios_search_option = iosJson.getString("ios_search_option");
        appParams.ios_store_url = iosJson.getString("ios_store_url");
        appParams.ios_custom_url = iosJson.getString("ios_custom_url");
        appParams.ios_enable_ulink = iosJson.getBoolean("ios_enable_ulink");
        appParams.ios_bundle_id = iosJson.getString("ios_bundle_id");
        appParams.ios_app_prefix = iosJson.getString("ios_app_prefix");

        appParams.has_android = adrJson.getBoolean("has_android");
        appParams.android_not_url = adrJson.getString("android_not_url");
        appParams.android_uri_scheme = adrJson.getString("android_uri_scheme");
        appParams.android_search_option = adrJson.getString("android_search_option");
        appParams.google_play_url = adrJson.getString("google_play_url");
        appParams.android_custom_url = adrJson.getString("android_custom_url");
        appParams.android_package_name = adrJson.getString("android_package_name");
        appParams.android_enable_applinks = adrJson.getBoolean("android_enable_applinks");
        appParams.android_sha256_fingerprints = adrJson.getString("android_sha256_fingerprints");

        appParams.use_default_landing_page = desktopJson.getBoolean("use_default_landing_page");
        appParams.custom_landing_page = desktopJson.getString("custom_landing_page");

        int ios_android_flag =
                ((appParams.has_ios ? 1 : 0) << 3) + (appParams.ios_enable_ulink ? 1 : 0) << 2 + (appParams.has_android ? 1 : 0) << 1
                        + (appParams.android_enable_applinks ? 1 : 0);

        appParams.ios_android_flag = ios_android_flag;

        int result = appService.updateApp(appParams);

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("ret", result > 0);
        return resultJson.flip().toString();
    }

}
