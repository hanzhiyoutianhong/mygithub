package cc.linkedme.api.resources;

import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.params.SummaryButtonParams;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.service.webapi.SummaryService;

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
    @Produces(MediaType.APPLICATION_JSON)
    public String getDeepLinkSummary(@QueryParam("app_id") int appid,
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

        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams(appid, start_date, end_date, feature, campaign, stage,
                channel, tag, source, unique, return_number, skip_number, orderby);
        Map<Long, DeepLinkCount> countMap = summaryService.getDeepLinkSummary(summaryDeepLinkParams);
        int deepLinkCounts = countMap.size();
        long ios_click = 0, ios_install = 0, ios_open = 0, adr_click = 0, adr_install = 0, adr_open = 0, pc_click = 0, pc_ios_scan = 0, pc_adr_scan = 0;
        for (Map.Entry<Long, DeepLinkCount> entry : countMap.entrySet()) {
            DeepLinkCount value = entry.getValue();
            if (value != null) {
                ios_click += value.getIos_click();
                ios_install += value.getIos_install();
                ios_open += value.getIos_open();

                adr_click += value.getAdr_click();
                adr_install += value.getAdr_install();
                adr_open += value.getAdr_open();

                pc_click += value.getPc_click();
                pc_ios_scan += value.getPc_ios_scan();
                pc_adr_scan += value.getPc_adr_scan();
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

        JSONObject pcJson = new JSONObject();
        pcJson.put("click", pc_click);
        pcJson.put("pc_ios_scan", pc_ios_scan);
        pcJson.put("pc_adr_scan", pc_adr_scan);

        JSONObject retJson = new JSONObject();
        retJson.put("link_count", deepLinkCounts);
        retJson.put("ios", iosJson);
        retJson.put("android", adrJson);
        retJson.put("pc", pcJson);
        return retJson.toString();
    }

    @Path("deeplink_count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getDeepLinkInfo(@QueryParam("deeplink_id") long deeplink_id,
                                  @QueryParam("app_id") int app_id,
                                  @QueryParam("token") String token) {
        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams();
        summaryDeepLinkParams.deepLinkId = deeplink_id;
        summaryDeepLinkParams.appid = app_id;

        return summaryService.getDeepLinkInfoByDeepLinkId(summaryDeepLinkParams);
    }

    @Path("multi_deeplink_count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMultiDeepLinkInfo(@QueryParam("deeplink_ids") String deeplink_ids,
                                       @QueryParam("token") String token) {
        String[] deeplink_id = deeplink_ids.split(",");

        int click = 0;
        int open = 0;
        int install = 0;

        for (int i = 0; i < deeplink_id.length; i++) {
            int[] tmp = summaryService.getDeepLikCounts(Long.parseLong(deeplink_id[i]));
            click += tmp[0];
            open += tmp[1];
            install += tmp[2];
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("click", click);
        resultJson.put("open", open);
        resultJson.put("install", install);

        return resultJson.toString();
    }

    @Path("/deeplinks_count_history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDeepLinkHistoryCount(@QueryParam("app_id") int appid,
                                          @QueryParam("start_date") String start_date,
                                          @QueryParam("end_date") String end_date,
                                          @QueryParam("feature") String feature,
                                          @QueryParam("campaign") String campaign,
                                          @QueryParam("stage") String stage,
                                          @QueryParam("channel") String channel,
                                          @QueryParam("tag") String tags,
                                          @QueryParam("source") String source,
                                          @QueryParam("unique") boolean unique,
                                          @QueryParam("interval") int interval,
                                          @QueryParam("orderby") String orderby,
                                          @QueryParam("token") String token) {

        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams(appid, start_date, end_date, feature, campaign, stage,
                channel, tags, source, unique, interval, orderby);
        String result = summaryService.getDeepLinkHistoryCount(summaryDeepLinkParams);
        return result;
    }

    @Path("/get_income_rank")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIncome(@QueryParam("user_id") long user_id,
                            @QueryParam("app_id") long app_id,
                            @QueryParam("start_date") String start_date,
                            @QueryParam("end_date") String end_date,
                            @QueryParam("return_number") int return_number,
                            @QueryParam("order_by") String order_by) {

        SummaryButtonParams summaryButtonParams = new SummaryButtonParams(user_id, app_id, start_date, end_date, return_number, order_by);
        String result = summaryService.getButtonsIncome(summaryButtonParams);
        return result;
    }

    @Path("/get_income_history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHistoryIncome(@QueryParam("user_id") long user_id,
                                   @QueryParam("app_id") long app_id,
                                   @QueryParam("start_date") String start_date,
                                   @QueryParam("end_date") String end_date,
                                   @QueryParam("interval") int interval) {

        SummaryButtonParams summaryButtonParams = new SummaryButtonParams(user_id, app_id, start_date, end_date, interval);
        String result = summaryService.getHistoryIncome(summaryButtonParams);
        return result;
    }

    @Path("/btn_count_history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBtnCountHistory(@QueryParam("user_id") long user_id,
                              @QueryParam("app_id") long app_id,
                              @QueryParam("button_id") String button_id,
                              @QueryParam("start_date") String start_date,
                              @QueryParam("end_date") String end_date,
                              @QueryParam("interval") int interval) {

        SummaryButtonParams summaryButtonParams = new SummaryButtonParams(user_id, app_id, start_date, end_date, button_id, interval);
        String result = summaryService.getBtnHistoryCounts(summaryButtonParams);
        return result;
    }

    @Path("/btn_count")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBtnCount(@QueryParam("user_id") long user_id,
                              @QueryParam("app_id") long app_id,
                              @QueryParam("button_id") String button_id,
                              @QueryParam("start_date") String start_date,
                              @QueryParam("end_date") String end_date,
                              @QueryParam("interval") int interval) {

        SummaryButtonParams summaryButtonParams = new SummaryButtonParams(user_id, app_id, start_date, end_date, button_id, interval);
        String result = summaryService.getBtnClickAndOrderCounts(summaryButtonParams);
        return result;
    }

}
