package cc.linkedme.api.app.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.service.webapi.AppService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by LinkedME01 on 16/3/17.
 */

@Path("app")
@Component
public class App {
    @Resource
    private AppService appService;

    @Path("/get_apps")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getApps(@QueryParam("user_id") long user_id,
                          @QueryParam("token") String token) {

        if(user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
        List<AppInfo> apps = appService.getAppsByUserId(user_id);
        JSONArray jsonArray = new JSONArray();
        for(AppInfo app: apps) {
            jsonArray.add(app.toJson());
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("counts", apps.size());
        resultJson.put("data", jsonArray);
        return resultJson.toString();
    }

    @Path("/create_app")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String createApp(@FormParam("app_name") String app_name,
                      @FormParam("user_id") long user_id,
                      @FormParam("token") String token) {

        if(user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
        if(app_name == null) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM);
        }
        AppParams appParam = new AppParams(app_name, user_id);
        long app_id = appService.createApp(appParam);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("app_id", app_id);
        return resultJson.flip().toString();
    }

    @Path("/delete_app")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteApp(@FormParam("user_id") long user_id,
                            @FormParam("app_name") String app_name,
                            @FormParam("token") String token)
    {
        AppParams appParams = new AppParams( app_name, user_id );
        appService.deleteApp( appParams );

        if(user_id <= 0)
        {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }
        if(app_name == null)
        {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM);
        }

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append( "ret", "true" );
        return resultJson.flip().toString();
    }

    @Path("/query_app")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String queryApp(@QueryParam("app_id") long app_id,
                           @QueryParam("token") String token)
    {
        if( app_id <= 0 )
        {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }

        AppParams appParams = new AppParams();
        appParams.appId = app_id;

        AppInfo appInfo = appService.queryApp(appParams);

        return appInfo.toJson();
    }

    @Path("/update_app")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String updateApp( @FormParam("app_id") long app_id,
                             @FormParam("app_name") String app_name,
                             @FormParam("lkme_live_key") String app_live_key,
                             @FormParam("lkme_live_secret") String app_live_secret,
                             @FormParam("lkme_test_key") String app_test_key,
                             @FormParam("lkme_test_secret") String app_test_secret,
                             @FormParam("ios_uri_scheme") String ios_uri_scheme,
                             @FormParam("ios_search_option") String ios_not_url,
                             @FormParam("apple_store_search") String ios_store_url,
                             @FormParam("ios_custom_url") String ios_custom_url,
                             @FormParam("bundle_id") String ios_bundle_id,
                             @FormParam("app_prefix") String ios_prefix,
                             @FormParam("ios_team_id") String ios_team_id,///
                             @FormParam("android_uri_scheme") String android_uri_scheme,
                             @FormParam("android_search_option") String android_not_url,
                             @FormParam("google_play_search") String google_play_url,
                             @FormParam("android_custom_url") String android_custom_url,
                             @FormParam("android_package_name") String android_package_name,
                             @FormParam("sha256_fingerprints") String android_prefix,
                             @FormParam("has_ios") int has_ios,
                             @FormParam("enable_ulink") int enable_ulink,
                             @FormParam("has_android") int has_android,
                             @FormParam("enable_applinks") int enable_applinks,
                             @FormParam("qc_code") String desktop_url)
    {
        AppParams appParams = new AppParams();
        appParams.appId = app_id;
        appParams.appName = app_name;
        appParams.appLiveKey = app_live_key;
        appParams.appLiveSecret = app_live_secret;
        appParams.appTestKey = app_test_key;
        appParams.appTestSecret = app_test_secret;
        appParams.iosUriScheme = ios_uri_scheme;
        appParams.iosNotUrl = ios_not_url;
        appParams.iosStoreUrl = ios_store_url;
        appParams.iosCustomUrl = ios_custom_url;
        appParams.iosBundleId = ios_bundle_id;
        appParams.iosPrefix = ios_prefix;
        appParams.iosTeamId = ios_team_id;
        appParams.androidUriScheme = android_uri_scheme;
        appParams.androidNotUrl = android_not_url;
        appParams.googlePlayUrl = google_play_url;
        appParams.androidCustomUrl = android_custom_url;
        appParams.androidPackageName = android_package_name;
        appParams.androidPrefix = android_prefix;
        int ios_android_flag = ( has_ios << 3 ) + ( enable_ulink << 2 ) + ( has_android << 1 ) + enable_applinks;
        appParams.iosAndroidFlag = ios_android_flag;
        appParams.desktopUrl = desktop_url;

        appService.updateApp( appParams );

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append( "ret", "true" );
        return resultJson.flip().toString();
    }

}
