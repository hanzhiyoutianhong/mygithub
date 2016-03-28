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

        Map<Long, DeepLinkCount> countMap = summaryService.getDeepLinkSummary( summaryDeepLinkParams );
        int deepLinkCounts = countMap.size();
        int ios_click = 0;
        int ios_install = 0;
        int ios_open = 0;
        int adr_click = 0;
        int adr_install = 0;
        int adr_open = 0;
        for (Map.Entry<Long, DeepLinkCount> entry : countMap.entrySet()) {
            DeepLinkCount value = entry.getValue();
            if (value != null) {
                ios_click += value.getIos_click();
                ios_install += value.getIos_install();
                ios_open += value.getIos_open();

                adr_click += value.getAdr_click();
                adr_install += value.getAdr_install();
                adr_open += value.getAdr_open();
            }
        }

        JSONObject iosJson = new JSONObject();
        iosJson.put("click", ios_click);
        iosJson.put("install", ios_install);
        iosJson.put("open", ios_open);

        JSONObject adrJson = new JSONObject();
        adrJson.put("click", adr_click);
        adrJson.put("install", adr_install);
        adrJson.put("open", adr_open);

        JSONObject retJson = new JSONObject();
        retJson.put("link_count", deepLinkCounts);
        retJson.put("ios", iosJson);
        retJson.put("android", adrJson);
        return retJson.toString();
    }
}
