package cc.linkedme.api.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.data.model.params.DashboardUrlParams;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.webapi.AppService;
import cc.linkedme.service.webapi.SummaryService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.base.Joiner;
import net.sf.json.JSONArray;
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
import java.io.IOException;
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
        if(jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("app_id", String.valueOf(dashboardUrlParams.app_id)));
        params.add(new BasicNameValuePair("ios_use_default", String.valueOf(dashboardUrlParams.ios_use_default)));
        params.add(new BasicNameValuePair("ios_custom_url", String.valueOf(dashboardUrlParams.ios_custom_url)));
        params.add(new BasicNameValuePair("android_use_default", String.valueOf(dashboardUrlParams.android_use_default)));
        params.add(new BasicNameValuePair("android_custom_url", String.valueOf(dashboardUrlParams.android_custom_url)));
        params.add(new BasicNameValuePair("desktop_use_default", String.valueOf(dashboardUrlParams.desktop_use_default)));
        params.add(new BasicNameValuePair("desktop_custom_url", String.valueOf(dashboardUrlParams.desktop_custom_url)));

        Joiner joiner = Joiner.on(",").skipNulls();
        params.add(new BasicNameValuePair("feature", joiner.join(dashboardUrlParams.feature)));
        params.add(new BasicNameValuePair("campaign", joiner.join(dashboardUrlParams.campaign)));
        params.add(new BasicNameValuePair("stage", joiner.join(dashboardUrlParams.stage)));
        params.add(new BasicNameValuePair("channel", joiner.join(dashboardUrlParams.channel)));
        params.add(new BasicNameValuePair("tags", joiner.join(dashboardUrlParams.tags)));
        params.add(new BasicNameValuePair("source", dashboardUrlParams.source));
        params.add(new BasicNameValuePair("params", dashboardUrlParams.params.toString()));

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
                             @QueryParam("return_number") int return_number,
                             @QueryParam("skip_number") int skip_number,
                             @QueryParam("orderby") String orderby) {

        SummaryDeepLinkParams summaryDeepLinkParams = new SummaryDeepLinkParams(appid, start_date, end_date, feature, campaign, stage,
                channel, tag, source, unique, return_number, skip_number, orderby);
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
}
