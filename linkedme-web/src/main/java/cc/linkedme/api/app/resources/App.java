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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
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

    @Path("/create_app")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createApp(AppParams appParam, @Context HttpServletRequest request) {

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
        List<AppInfo> apps = appService.getAppsByUserId(user_id);
        JSONArray jsonArray = new JSONArray();
        for (AppInfo app : apps) {
            jsonArray.add(app.toJson());
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("counts", apps.size());
        resultJson.put("data", jsonArray);
        return resultJson.toString();
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
                           @QueryParam("token") String token,
                           @Context HttpServletRequest request) {
        if (app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE);
        }

        AppParams appParams = new AppParams();
        appParams.app_id = app_id;

        AppInfo appInfo = appService.queryApp(appParams);

        return appInfo.toJson();
    }

    @Path("/update_app")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateApp(AppParams appParams, @Context HttpServletRequest request) {

        int ios_android_flag =
                ((appParams.has_ios ? 1 : 0) << 3) + (appParams.enable_ulink ? 1 : 0) << 2 + (appParams.has_android ? 1 : 0) << 1
                        + (appParams.enable_applinks ? 1 : 0);
        appParams.iosAndroidFlag = ios_android_flag;

        appService.updateApp(appParams);

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("ret", "true");
        return resultJson.flip().toString();
    }

}
