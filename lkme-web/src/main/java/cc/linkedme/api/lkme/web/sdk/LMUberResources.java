package cc.linkedme.api.lkme.web.sdk;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.params.InitUberButtonParams;
import cc.linkedme.service.webapi.BtnService;
import cc.linkedme.service.webapi.ConsumerService;
import cc.linkedme.uber.rides.client.Session;
import cc.linkedme.uber.rides.client.UberRidesServices;
import cc.linkedme.uber.rides.client.UberRidesSyncService;
import cc.linkedme.uber.rides.client.error.NetworkException;
import cc.linkedme.uber.rides.client.model.PriceEstimate;
import cc.linkedme.uber.rides.client.model.PriceEstimatesResponse;
import cc.linkedme.uber.rides.client.model.WebhooksParams;
import cc.linkedme.uber.rides.service.UberService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import java.util.List;

@Path("uber")
@Component
public class LMUberResources {
    @Resource
    UberService uberService;

    @Path("/init_button")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String initButton(InitUberButtonParams initUberButtonParams) {
        String result = uberService.initButton(initUberButtonParams);
        return result;
    }

    @Path(("/click_btn"))
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String clickBtn() {
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
