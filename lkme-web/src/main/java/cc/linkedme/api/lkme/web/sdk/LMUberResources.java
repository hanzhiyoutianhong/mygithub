package cc.linkedme.api.lkme.web.sdk;

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

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import cc.linkedme.auth.SignAuthService;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.util.Util;
import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.GetBtnStatusParams;
import cc.linkedme.data.model.params.InitUberButtonParams;
import cc.linkedme.uber.rides.service.UberService;

import com.google.common.base.Strings;

@Path("btn/ride")
@Component
public class LMUberResources {
    
    @Resource
    UberService uberService;

    @Resource
    private SignAuthService signAuthService;

    
    @Path("/status")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getBtnStatus(@QueryParam("btn_id") String btnId,
                               @QueryParam("linkedme_key") String linkedmeKey,
                               @QueryParam("identity_id") long identityId,
                               @QueryParam("session_id") long sessionId,
                               @QueryParam("sdk_version") String sdkVersion, 
                               @QueryParam("retry_times") int retryTimes,
                               @QueryParam("timestamp") long timestamp, 
                               @QueryParam("sign") String sign,
                               
                               @Context HttpServletRequest request){
        
        GetBtnStatusParams getBtnStatusParams = new GetBtnStatusParams();
        getBtnStatusParams.btnId = btnId;
        getBtnStatusParams.linkedmeKey = linkedmeKey;
        getBtnStatusParams.identityId = identityId;
        getBtnStatusParams.sessionId = sessionId;
        getBtnStatusParams.sdkVersion = sdkVersion;
        getBtnStatusParams.retryTimes = retryTimes;
        getBtnStatusParams.timestamp = timestamp;
        getBtnStatusParams.sign = sign;
        
        JSONObject requestJson = JSONObject.fromObject(getBtnStatusParams);

        String result = uberService.getBtnStatus(getBtnStatusParams);
        
        JSONObject log = new JSONObject();
        JSONObject responseJson = JSONObject.fromObject(result);
        log.put("request", requestJson);
        log.put("response", responseJson);
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "get_uber_btn_status",
            getBtnStatusParams.getBtnId(),getBtnStatusParams.getLinkedmeKey(),getBtnStatusParams.getIdentityId(),
            getBtnStatusParams.getSessionId(), getBtnStatusParams.getSdkVersion(), getBtnStatusParams.getRetryTimes(), 
            getBtnStatusParams.getTimestamp(), getBtnStatusParams.getSign(), log.toString()));
        
        return result;
        
    }

    
    
    @Path("/init")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String initButton(@FormParam("pickup_lng") double pickup_lng, 
                             @FormParam("pickup_lat") double pickup_lat,
                             @FormParam("pickup_label") String pickup_label, 
                             @FormParam("dropoff_lat") double dropoff_lat,
                             @FormParam("dropoff_lng") double dropoff_lng,
                             @FormParam("dropoff_label") String dropoff_label, 
                             @FormParam("btn_id") String btn_id, 
                             @FormParam("source") String source,
                             @FormParam("linkedme_key") String linkedme_key, 
                             @FormParam("identity_id") long identity_id, 
                             @FormParam("session_id") long session_id,
                             @FormParam("sdk_version") String sdk_version, 
                             @FormParam("retry_times") int retry_times,
                             @FormParam("timestamp") long timestamp, 
                             @FormParam("sign") String sign,
                             
                             @Context HttpServletRequest request) {

        InitUberButtonParams initUberButtonParams = new InitUberButtonParams();
        initUberButtonParams.pickup_lng = pickup_lng;
        initUberButtonParams.dropoff_lat = dropoff_lat;
        initUberButtonParams.btn_id = btn_id;
        initUberButtonParams.dropoff_lng = dropoff_lng;
        initUberButtonParams.pickup_label = pickup_label;
        initUberButtonParams.pickup_lat = pickup_lat;
        initUberButtonParams.dropoff_label = dropoff_label;
        initUberButtonParams.identity_id = identity_id;
        initUberButtonParams.session_id = session_id;
        initUberButtonParams.source = source;
        initUberButtonParams.sdk_version = sdk_version;
        initUberButtonParams.retry_times = retry_times;
        initUberButtonParams.linkedme_key = Util.formatLinkedmeKey(linkedme_key);
        initUberButtonParams.timestamp = timestamp;
        initUberButtonParams.sign = sign;

        // String apiName = "/i/uber/init_button";
        // if (!signAuthService.doAuth(apiName, initUberButtonParams.sign,
        // String.valueOf(initUberButtonParams.identity_id), initUberButtonParams.linkedme_key,
        // String.valueOf(initUberButtonParams.session_id),
        // String.valueOf(initUberButtonParams.timestamp))) {
        // throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
        // }
        JSONObject requestJson = JSONObject.fromObject(initUberButtonParams);

        String result = uberService.initButton(initUberButtonParams);

        JSONObject log = new JSONObject();
        JSONObject responseJson = JSONObject.fromObject(result);
        log.put("request", requestJson);
        log.put("response", responseJson);
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "init_uber_btn",
                initUberButtonParams.identity_id, initUberButtonParams.linkedme_key, initUberButtonParams.btn_id,
                initUberButtonParams.session_id, initUberButtonParams.retry_times, initUberButtonParams.is_debug,
                initUberButtonParams.sdk_version, log.toString()));

        
      
        return result;

    }


    @Path("/click")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String clickBtn(
                           @FormParam("btn_id") String btn_id,
                           @FormParam("linkedme_key") String linkedme_key,
                           @FormParam("identity_id") long identity_id,
                           @FormParam("session_id") long session_id, 
                           @FormParam("source") String source,
                           @FormParam("sdk_version") String sdk_version,
                           @FormParam("retry_times") int retry_times, 
                           @FormParam("timestamp") long timestamp, 
                           @FormParam("sign") String sign, 
                           @Context HttpServletRequest request) {
        
        ClickBtnParams clickBtnParams = new ClickBtnParams();
        clickBtnParams.btn_id = btn_id;
        clickBtnParams.identity_id = identity_id;
        clickBtnParams.session_id = session_id;
        clickBtnParams.sdk_version = sdk_version;
        clickBtnParams.source = source;
        clickBtnParams.retry_times = retry_times;
        clickBtnParams.linkedme_key = Util.formatLinkedmeKey(linkedme_key);
        clickBtnParams.timestamp = timestamp;
        clickBtnParams.sign = sign;

        // String apiName = "/i/uber/click_btn";
        // if (!signAuthService.doAuth(apiName, clickBtnParams.sign,
        // String.valueOf(clickBtnParams.identity_id), clickBtnParams.linkedme_key,
        // String.valueOf(clickBtnParams.session_id), String.valueOf(clickBtnParams.timestamp))) {
        // throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
        // }
        JSONObject requestJson = JSONObject.fromObject(clickBtnParams);

        uberService.clickBtn(clickBtnParams);

        JSONObject log = new JSONObject();
        log.put("request", requestJson);
        log.put("response", "{}");
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click_uber_btn",
                clickBtnParams.identity_id, clickBtnParams.linkedme_key, clickBtnParams.btn_id, clickBtnParams.session_id,
                clickBtnParams.retry_times, clickBtnParams.is_debug, clickBtnParams.sdk_version, log.toString()));

        return "{}";
    }



    @Path("/test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String webhooks(@QueryParam("param") String param) {

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
