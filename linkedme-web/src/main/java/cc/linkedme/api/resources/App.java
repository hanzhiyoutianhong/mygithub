package cc.linkedme.api.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.google.api.client.repackaged.com.google.common.base.Strings;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.UrlTagsInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.service.webapi.AppService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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


        int ios_android_flag = ((appParams.qq_app_download_available ? 1 : 0) << 4 ) + ((appParams.has_ios ? 1 : 0) << 3) + ((appParams.ios_enable_ulink ? 1 : 0) << 2)
                + ((appParams.has_android ? 1 : 0) << 1) + (appParams.android_enable_applinks ? 1 : 0);

        appParams.ios_android_flag = ios_android_flag;

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
//         String basePath = request.getScheme() + "://" + request.getServerName() + ":"
//         + request.getServerPort() + "/i/app/images/";
        String basePath = "https://www.linkedme.cc/i/app/images/";
        String imageName = appService.uploadImg(appParams, basePath);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("img_url", basePath + imageName);
        return resultJson.flip().toString();
    }


    @Path("/images/{name}.{type}")
    @GET
    public void showImg(@PathParam("name") String imageName,
                        @PathParam("type") String type,
                        @Context HttpServletResponse response)
            throws IOException {
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            File file = new File(Constants.ImgPath + imageName + "." + type);
            inputStream = new FileInputStream(file);
            out = response.getOutputStream();
            // pic size = 1M
            byte[] bytes = new byte[1024 * 1024];
            int len;
            while ((len = inputStream.read(bytes)) > 0) {
                out.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) out.close();
            if (inputStream != null) inputStream.close();
        }
    }

}
