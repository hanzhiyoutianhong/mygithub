package cc.linkedme.api.lkme.web.sdk;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.InitUberButtonParams;
import cc.linkedme.uber.rides.service.UberService;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

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

    @Path("/click_btn")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String clickBtn(ClickBtnParams clickBtnParams) {
        uberService.clickBtn(clickBtnParams);
        return "{}";
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
