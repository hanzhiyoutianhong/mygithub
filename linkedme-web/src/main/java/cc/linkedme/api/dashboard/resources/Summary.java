package cc.linkedme.api.dashboard.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.DeepLinkCount;
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
import java.util.Map;

/**
 * Created by LinkedME01 on 16/3/20.
 */

@Path("summary")
@Component
public class Summary {

    @Resource
    private SummaryService summaryService;

    @Path("/deeplinks_count")
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
                          @QueryParam("source") String source,
                          @QueryParam("unique") boolean unique,
                          @QueryParam("return_number") int return_number,
                          @QueryParam("skip_number") int skip_number,
                          @QueryParam("orderby") String orderby) {

        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams(appid, start_date, end_date, feature, campaign, stage, channel, tag, source, unique, return_number, skip_number, orderby);

        Map<Long, DeepLinkCount> result = summaryService.getDeepLinkSummary( summaryDeepLinkParams );

        return null;
    }
}
