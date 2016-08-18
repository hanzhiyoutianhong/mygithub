package cc.linkedme.api.lkme.web.appanalysis;

import cc.linkedme.commons.util.Util;
import cc.linkedme.service.sdkapi.AppAnalysisService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by LinkedME07 on 16/7/28.
 */

@Path("analysis")
@Component
public class AppAnalysis {

    @Resource
    private AppAnalysisService appAnalysisService;

    @Path("/get_changed_apps")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getChangedApps(@QueryParam("company") String company,
                                 @QueryParam("date") String date,
                                 @Context HttpServletRequest request) {

        JSONArray jsonArray = new JSONArray();

        if (Strings.isNullOrEmpty(company)) {
            jsonArray.add(Util.getErrorMsg("40001", "company", "company 为空"));
        }

        if (Strings.isNullOrEmpty(date)) {
            jsonArray.add(Util.getErrorMsg("40001", "date", "date 为空"));
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        return appAnalysisService.getChangedApps(company, date);
    }

    @Path("/get_apps")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getApps(@QueryParam("company") String company,
                          @Context HttpServletRequest request) {

        JSONArray jsonArray = new JSONArray();

        if (Strings.isNullOrEmpty(company)) {
            jsonArray.add(Util.getErrorMsg("40001", "company", "company 为空"));
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        return appAnalysisService.getApps(company);
    }

    @Path("/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String count(@QueryParam("startDate") String startDate,
                        @QueryParam("endDate") String endDate,
                        @Context HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("magicwindow", appAnalysisService.count("magicwindow", startDate, endDate));
        jsonObject.put("deepshare", appAnalysisService.count("deepshare", startDate, endDate));
        jsonObject.put("linkedme", appAnalysisService.count("linkedme", startDate, endDate));

        return jsonObject.toString();
    }

    @Path("/countbyinterval")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String count(@QueryParam("interval") int interval,
                        @Context HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("magicwindow", appAnalysisService.count("magicwindow", interval));
        jsonObject.put("deepshare", appAnalysisService.count("deepshare", interval));
        jsonObject.put("linkedme", appAnalysisService.count("linkedme", interval));

        return jsonObject.toString();
    }



    @Path("/update_status")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String updateStatus(@QueryParam("app_id") String appId,
                               @QueryParam("company") String company,
                               @QueryParam("status") String status,
                               @Context HttpServletRequest request) {

        JSONArray jsonArray = new JSONArray();

        if (Strings.isNullOrEmpty(appId)) {
            jsonArray.add(Util.getErrorMsg("40001", "bundled_id", "bundled_id 为空"));
        }

        if (Strings.isNullOrEmpty(company)) {
            jsonArray.add(Util.getErrorMsg("40001", "company", "company 为空"));
        }

        if (Strings.isNullOrEmpty(status)) {
            jsonArray.add(Util.getErrorMsg("40001", "status", "status 为空"));
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        return appAnalysisService.updateStatus(appId, company, status);
    }
}
