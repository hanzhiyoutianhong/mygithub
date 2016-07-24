package cc.linkedme.api.lkme.web.sdk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
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

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.util.Util;
import cc.linkedme.data.model.LMParams;
import cc.linkedme.data.model.Ride;
import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.GetBtnStatusParams;
import cc.linkedme.data.model.params.InitUberButtonParams;
import cc.linkedme.uber.rides.service.RideService;

@Path("btn/ride")
@Component
public class BtnRideResources {

    @Resource
    RideService uberService;


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
    public String initButton(@FormParam("btn_id") String btnId, @FormParam("source") String source, @BeanParam Ride ride,
            @BeanParam LMParams lmParams) {

        InitUberButtonParams initUberButtonParams = new InitUberButtonParams();
        initUberButtonParams.pickup_lng = ride.getPickupLongitude();
        initUberButtonParams.dropoff_lat = ride.getDropoffLatitude();
        initUberButtonParams.btn_id = btnId;
        initUberButtonParams.dropoff_lng = ride.getDropoffLongitude();
        initUberButtonParams.pickup_label = ride.getPickupLabel();
        initUberButtonParams.pickup_lat = ride.getPickupLatitude();
        initUberButtonParams.dropoff_label = ride.getDropoffLabel();
        initUberButtonParams.identity_id = lmParams.getIdentityId();
        initUberButtonParams.session_id = lmParams.getSessionId();
        initUberButtonParams.source = source;
        initUberButtonParams.sdk_version = lmParams.getSdkVersion();
        initUberButtonParams.retry_times = lmParams.getRetryTimes();
        initUberButtonParams.linkedme_key = Util.formatLinkedmeKey(lmParams.getLinkedmeKey());
        initUberButtonParams.timestamp = lmParams.getTimestamp();
        initUberButtonParams.sign = lmParams.getSign();

        JSONObject requestJson = JSONObject.fromObject(initUberButtonParams);

        String result = uberService.initButton(ride, btnId, source);

        JSONObject log = new JSONObject();
        JSONObject responseJson = JSONObject.fromObject(result);
        log.put("request", requestJson);
        log.put("response", responseJson);

        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", lmParams.getRemoteIp(), "init_uber_btn",
                lmParams.getIdentityId(), lmParams.getLinkedmeKey(), btnId, lmParams.getSessionId(), lmParams.getRetryTimes(),
                lmParams.getSdkVersion(), log.toString()));

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


}
