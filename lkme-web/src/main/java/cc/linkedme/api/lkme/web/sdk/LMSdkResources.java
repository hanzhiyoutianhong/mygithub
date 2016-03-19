package cc.linkedme.api.lkme.web.sdk;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;

import cc.linkedme.service.sdkapi.LMSdkService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("v1")
@Component
public class LMSdkResources {

    @Resource
    private LMSdkService lmSdkService;

    public void setLmSdkService(LMSdkService lmSdkService) {
        this.lmSdkService = lmSdkService;
    }

    @Path("/install")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String install(@QueryParam("linkedme_key") String linkedMEKey,
                          @QueryParam("device_id") String deviceId,
                          @QueryParam("device_type") Byte deviceType,
                          @QueryParam("device_brand") String deviceBrand,
                          @QueryParam("device_model") String deviceModel,
                          @QueryParam("has_bluetooth") boolean hasBluetooth,
                          @QueryParam("has_nfc") boolean hasNfc,
                          @QueryParam("has_sim") boolean hasSim,
                          @QueryParam("os") String os,
                          @QueryParam("os_version") String osVersion,
                          @QueryParam("screen_dpi") int screenDpi,
                          @QueryParam("screen_height") int screenHeight,
                          @QueryParam("screen_width") int screenWidth,
                          @QueryParam("is_wifi") boolean isWifi,
                          @QueryParam("is_referable") boolean isReferable,
                          @QueryParam("lat_val") String latVal,
                          @QueryParam("carrier") String carrier,
                          @QueryParam("app_version") String appVersion,
                          @QueryParam("sdk_update") String sdkUpdate,
                          @QueryParam("sdk_version") String sdkVersion,
                          @QueryParam("iOS_team_id") String iOSTeamId,
                          @QueryParam("iOS_bundle_id") String iOSBundleId,
                          @QueryParam("is_debug") boolean isDebug ,
                          @QueryParam("retry_times") int retryTimes,
                          @Context HttpServletRequest request) {
        if (Strings.isNullOrEmpty(linkedMEKey)) {

        }

        LMInstallParams lmInstallParams = new LMInstallParams(linkedMEKey, 0L, null, sdkVersion, retryTimes, isDebug, deviceId, deviceType, deviceBrand, deviceModel, hasBluetooth, hasNfc, hasSim, os, osVersion, screenDpi, screenHeight, screenWidth, isWifi, isReferable, latVal, carrier, appVersion, iOSTeamId, iOSBundleId);
        String result = lmSdkService.install(lmInstallParams);
        return result;
    }

    @Path("/open")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String open(@QueryParam("device_fingerprint_id") String device_fingerprint_id,
                       @QueryParam("identity_id") long identity_id,
                       @QueryParam("is_referrable") boolean is_referable,
                       @QueryParam("app_version") String app_version,
                       @QueryParam("os_version") String os_version,
                       @QueryParam("sdk_update") int sdk_update,
                       @QueryParam("os") String os,
                       @QueryParam("is_debug") boolean is_debug,
                       @QueryParam("lat_val") String lat_val,
                       @QueryParam("sdk_version") String sdk_version,
                       @QueryParam("retry_times") int retry_times,
                       @QueryParam("linkedme_key") String linkedme_key,
                       @QueryParam("sign") String sign) {

        LMOpenParams lmOpenParams = new LMOpenParams(device_fingerprint_id, identity_id, is_referable, app_version, os_version, sdk_update,
                os, is_debug, lat_val, sdk_version, retry_times, linkedme_key);

        String result = lmSdkService.open(lmOpenParams);

        return "";

    }


    @Path("/close")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String close(@QueryParam("linkedme_key") String linkedMeKey,
                        @QueryParam("identity_id") String identifyId,
                        @QueryParam("device_fingerprint_id") String deviceFingerprintId,
                        @QueryParam("sdk") String sdk,
                        @QueryParam("session_id") String sessionId,
                        @QueryParam("retry_number") String retryNumber,
                        @Context HttpServletRequest request) {
        if (Strings.isNullOrEmpty(linkedMeKey)) {

        }

//        LMCloseParams lmCloseParams = new LMCloseParams(linkedMeKey, sdk, retryNumber, null, identifyId,
//                                                            deviceFingerprintId, sessionId);
//        String result = lmSdkService.close(lmCloseParams);
        return "";
    }

    @Path("/url")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url(@FormParam("linkedme_key") String linkedmeKey,
                      @FormParam("appid") long appid,
                      @FormParam("identity_id") long identityId,
                      @FormParam("device_fingerprint_id") String deviceFingerprintId,
                      @FormParam("tags") String tags,
                      @FormParam("alias") String alias,
                      @FormParam("channel") String channel,
                      @FormParam("feature") String feature,
                      @FormParam("stage") String stage,
                      @FormParam("campaign") String campaign,
                      @FormParam("params") String params,
                      @FormParam("source") String source,
                      @FormParam("sdk_version") String sdkVersion,
                      @FormParam("session_id") String sessionId,
                      @FormParam("retry_times") int retryTimes,
                      @FormParam("debug") boolean debug) {

        LMUrlParams lmUrlParams = new LMUrlParams(linkedmeKey, appid, identityId, deviceFingerprintId, sdkVersion, retryTimes, debug, tags, alias,
                channel, feature, stage, campaign, params, source, sessionId);

        String url = lmSdkService.url(lmUrlParams);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("url", url);
        return resultJson.flip().toString();

    }

    @Path("/preInstall")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url(@FormParam("link_click_id") String linkClickId) {

        String result = lmSdkService.preInstall(linkClickId);

        return result;
    }

}
