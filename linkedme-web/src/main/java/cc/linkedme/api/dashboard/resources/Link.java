package cc.linkedme.api.dashboard.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.data.model.params.UrlParams;
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

    @Path("/create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String createUrl(UrlParams urlParams, @Context HttpServletRequest request) {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8888/sdk/url");
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
}