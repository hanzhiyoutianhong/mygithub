package cc.linkedme.api.lkme.web.sdk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.data.model.params.LMCloseParams;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;
import cc.linkedme.service.sdkapi.LMSdkService;

import com.google.common.base.Strings;

@Path("uber")
@Component
public class LMUberResources {

    @Resource
    private LMSdkService lmSdkService;

    public void setLmSdkService(LMSdkService lmSdkService) {
        this.lmSdkService = lmSdkService;
    }

    @Path("/sdk")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String sdk(@QueryParam("linkedme_key") String linkedMEKey,
                          @QueryParam("button_id") String deviceId,
                          @QueryParam("device_type") Byte deviceType) {
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
