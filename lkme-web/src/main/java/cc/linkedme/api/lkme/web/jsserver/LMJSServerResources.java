package cc.linkedme.api.lkme.web.jsserver;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.data.model.params.JsActionsParams;
import cc.linkedme.data.model.params.JsRecordIdParams;
import cc.linkedme.service.sdkapi.JsService;

@Path("js")
@Component
public class LMJSServerResources {

    @Resource
    private JsService jsService;

    @Path("/lmactions")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String lmActions(JsActionsParams jsActionsParams, @Context HttpServletRequest request) {

        String deepLinkId = jsActionsParams.getDeepLinkId();
        int lmTag = jsActionsParams.getLmTag();
        String destination = jsActionsParams.getDestination();

        JSONObject resultJson = new JSONObject();

        resultJson.put("deepLinkId", deepLinkId);
        resultJson.put("lmTag", lmTag);
        resultJson.put("destination", destination);

        ApiLogger.info(resultJson.toString());

        return null;
    }

    @Path("/record_id")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String recordId(@FormParam("identity_id") long identity_id,
                           @FormParam("app_id") long app_id,
                           @FormParam("is_valid_identityid") boolean is_valid_identityid,
                           @FormParam("browser_fingerprint_id") String browser_fingerprint_id,
                           @FormParam("deeplink_id") long deeplink_id,
                           @FormParam("is_pc_scan") boolean is_pc_scan,
                           @Context HttpServletRequest request) {

        JsRecordIdParams jsRecordIdParams = new JsRecordIdParams();
        jsRecordIdParams.identity_id = identity_id;
        jsRecordIdParams.is_valid_identityid = is_valid_identityid;
        jsRecordIdParams.browser_fingerprint_id = browser_fingerprint_id;
        jsRecordIdParams.deeplink_id = deeplink_id;
        jsRecordIdParams.is_pc_scan = is_pc_scan;
        jsService.recordId(jsRecordIdParams);

        String clientIP = request.getHeader("x-forwarded-for");
        String isValidIdentityIdForLog = "is_valid_identityid=" + is_valid_identityid;
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s", clientIP, "record_id", identity_id, app_id, deeplink_id, browser_fingerprint_id, isValidIdentityIdForLog));
        return "{}";
    }
    
    @Path("/record_id_and_redirect")
    @GET
    public Response recordIdAndRedirect(@QueryParam("identity_id") long identity_id,
                           @QueryParam("app_id") long app_id,
                           @QueryParam("is_valid_identityid") boolean is_valid_identityid,
                           @QueryParam("browser_fingerprint_id") String browser_fingerprint_id,
                           @QueryParam("deeplink_id") long deeplink_id,
                           @QueryParam("url") String url,
                           @Context HttpServletRequest request){
        
        JsRecordIdParams jsRecordIdParams = new JsRecordIdParams();
        jsRecordIdParams.identity_id = identity_id;
        jsRecordIdParams.is_valid_identityid = is_valid_identityid;
        jsRecordIdParams.browser_fingerprint_id = browser_fingerprint_id;
        jsRecordIdParams.deeplink_id = deeplink_id;
        jsService.recordId(jsRecordIdParams);
        
        String clientIP = request.getHeader("x-forwarded-for");
        String isValidIdentityIdForLog = "is_valid_identityid=" + is_valid_identityid;
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", clientIP, "record_id_and_redirect", identity_id, app_id, deeplink_id,
                browser_fingerprint_id, isValidIdentityIdForLog, url));
       
        URI downloadUri = null;
        try {
            //设计专门的错误提示页
            downloadUri = new URI("http://www.linkedme.cc");
            downloadUri = new URI(url);
        } catch (URISyntaxException e) {
            ApiLogger.warn("download url(" + url + ") error", e);
        }
        
        return Response.temporaryRedirect(downloadUri).build();
    }

    @Path("/record_event")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String recordJSEvent(@FormParam("destination") String destination,
                                @FormParam("identity_id") String identity_id,
                                @FormParam("app_id") String app_id,
                                @FormParam("deeplink_id") String deeplink_id, @Context HttpServletRequest request){
        String clientIP = request.getHeader("x-forwarded-for");
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", clientIP, "js_event", identity_id, app_id, deeplink_id, destination));
        return null;
    }

    @Path("/record_click_event")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String recordClickEvent(@FormParam("destination") String destination,
                                   @FormParam("identity_id") long identity_id,
                                   @FormParam("app_id") long app_id,
                                   @FormParam("deeplink_id") long deeplink_id, @Context HttpServletRequest request){
        String clientIP = request.getHeader("x-forwarded-for");
        ApiLogger.biz(
                String.format("%s\t%s\t%s\t%s\t%s\t%s", clientIP, "user_click_event", identity_id, app_id, deeplink_id, destination));
        return null;
    }

}
