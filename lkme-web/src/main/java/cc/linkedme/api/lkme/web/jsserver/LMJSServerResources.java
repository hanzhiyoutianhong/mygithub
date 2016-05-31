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
    public String recordId(JsRecordIdParams jsRecordIdParams, @Context HttpServletRequest request) {
        jsService.recordId(jsRecordIdParams);
        return "{}";
    }


}
