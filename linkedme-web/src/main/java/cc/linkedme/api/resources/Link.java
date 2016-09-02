package cc.linkedme.api.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.DashboardUrlParams;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.webapi.AppService;
import cc.linkedme.service.webapi.SummaryService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.base.Joiner;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/3/30.
 */

@Path("url")
@Component
public class Link {
    @Resource
    private SummaryService summaryService;

    @Resource
    private DeepLinkService deepLinkService;

    @Resource
    private AppService appService;

    @Path("/create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String createUrl(DashboardUrlParams dashboardUrlParams, @Context HttpServletRequest request) {
        JSONArray jsonArray = getCheckParamResult(dashboardUrlParams);
        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        if (Strings.isNullOrEmpty(dashboardUrlParams.live_test_flag)) {
            dashboardUrlParams.live_test_flag = "live";
        }

        UrlParams urlParams = new UrlParams();
        urlParams.app_id = dashboardUrlParams.app_id;
        urlParams.promotion_name = dashboardUrlParams.promotion_name;
        urlParams.live_test_flag = dashboardUrlParams.live_test_flag;
        if (appService.validPromotionName(urlParams)) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "Duplicated promotion name!");
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("app_id", String.valueOf(dashboardUrlParams.app_id)));
        params.add(new BasicNameValuePair("promotion_name", String.valueOf(dashboardUrlParams.promotion_name)));
        params.add(new BasicNameValuePair("ios_use_default", String.valueOf(dashboardUrlParams.ios_use_default)));
        params.add(new BasicNameValuePair("ios_custom_url", dashboardUrlParams.ios_custom_url));
        params.add(new BasicNameValuePair("android_use_default", String.valueOf(dashboardUrlParams.android_use_default)));
        params.add(new BasicNameValuePair("android_custom_url", dashboardUrlParams.android_custom_url));
        params.add(new BasicNameValuePair("desktop_use_default", String.valueOf(dashboardUrlParams.desktop_use_default)));
        params.add(new BasicNameValuePair("desktop_custom_url", dashboardUrlParams.desktop_custom_url));

        Joiner joiner = Joiner.on(",").skipNulls();
        dashboardUrlParams.feature = dashboardUrlParams.feature == null ? new String[0] : dashboardUrlParams.feature;
        dashboardUrlParams.campaign = dashboardUrlParams.campaign == null ? new String[0] : dashboardUrlParams.campaign;
        dashboardUrlParams.stage = dashboardUrlParams.stage == null ? new String[0] : dashboardUrlParams.stage;
        dashboardUrlParams.channel = dashboardUrlParams.channel == null ? new String[0] : dashboardUrlParams.channel;
        dashboardUrlParams.tags = dashboardUrlParams.tags == null ? new String[0] : dashboardUrlParams.tags;
        params.add(new BasicNameValuePair("feature", joiner.join(dashboardUrlParams.feature)));
        params.add(new BasicNameValuePair("campaign", joiner.join(dashboardUrlParams.campaign)));
        params.add(new BasicNameValuePair("stage", joiner.join(dashboardUrlParams.stage)));
        params.add(new BasicNameValuePair("channel", joiner.join(dashboardUrlParams.channel)));
        params.add(new BasicNameValuePair("tags", joiner.join(dashboardUrlParams.tags)));
        params.add(new BasicNameValuePair("source", dashboardUrlParams.source));
        params.add(new BasicNameValuePair("params", dashboardUrlParams.params.toString()));
        params.add(new BasicNameValuePair("type", dashboardUrlParams.live_test_flag));

        HttpClient client = new DefaultHttpClient();
        String result = null;
        HttpPost postMethod = new HttpPost(Constants.CREATE_URL_API);
        try {
            postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            result = EntityUtils.toString(client.execute(postMethod).getEntity(), HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.getConnectionManager().shutdown();
        if (Strings.isNullOrEmpty(result)) {
            throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "create deeplink failed!");
        }

        // 把短链的tags添加到库里
        appService.addUrlTags(dashboardUrlParams);

        return result;
    }

    @Path("/list")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUrlList(@QueryParam("app_id") int appid,
                             @QueryParam("start_date") String start_date,
                             @QueryParam("end_date") String end_date,
                             @QueryParam("feature") String feature,
                             @QueryParam("campaign") String campaign,
                             @QueryParam("stage") String stage,
                             @QueryParam("channel") String channel,
                             @QueryParam("tag") String tag,
                             @QueryParam("source") String source,
                             @QueryParam("unique") boolean unique,
                             @QueryParam("live_test_flag") String liveTestFlag,
                             @QueryParam("return_number") int return_number,
                             @QueryParam("skip_number") int skip_number,
                             @QueryParam("orderby") String orderby,
                             @QueryParam("order") String order) {

        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams(appid, start_date, end_date, feature, campaign, stage,
                channel, tag, source, unique, return_number, skip_number, orderby, order);
        if (Strings.isNullOrEmpty(liveTestFlag)) {
            summaryDeepLinkParams.liveTestFlag = "live";
        } else {
            summaryDeepLinkParams.liveTestFlag = liveTestFlag;
        }
        String deepLinks = summaryService.getDeepLinksWithCount(summaryDeepLinkParams);
        if (Strings.isNullOrEmpty(deepLinks)) {
            return "{\"total_count\":0, \"ret\":[]}";
        }

        return deepLinks;
    }

    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUrl(DashboardUrlParams dashboardUrlParams, @Context HttpServletRequest request) {
        // if (urlParams.deeplink_id <= 0) {
        // throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "deeplink_id <= 0");
        // }
        // if (urlParams.app_id <= 0) {
        // throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        // }

        boolean result = deepLinkService.deleteDeepLink(dashboardUrlParams.deeplink_ids, dashboardUrlParams.app_id);
        return "{ \"ret\" : " + result + "}";
    }

    @Path("info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String urlInfo(@QueryParam("user_id") int user_id,
                          @QueryParam("app_id") long app_id,
                          @QueryParam("deeplink_id") long deeplink_id ) { //忽略type和link_label
        if (user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "user_id <= 0");
        }
        if (app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }
        if (deeplink_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "deeplink_id <= 0");
        }
        UrlParams urlParams = new UrlParams();
        urlParams.app_id = app_id;
        urlParams.user_id = user_id;
        urlParams.deeplink_id = deeplink_id;
        String urlInfo = deepLinkService.getUrlInfo(urlParams);

        return urlInfo;
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String urlUpdate(DashboardUrlParams dashboardUrlParams, @Context HttpServletRequest request) {// 忽略type和link_label
        JSONArray jsonArray = getCheckParamResult(dashboardUrlParams);
        if (dashboardUrlParams.deeplink_id <= 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err_code", 40001);
            jsonObject.put("err_param", "deeplink_id");
            jsonObject.put("err_msg", "深度链接ID不能为空!");
            jsonArray.add(jsonObject);
        }

        if (Strings.isNullOrEmpty(dashboardUrlParams.promotion_name)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err_code", 40002);
            jsonObject.put("err_param", "promotion_name");
            jsonObject.put("err_msg", "推广名称不能为空!");
            jsonArray.add(jsonObject);
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        if (Strings.isNullOrEmpty(dashboardUrlParams.live_test_flag)) {
            dashboardUrlParams.live_test_flag = "live";
        }

        boolean res = deepLinkService.updateUrl(dashboardUrlParams);

        if (res) {
            // 把短链的tags添加到库里
            appService.addUrlTags(dashboardUrlParams);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ret", res);

        return jsonObject.toString();
    }

    private JSONArray getCheckParamResult(DashboardUrlParams dashboardUrlParams) {
        JSONArray jsonArray = new JSONArray();
        if (dashboardUrlParams.app_id <= 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err_code", 40001);
            jsonObject.put("err_param", "app_id");
            jsonObject.put("err_msg", "App ID无效!");
            jsonArray.add(jsonObject);
        }

        if (!dashboardUrlParams.ios_use_default) {
            if (Strings.isNullOrEmpty(dashboardUrlParams.ios_custom_url)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("err_code", 40001);
                jsonObject.put("err_param", "ios_custom_url");
                jsonObject.put("err_msg", "自定义URL不能为空!");
                jsonArray.add(jsonObject);
            }
        }

        if (!dashboardUrlParams.android_use_default) {
            if (Strings.isNullOrEmpty(dashboardUrlParams.android_custom_url)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("err_code", 40001);
                jsonObject.put("err_param", "android_custom_url");
                jsonObject.put("err_msg", "自定义URL不能为空!");
                jsonArray.add(jsonObject);
            }
        }

        if (!dashboardUrlParams.desktop_use_default) {
            if (Strings.isNullOrEmpty(dashboardUrlParams.desktop_custom_url)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("err_code", 40001);
                jsonObject.put("err_param", "desktop_custom_url");
                jsonObject.put("err_msg", "自定义URL不能为空!");
                jsonArray.add(jsonObject);
            }
        }

        return jsonArray;
    }

    // promotion_name, channel, tag, params
    @POST
    @Path("/batch_create/{app_id}/{live_test_flag}")
    @Produces("application/json;charset=UTF-8")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String batchUrlCreation(@FormDataParam("file") InputStream csvData,
                                   @FormDataParam("file") FormDataContentDisposition dataDetail,
                                   @PathParam("app_id") long appId,
                                   @PathParam("live_test_flag") String live_test_flag) {

        List<DashboardUrlParams> urlDatas = new ArrayList<>();
        List<String> resultDatas = new ArrayList<>();
        JSONArray resultJson = new JSONArray();
        //AppInfo appInfo = appService.getAppById(appId);
        AppInfo appInfo = new AppInfo();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvData));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println( line );
                DashboardUrlParams urlData = new DashboardUrlParams();
                String[] data = line.split("\t", 4 );
                if( data.length < 2 ) {
                    continue;
                }
                urlData.app_id = appId;
                urlData.promotion_name = data[0];
                urlData.channel = Strings.isNullOrEmpty(data[1]) ? new String[0] : data[1].split(";");
                urlData.tags = Strings.isNullOrEmpty(data[2]) ? new String[0] : data[2].split(";");
                urlData.params = Strings.isNullOrEmpty(data[3]) ? JSONObject.fromObject((new String[0])) : JSONObject.fromObject(data[3]);
                urlData.ios_use_default = true;
                urlData.ios_custom_url = appInfo.getIos_custom_url();
                urlData.android_use_default = true;
                urlData.android_custom_url = appInfo.getAndroid_custom_url();
                urlData.desktop_use_default = true;
                urlData.desktop_custom_url = appInfo.getCustom_landing_page();
                urlData.source = "Dashboard";
                urlData.linkedme_key = appInfo.getApp_key();
                urlData.live_test_flag = live_test_flag;
                String res = createUrl(urlData, null);
                JSONObject jsonObject;
                try {
                    jsonObject = JSONObject.fromObject(res);
                } catch (JSONException e) {
                    jsonObject = null;
                }
                if (jsonObject == null) {
                    jsonObject.put(urlData.promotion_name, "failed");
                }
                resultJson.add(jsonObject);
                resultDatas.add(res);
                urlDatas.add(urlData);
            }
        } catch (Exception e) {
            new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "illegal csv params!");
        }
        return resultJson.toString();
    }
}
