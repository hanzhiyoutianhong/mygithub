package cc.linkedme.api.lkme.web.sdk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import cc.linkedme.auth.SignAuthService;
import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.InitUberButtonParams;
import cc.linkedme.uber.rides.service.UberService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Path("uber")
@Component
public class LMUberResources {
    @Resource
    UberService uberService;

    @Resource
    private SignAuthService signAuthService;

    @Path("/init_button")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String initButton(InitUberButtonParams initUberButtonParams, @Context HttpServletRequest request) {
//        String apiName = "/i/uber/init_button";
//        if (!signAuthService.doAuth(apiName, initUberButtonParams.sign, String.valueOf(initUberButtonParams.identity_id), initUberButtonParams.linkedme_key, String.valueOf(initUberButtonParams.session_id), String.valueOf(initUberButtonParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }

        String result = uberService.initButton(initUberButtonParams);

        JSONObject log = new JSONObject();
        JSONObject requestJson = JSONObject.fromObject(initUberButtonParams);
        JSONObject responseJson = JSONObject.fromObject(result);
        log.put("request", requestJson);
        log.put("response", responseJson);
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "init_uber_btn", initUberButtonParams.identity_id,
                initUberButtonParams.linkedme_key, initUberButtonParams.btn_id, initUberButtonParams.session_id, initUberButtonParams.retry_times,
                initUberButtonParams.is_debug, initUberButtonParams.sdk_version, log.toString()));

        return result;
    }

    @Path("/click_btn")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String clickBtn(ClickBtnParams clickBtnParams, @Context HttpServletRequest request) {
//        String apiName = "/i/uber/click_btn";
//        if (!signAuthService.doAuth(apiName, clickBtnParams.sign, String.valueOf(clickBtnParams.identity_id), clickBtnParams.linkedme_key, String.valueOf(clickBtnParams.session_id), String.valueOf(clickBtnParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }

        uberService.clickBtn(clickBtnParams);

        JSONObject log = new JSONObject();
        JSONObject requestJson = JSONObject.fromObject(clickBtnParams);
        log.put("request", requestJson);
        log.put("response", "{}");
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click_uber_btn", clickBtnParams.identity_id,
                clickBtnParams.linkedme_key, clickBtnParams.btn_id, clickBtnParams.session_id, clickBtnParams.retry_times,
                clickBtnParams.is_debug, clickBtnParams.sdk_version, log.toString()));

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
