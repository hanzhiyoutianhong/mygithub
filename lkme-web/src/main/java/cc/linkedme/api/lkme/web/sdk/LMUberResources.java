package cc.linkedme.api.lkme.web.sdk;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.data.model.params.OpenUberParams;
import cc.linkedme.uber.rides.client.model.WebhooksParams;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Path("uber")
@Component
public class LMUberResources {

    @Path("/init_button")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String initButton(OpenUberParams openUberParams) {

        return null;
    }

    @Path("/webhooks")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String webhooks(WebhooksParams webhooksParams) {

        System.out.println(webhooksParams.event_id);
        System.out.println(webhooksParams.toJson());
        ApiLogger.info(webhooksParams.toJson());
        String result = webhooksParams.toJson();
        return result;
    }

    @Path("/test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String webhooks( @QueryParam("param") String param) {

        return "{\"hello\":" + param + "}";
    }

    @Path("/sdk")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String sdk(@QueryParam("linkedme_key") String linkedMEKey,
                      @QueryParam("button_id") String deviceId) {
        if (Strings.isNullOrEmpty(linkedMEKey)) {

        }

        String result = null;
        return result;
    }

    @Path("/callback")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String callback(@QueryParam("linkedme_key") String linkedMEKey,
                           @QueryParam("button_id") String deviceId,
                           @QueryParam("device_type") Byte deviceType) {
        if (Strings.isNullOrEmpty(linkedMEKey)) {

        }

        String result = null;

        return result;
    }
}
