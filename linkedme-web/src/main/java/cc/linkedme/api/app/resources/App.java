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
        long appid = appService.createApp(appParam);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("appid", appid);
        return resultJson.flip().toString();
    }

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

}
