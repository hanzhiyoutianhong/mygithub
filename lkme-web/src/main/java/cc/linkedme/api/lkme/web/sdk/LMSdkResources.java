package cc.linkedme.api.lkme.web.sdk;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.ClientInfo;
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

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setLinkedmeKey(linkedMEKey);
        clientInfo.setDeviceId(deviceId);
        clientInfo.setDeviceType(deviceType);
        clientInfo.setDeviceBrand(deviceBrand);
        clientInfo.setDeviceModel(deviceModel);
        clientInfo.setHasBlutooth(hasBluetooth);
        clientInfo.setHasNfc(hasNfc);
        clientInfo.setHasSim(hasSim);
        clientInfo.setOs(os);
        clientInfo.setOsVersion(osVersion);
        clientInfo.setScreenDpi(screenDpi);
        clientInfo.setScreenHeight(screenHeight);
        clientInfo.setScreenWidth(screenWidth);
        clientInfo.setIsWifi(isWifi);
        clientInfo.setIsReferable(isReferable);
        clientInfo.setLatVal(latVal);
        clientInfo.setCarrier(carrier);
        clientInfo.setAppVersion(appVersion);
        clientInfo.setSdkUpdate(sdkUpdate);
        clientInfo.setIosTeamId(iOSTeamId);
        clientInfo.setIosBundleId(iOSBundleId);

        int result = lmSdkService.install(clientInfo);
        return String.valueOf(result);
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
                       @FormParam("debug") String debug) {

//        LMOpenParams lmOpenParams = new LMOpenParams(linkedMeKey, sdk, retryNumber, debug, identifyId,
//                                                        deviceFingerprintId, adTrackingEnabled, linkIdentifier, isReferable, os,
//                                                        osVersion, appVersion, update, uriScheme, iOSBundleId,
//                                                        iOSTeamId, spotlightIdentifier, universalLinkUrl, latVal);
//
//        String result = lmSdkService.open(lmOpenParams);

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
                      @FormParam("identity_id") long identityId,
                      @FormParam("device_fingerprint_id") String deviceFingerPrintId,
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
                      @FormParam("debug") int debug) {

        LMUrlParams lmUrlParams = new LMUrlParams(linkedmeKey, identityId, deviceFingerPrintId, sdkVersion, retryTimes, debug, tags, alias,
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
