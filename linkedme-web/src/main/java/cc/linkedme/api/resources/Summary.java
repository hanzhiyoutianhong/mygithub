package cc.linkedme.api.resources;

import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cc.linkedme.commons.client.balancer.util.SystemTimer;
import cc.linkedme.commons.util.ArrayUtil;
import cc.linkedme.data.model.DeepLinkDateCount;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.params.SummaryButtonParams;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.service.webapi.SummaryService;
import org.springframework.util.CollectionUtils;

/**
 * Created by LinkedME01 on 16/3/20.
 */

@Path("summary")
@Component
public class Summary {
    @Resource
    private SummaryService summaryService;

    private static final long baseOfDevices = 2503030L;
    private static long addNum = 0L;
    private static long five_second_stamp = 1L;
    private static long sumOfDevices = 0L;

    @Path("/counts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDeepLinkCounts(@QueryParam("app_id") int appId,
                                    @QueryParam("start_date") String start_date,
                                    @QueryParam("end_date") String end_date,
                                    @QueryParam("feature") String feature,
                                    @QueryParam("campaign") String campaign,
                                    @QueryParam("stage") String stage,
                                    @QueryParam("channel") String channel,
                                    @QueryParam("tag") String tag,
                                    @QueryParam("source") String source,
                                    @QueryParam("unique") boolean unique,
                                    @QueryParam("token") String token) {

        SummaryDeepLinkParams summaryDeepLinkParams =
                new SummaryDeepLinkParams(appId, start_date, end_date, feature, campaign, stage, channel, tag, source, unique);
        return summaryService.getDeepLinksHistoryCounts(summaryDeepLinkParams);
    }

    @Path("/deeplink_count_history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDeepLinkHistoryCount(@QueryParam("app_id") int appid,
                                          @QueryParam("deeplink_id") long deeplink_id,
                                          @QueryParam("start_date") String start_date,
                                          @QueryParam("end_date") String end_date,
                                          @QueryParam("token") String token) {

        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams(appid, deeplink_id, start_date, end_date);
        String result = summaryService.getDeepLinkHistoryCount(summaryDeepLinkParams);
        return result;
    }

    @Path("multi_deeplink_count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMultiDeepLinkInfo(@QueryParam("deeplink_ids") String deeplinkIds,
                                       @QueryParam("app_id") int appId,
                                       @QueryParam("start_date") String startDate,
                                       @QueryParam("end_date") String endDate,
                                       @QueryParam("token") String token) {
        String[] deeplinkIdsArr = deeplinkIds.split(",");
        if (deeplinkIdsArr == null || deeplinkIdsArr.length == 0) {
            return "{}";
        }
        String result = summaryService.getDeepLinksCounts(appId, deeplinkIdsArr, startDate, endDate);
        return result;
    }

    @Path("/sum_of_devices")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public long getSumOfDevices() {
        long timestamp = System.currentTimeMillis();
        if (addNum == 0L) addNum += (timestamp / 1000 - 1466691000L) * 10;
        if ((timestamp / 5000) != five_second_stamp) {
            five_second_stamp = timestamp / 5000;
            int pct = (int) (Math.random() * 10);
            if (pct == 1) {
                addNum += (long) (Math.random() * 80000);
            } else if (pct == 2) {
                addNum -= (long) (Math.random() * 80000);
            }
            else if (pct % 2 == 0) {
                addNum += (long) (Math.random() * 30000);
            } else {
                addNum -= (long) (Math.random() * 30000);
            }
            sumOfDevices = addNum + (timestamp / 1000 - 1466397100L) * 5;
        }
        return sumOfDevices + baseOfDevices;
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

    @Deprecated
    @Path("/deeplinks_count_history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDeepLinksHistoryCount(@QueryParam("app_id") int appid,
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
        String result = summaryService.getDeepLinksHistoryCount(summaryDeepLinkParams);
        return result;
    }

}
