package cc.linkedme.api.lkme.web.sdk;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.params.LMCloseParams;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;
import cc.linkedme.service.LMSdkService;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("v1")
@Component
public class LMSdkResources {

    @Resource
    private LMSdkService lmSdkService;

    @Path("/install")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String install(@QueryParam("linkedme_key") String linkedMeKey,
                          @QueryParam("hardware_id") String hardwareId,
                          @QueryParam("google_advertising_id") String googleAdvertisingId,
                          @QueryParam("is_hardware_id_real") String isHardwareIdReal,
                          @QueryParam("ad_tracking_enabled") String adTrackingEnabled,
                          @QueryParam("brand") String brand,
                          @QueryParam("carrier") String carrier,
                          @QueryParam("ios_bundle_id") String iOSBundleId,
                          @QueryParam("is_referable") String isReferable,
                          @QueryParam("os") String os,
                          @QueryParam("osVersion") String osVersion,
                          @QueryParam("app_version") String appVersion,
                          @QueryParam("sdk") String sdk,
                          @QueryParam("update") String update,
                          @QueryParam("uri_scheme") String uriScheme,
                          @QueryParam("ios_team_id") String iOSTeamId,
                          @QueryParam("universal_link_url") String universalLinkUrl,
                          @QueryParam("spotlight_identifier") String spotlightIdentifier,
                          @QueryParam("lat_val") String latVal,
                          @QueryParam("wifi") String wifi,
                          @QueryParam("has_nfc") String hasNfc,
                          @QueryParam("has_telephone") String hasTelephone,
                          @QueryParam("bluetooth") String bluetooth,
                          @QueryParam("screen_dpi") String screenDpi,
                          @QueryParam("screen_height") String screenHeight,
                          @QueryParam("screen_width") String screenWidth,
                          @QueryParam("retry_number") String retryNumber,
                          @QueryParam("debug") String debug,
                          @Context HttpServletRequest request) {
        if (Strings.isNullOrEmpty(linkedMeKey)) {

        }

        // request info
        String ip = request.getRemoteAddr();
        HttpSession httpSession = request.getSession();
        httpSession.setMaxInactiveInterval(1);  //set expire time
        String sessionId = httpSession.getId().substring(8, 24);

//        LMInstallParams lmInstallParams = new LMInstallParams(linkedMeKey, sdk, debug, retryNumber, hardwareId,
//                                                                googleAdvertisingId, isHardwareIdReal, adTrackingEnabled, brand, carrier,
//                                                                iOSBundleId, isReferable, os, osVersion, appVersion,
//                                                                update,  uriScheme, iOSTeamId, universalLinkUrl, spotlightIdentifier,
//                                                                latVal, wifi, hasNfc, hasTelephone, bluetooth,
//                                                                screenDpi, screenHeight, screenWidth);
//        String result = lmSdkService.install(lmInstallParams);
        return "";
    }

    @Path("/open")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String open(@QueryParam("device_fingerprint_id") String device_fingerprint_id,
                       @QueryParam("identity_id") String identity_id,
                       @QueryParam("is_referrable") boolean is_referrable,
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

        LMOpenParams lmOpenParams = new LMOpenParams(device_fingerprint_id, identity_id, is_referrable, app_version, os_version, sdk_update,
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
                      @FormParam("identity_id") String identityId,
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
