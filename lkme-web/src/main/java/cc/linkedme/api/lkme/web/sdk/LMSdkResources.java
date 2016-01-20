package cc.linkedme.api.lkme.web.sdk;

import cc.linkedme.common.params.LMCloseParams;
import cc.linkedme.common.params.LMInstallParams;
import cc.linkedme.common.params.LMOpenParams;
import cc.linkedme.common.params.LMUrlParams;
import cc.linkedme.service.LMSdkService;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("v1")
@Component
public class LMSdkResources {

    private LMSdkService lmSdkService;

    public LMSdkService getLmSdkService() {
        return lmSdkService;
    }

    public void setLmSdkService(LMSdkService lmSdkService) {
        this.lmSdkService = lmSdkService;
    }

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

        LMInstallParams lmInstallParams = new LMInstallParams(linkedMeKey, debug, retryNumber, hardwareId, googleAdvertisingId,
                                                                isHardwareIdReal, adTrackingEnabled, brand, carrier, iOSBundleId,
                                                                isReferable, os, osVersion, appVersion, sdk,
                                                                update, uriScheme, iOSTeamId, universalLinkUrl, spotlightIdentifier,
                                                                latVal, wifi, hasNfc, hasTelephone, bluetooth,
                                                                screenDpi, screenHeight, screenWidth);
        String result = lmSdkService.install(lmInstallParams);
        return result;
    }

    @Path("/open")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String open(@FormParam("Linkedme_key") String linkedMeKey,
                       @FormParam("identity_id") String identifyId,
                       @FormParam("device_fingerprint_id") String deviceFingerprintId,
                       @FormParam("ad_tracking_enabled") String adTrackingEnabled,
                       @FormParam("link_identifier") String linkIdentifier,
                       @FormParam("is_referable") String isReferable,
                       @FormParam("os") String os,
                       @FormParam("os_version") String osVersion,
                       @FormParam("app_version") String appVersion,
                       @FormParam("sdk") String sdk,
                       @FormParam("update") String update,
                       @FormParam("uri_scheme") String uriScheme,
                       @FormParam("ios_bundle_id") String iOSBundleId,
                       @FormParam("ios_team_id") String iOSTeamId,
                       @FormParam("spotlight_identifier") String spotlightIdentifier,
                       @FormParam("universal_link_url") String universalLinkUrl,
                       @FormParam("lat_val") String latVal,
                       @FormParam("retry_number") String retryNumber,
                       @FormParam("debug") String debug) {;

        LMOpenParams lmOpenParams = new LMOpenParams(linkedMeKey, retryNumber, debug, identifyId, deviceFingerprintId,
                                                        adTrackingEnabled, linkIdentifier, isReferable, os, osVersion,
                                                        appVersion, sdk, update, uriScheme, iOSBundleId,
                                                        iOSTeamId, spotlightIdentifier, universalLinkUrl, latVal);

        String result = lmSdkService.open(lmOpenParams);

        return result;

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

        LMCloseParams lmCloseParams = new LMCloseParams(linkedMeKey, retryNumber, null, identifyId, deviceFingerprintId, sdk, sessionId);

        String result = lmSdkService.close(lmCloseParams);
        return result;
    }

    @Path("/url")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url(@FormParam("Linkedme_key") String linkedMeKey,
                      @FormParam("identity_id") String identifyId,
                      @QueryParam("device_fingerprint_id") String deviceFingerprintId,
                      @FormParam("type") String type,
                      @FormParam("tags") String tags,
                      @FormParam("channel") String channel,
                      @FormParam("feature") String feature,
                      @FormParam("stage") String stage,
                      @FormParam("alias") String alias,
                      @FormParam("sdk") String sdk,
                      @FormParam("data") String data,
                      @FormParam("update") String update,
                      @FormParam("source") String source,
                      @FormParam("deeplink_path") String deepLinkPath,
                      @FormParam("duration") String duration,
                      @FormParam("session_id") String sessionId,
                      @FormParam("retry_number") String retryNumber,
                      @FormParam("debug") String debug) {

        LMUrlParams lmUrlParams = new LMUrlParams(linkedMeKey, retryNumber, debug, identifyId, deviceFingerprintId, type, tags, channel, feature, stage, alias, sdk, data, update, source, deepLinkPath, duration, sessionId);

        String result = lmSdkService.url(lmUrlParams);
        return result;

    }

    @Path("/preInstall")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url(@FormParam("link_click_id") String linkClickId) {

        String result = lmSdkService.preInstall(linkClickId);

        return result;
    }

}
