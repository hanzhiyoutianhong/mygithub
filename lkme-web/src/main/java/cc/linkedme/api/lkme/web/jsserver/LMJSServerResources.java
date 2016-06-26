package cc.linkedme.api.lkme.web.jsserver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.data.model.params.JsActionsParams;
import cc.linkedme.data.model.params.JsRecordIdParams;
import cc.linkedme.service.sdkapi.JsService;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

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
                           @FormParam("is_valid_identityid") boolean is_valid_identityid,
                           @FormParam("browser_fingerprint_id") String browser_fingerprint_id,
                           @FormParam("deeplink_id") long deeplink_id,
                           @Context HttpServletRequest request) {

        JsRecordIdParams jsRecordIdParams = new JsRecordIdParams();
        jsRecordIdParams.identity_id = identity_id;
        jsRecordIdParams.is_valid_identityid = is_valid_identityid;
        jsRecordIdParams.browser_fingerprint_id = browser_fingerprint_id;
        jsRecordIdParams.deeplink_id = deeplink_id;
        jsService.recordId(jsRecordIdParams);
        return "{}";
    }

    @Path("/record_event")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String recordJSEvent(@FormParam("destination") String destination,
                                @FormParam("identity_id") long identity_id,
                                @FormParam("is_valid_identity_id") boolean is_valid_identity_id,
                                @FormParam("browser_fingerprint_id") String browser_fingerprint_id,
                                @FormParam("app_id") long app_id,
                                @FormParam("deeplink_id") long deeplink_id){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("identityId", identity_id );
        jsonObject.put("is_valid_identity_id", is_valid_identity_id );
        jsonObject.put("app_id", app_id );
        jsonObject.put("deeplink_id", deeplink_id );
        jsonObject.put("destination", destination );
        ApiLogger.info( jsonObject );
        return null;
    }

    @Path("/record_click_event")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String recordClickEvent(@FormParam("destination") String destination,
                                   @FormParam("identity_id") long identity_id,
                                   @FormParam("is_valid_identity_id") boolean is_valid_identity_id,
                                   @FormParam("browser_fingerprint_id") String browser_fingerprint_id,
                                   @FormParam("app_id") long app_id,
                                   @FormParam("deeplink_id") long deeplink_id,
                                   @FormParam("click_id") long click_id){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("identityId", identity_id );
        jsonObject.put("is_valid_identity_id", is_valid_identity_id );
        jsonObject.put("app_id", app_id );
        jsonObject.put("deeplink_id", deeplink_id );
        jsonObject.put("click_id", click_id);
        jsonObject.put("destination", destination );
        ApiLogger.info( jsonObject );
        return null;
    }

}
