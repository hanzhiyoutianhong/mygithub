package cc.linkedme.api.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.webapi.SummaryService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
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

    private static final String CREATE_URL_API = "https://lkme.cc/sdk/url";

    @Path("/create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String createUrl(UrlParams urlParams, @Context HttpServletRequest request) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(CREATE_URL_API);
        String result = null;
        try {
            RequestEntity se = new StringRequestEntity(JSONObject.fromObject(urlParams).toString(), "application/json", "UTF-8");
            postMethod.setRequestEntity(se);
            client.executeMethod(postMethod);
            result = new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        postMethod.releaseConnection();
        if(Strings.isNullOrEmpty(result)) {
            throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "create deeplink failed!");
        }
        return result;
    }

    @Path("/list")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUrlList(@QueryParam("app_id") long appid,
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
    @Produces()
    public String deleteUrl(UrlParams urlParams, @Context HttpServletRequest request) {
        if (urlParams.deeplink_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "deeplink_id <= 0");
        }
        if (urlParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }
        boolean result = deepLinkService.deleteDeepLink(urlParams.deeplink_id, urlParams.app_id);
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
        String urlInfo = deepLinkService.getUrlInfo( urlParams );

        return urlInfo;
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String urlUpdate( UrlParams urlParams, @Context HttpServletRequest request) {//忽略type和link_label
        if (urlParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "deeplink_id <= 0");
        }
        if (urlParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }

        Boolean res = deepLinkService.updateUrl( urlParams );

        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "ret", res );

        return jsonObject.toString();
    }
}
