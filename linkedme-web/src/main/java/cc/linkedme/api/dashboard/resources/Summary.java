package cc.linkedme.api.dashboard.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.service.webapi.SummaryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by LinkedME01 on 16/3/20.
 */

@Path("v1")
@Component
public class Summary {

    @Resource
    private SummaryService summaryService;

    @Path("/get_deeplink_summary")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getDeepLinkSummary(@QueryParam("appid") long appid,
                          @QueryParam("start_date") String start_date,
                          @QueryParam("end_date") String end_date,
                          @QueryParam("feature") String feature,
                          @QueryParam("campaign") String campaign,
                          @QueryParam("stage") String stage,
                          @QueryParam("channel") String channel,
                          @QueryParam("tag") String tag,
                          @QueryParam("unique") boolean unique,
                          @QueryParam("return_number") int return_number,
                          @QueryParam("skip_number") int skip_number,
                          @QueryParam("orderby") String orderby) {

        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams(appid, start_date, end_date, feature, campaign, stage, channel, tag, unique, return_number, skip_number, orderby);
        String result = summaryService.getDeepLinkSummary(summaryDeepLinkParams);
        return result;
    }
}
