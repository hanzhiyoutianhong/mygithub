package cc.linkedme.api.lkme.web.sdk;

import cc.linkedme.auth.SignAuthService;
import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.data.model.AppListInfo;
import cc.linkedme.data.model.params.*;
import cc.linkedme.service.sdkapi.AppListService;
import cc.linkedme.service.sdkapi.LMSdkService;
import com.google.common.base.Strings;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("sdk")
@Component
public class LMSdkResources {

    @Resource
    private LMSdkService lmSdkService;
    @Resource
    private AppListService appListService;

    @Resource
    private SignAuthService signAuthService;

    @Deprecated
    @Path("/install")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String install(@FormParam("device_id") String device_id,
                          @FormParam("device_type") int device_type,
                          @FormParam("device_brand") String device_brand,
                          @FormParam("device_model") String device_model,
                          @FormParam("has_bluetooth") boolean has_bluetooth,
                          @FormParam("has_nfc") boolean has_nfc,
                          @FormParam("has_sim") boolean has_sim,
                          @FormParam("os") String os,
                          @FormParam("os_version") String os_version,
                          @FormParam("screen_dpi") int screen_dpi,
                          @FormParam("screen_height") int screen_height,
                          @FormParam("screen_width") int screen_width,
                          @FormParam("is_wifi") boolean is_wifi,
                          @FormParam("is_referable") boolean is_referable,
                          @FormParam("is_debug") boolean is_debug,
                          @FormParam("google_advertising_id") String google_advertising_id,
                          @FormParam("lat_val") boolean lat_val,
                          @FormParam("carrier") String carrier,
                          @FormParam("app_version") String app_version,
                          @FormParam("external_intent_uri") String external_intent_uri,
                          @FormParam("extra_uri_data") String extra_uri_data,
                          @FormParam("spotlight_identifier") String spotlight_identifier,
                          @FormParam("universal_link_url") String universal_link_url,
                          @FormParam("sdk_update") int sdk_update,
                          @FormParam("sdk_version") String sdk_version,
                          @FormParam("ios_team_id") String ios_team_id,
                          @FormParam("ios_bundle_id") String ios_bundle_id,
                          @FormParam("retry_times") int retry_times,
                          @FormParam("linkedme_key") String linkedme_key,
                          @FormParam("timestamp") long timestamp,
                          @FormParam("sign") String sign,
                          @Context HttpServletRequest request) {

        InstallParams installParams = new InstallParams();
        installParams.device_id = device_id	;
        installParams.device_type = device_type;
        installParams.device_brand = device_brand;
        installParams.device_model = device_model;
        installParams.has_bluetooth = has_bluetooth;
        installParams.has_nfc = has_nfc	;
        installParams.has_sim = has_sim	;
        installParams.os = os;
        installParams.os_version = os_version;
        installParams.screen_dpi  = screen_dpi;
        installParams.screen_height  = screen_height;
        installParams.screen_width  = screen_width;
        installParams.is_wifi  = is_wifi;
        installParams.is_referable	= is_referable;
        installParams.is_debug = is_debug;
        installParams.google_advertising_id	= google_advertising_id;
        installParams.lat_val = lat_val;
        installParams.carrier = carrier;
        installParams.app_version = app_version	;
        installParams.external_intent_uri = external_intent_uri;
        installParams.extra_uri_data = extra_uri_data;
        installParams.spotlight_identifier = spotlight_identifier;
        installParams.universal_link_url = universal_link_url;
        installParams.sdk_update = sdk_update;
        installParams.sdk_version = sdk_version	;
        installParams.ios_team_id = ios_team_id	;
        installParams.ios_bundle_id = ios_bundle_id	;
        installParams.retry_times = retry_times;
        installParams.linkedme_key = linkedme_key;
        installParams.timestamp = timestamp ;
        installParams.sign= sign                  ;


        if (Strings.isNullOrEmpty(installParams.linkedme_key)) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM, installParams.linkedme_key);
        }

//        String apiName = "/i/sdk/install";
//        if (!signAuthService.doAuth(apiName, installParams.sign, installParams.device_id, String.valueOf(installParams.device_type), installParams.os,
//                installParams.os_version, String.valueOf(installParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }
        installParams.clientIP = request.getHeader("x-forwarded-for");
        String result = lmSdkService.install(installParams);

        return result;
    }

    @Path("/install_bak")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String install(InstallParams installParams, @Context HttpServletRequest request) {
        if (Strings.isNullOrEmpty(installParams.linkedme_key)) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM, installParams.linkedme_key);
        }

//        String apiName = "/i/sdk/install";
//        if (!signAuthService.doAuth(apiName, installParams.sign, installParams.device_id, String.valueOf(installParams.device_type), installParams.os,
//                installParams.os_version, String.valueOf(installParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }
        installParams.clientIP = request.getHeader("x-forwarded-for");
        String result = lmSdkService.install(installParams);

        return result;
    }

    @Path("/open")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String openForm(@FormParam("device_fingerprint_id") String device_fingerprint_id,
                           @FormParam("identity_id") long identity_id,
                           @FormParam("is_referable") boolean is_referable,
                           @FormParam("app_version") String app_version,
                           @FormParam("external_intent_uri") String external_intent_uri,
                           @FormParam("extra_uri_data") String extra_uri_data,
                           @FormParam("spotlight_identifier") String spotlight_identifier,
                           @FormParam("universal_link_url") String universal_link_url,
                           @FormParam("os_version") String os_version,
                           @FormParam("sdk_update") int sdk_update,
                           @FormParam("os") String os,
                           @FormParam("is_debug") boolean is_debug,
                           @FormParam("lat_val") String lat_val,
                           @FormParam("sdk_version") String sdk_version,
                           @FormParam("retry_times") int retry_times,
                           @FormParam("linkedme_key") String linkedme_key,
                           @FormParam("timestamp") long timestamp,
                           @FormParam("sign") String sign,
                           @Context HttpServletRequest request) {

        OpenParams openParams = new OpenParams();
        openParams.device_fingerprint_id = device_fingerprint_id;
        openParams.identity_id = identity_id;
        openParams.is_referable = is_referable;
        openParams.app_version = app_version;
        openParams.external_intent_uri = external_intent_uri;
        openParams.extra_uri_data = extra_uri_data;
        openParams.spotlight_identifier = spotlight_identifier;
        openParams.universal_link_url = universal_link_url;
        openParams.os_version = os_version;
        openParams.sdk_update = sdk_update;
        openParams.os = os;
        openParams.is_debug = is_debug;
        openParams.lat_val = lat_val;
        openParams.sdk_version = sdk_version;
        openParams.retry_times = retry_times;
        openParams.linkedme_key = linkedme_key;
        openParams.timestamp = timestamp;
        openParams.sign = sign;

        // auth
//        String apiName = "/i/sdk/open";
//        if (!signAuthService.doAuth(apiName, openParams.sign, String.valueOf(openParams.identity_id), openParams.linkedme_key, openParams.os,
//                openParams.os_version, String.valueOf(openParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }
        openParams.clientIP = request.getHeader("x-forwarded-for");
        String result = lmSdkService.open(openParams);
        return result;
    }

    @Path("/open_bak")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String open(OpenParams openParams, @Context HttpServletRequest request) {
        // auth
//        String apiName = "/i/sdk/open";
//        if (!signAuthService.doAuth(apiName, openParams.sign, String.valueOf(openParams.identity_id), openParams.linkedme_key, openParams.os,
//                openParams.os_version, String.valueOf(openParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }
        openParams.clientIP = request.getHeader("x-forwarded-for");
        String result = lmSdkService.open(openParams);
        return result;
    }

    @Path("/url")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url_form(@FormParam("app_id") int app_id,
                           @FormParam("ios_use_default") boolean ios_use_default,
                           @FormParam("ios_custom_url") String ios_custom_url,
                           @FormParam("android_use_default") boolean android_use_default,
                           @FormParam("android_custom_url") String android_custom_url,
                           @FormParam("desktop_use_default") boolean desktop_use_default,
                           @FormParam("desktop_custom_url") String desktop_custom_url,
                           @FormParam("identity_id") long identity_id,
                           @FormParam("device_fingerprint_id") String device_fingerprint_id,
                           @FormParam("session_id") String session_id,
                           @FormParam("tags") String tags,
                           @FormParam("alias") String alias,
                           @FormParam("channel") String channel,
                           @FormParam("feature") String feature,
                           @FormParam("campaign") String campaign,
                           @FormParam("stage") String stage,
                           @FormParam("params") String params,
                           @FormParam("source") String source,
                           @FormParam("sdk_version") String sdk_version,
                           @FormParam("retry_times") int retry_times,
                           @FormParam("linkedme_key") String linkedme_key,
                           @FormParam("timestamp") long timestamp,
                           @FormParam("sign") String sign,
                           @Context HttpServletRequest request) {

        UrlParams urlParams = new UrlParams();
        urlParams.app_id = app_id;
        urlParams.ios_use_default = ios_use_default;
        urlParams.ios_custom_url = ios_custom_url;
        urlParams.android_use_default = android_use_default;
        urlParams.android_custom_url = android_custom_url;
        urlParams.desktop_use_default = desktop_use_default;
        urlParams.desktop_custom_url = desktop_custom_url;
        urlParams.campaign = campaign == null ? null : campaign.split(",");

        urlParams.identity_id = identity_id;
        urlParams.device_fingerprint_id = device_fingerprint_id;
        urlParams.session_id = session_id;
        urlParams.tags = tags == null ? null : tags.split(",");
        urlParams.alias = alias;
        urlParams.channel = channel == null ? null : channel.split(",");
        urlParams.feature = feature == null ? null : feature.split(",");
        urlParams.stage = stage == null ? null : stage.split(",");
        try {
            urlParams.params = JSONObject.fromObject(params);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "params is illegal");
        }
        urlParams.source = source;
        urlParams.sdk_version = sdk_version;
        urlParams.retry_times = retry_times;
        urlParams.linkedme_key = linkedme_key;
        urlParams.timestamp = timestamp;
        urlParams.sign = sign;


        //        String apiName = "/i/sdk/url";
//        if (!signAuthService.doAuth(apiName, urlParams.sign, String.valueOf(urlParams.identity_id), urlParams.linkedme_key, String.valueOf(urlParams.session_id), String.valueOf(urlParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }

        JSONObject requestJson = JSONObject.fromObject(urlParams);

        String url = lmSdkService.url(urlParams);
        String[] urlArr = url.split("/");
        long deepLinkId = 0;
        if (urlArr.length == 5) {
            deepLinkId = Base62.decode(urlArr[4]);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("url", url);

        JSONObject log = new JSONObject();
        log.put("request", requestJson);
        log.put("response", resultJson);

        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "url",
                urlParams.identity_id, urlParams.linkedme_key, deepLinkId, urlParams.session_id, urlParams.retry_times, urlParams.is_debug,
                urlParams.sdk_version, log.toString()));

        return resultJson.toString();
    }

    @Path("/url_bak")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url(UrlParams urlParams, @Context HttpServletRequest request) {
//        String apiName = "/i/sdk/url";
//        if (!signAuthService.doAuth(apiName, urlParams.sign, String.valueOf(urlParams.identity_id), urlParams.linkedme_key, String.valueOf(urlParams.session_id), String.valueOf(urlParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }

        JSONObject requestJson = JSONObject.fromObject(urlParams);

        String url = lmSdkService.url(urlParams);
        String[] urlArr = url.split("/");
        long deepLinkId = 0;
        if (urlArr.length == 5) {
            deepLinkId = Base62.decode(urlArr[4]);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("url", url);

        JSONObject log = new JSONObject();
        log.put("request", requestJson);
        log.put("response", resultJson);

        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "url",
                urlParams.identity_id, urlParams.linkedme_key, deepLinkId, urlParams.session_id, urlParams.retry_times, urlParams.is_debug,
                urlParams.sdk_version, log.toString()));

        return resultJson.toString();
    }

    @Path("/close")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String close(@FormParam("device_fingerprint_id")   String device_fingerprint_id,
                        @FormParam("identity_id") long identity_id,
                        @FormParam("session_id")  String session_id,
                        @FormParam("sdk_version") String sdk_version,
                        @FormParam("retry_times") int retry_times,
                        @FormParam("linkedme_key")  String linkedme_key,
                        @FormParam("timestamp")     long timestamp,
                        @FormParam("sign")        String sign,
                        @Context HttpServletRequest request){

        CloseParams closeParams = new CloseParams();
        closeParams.device_fingerprint_id = device_fingerprint_id;
        closeParams.identity_id = identity_id;
        closeParams.session_id = session_id;
        closeParams.sdk_version = sdk_version;
        closeParams.retry_times = retry_times;
        closeParams.linkedme_key = linkedme_key;
        closeParams.timestamp = timestamp;
        closeParams.sign = sign;

        if (Strings.isNullOrEmpty(closeParams.linkedme_key)) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM, closeParams.linkedme_key);
        }

//        String apiName = "/i/sdk/close";
//        if (!signAuthService.doAuth(apiName, closeParams.sign, String.valueOf(closeParams.identity_id), closeParams.linkedme_key, String.valueOf(closeParams.session_id), String.valueOf(closeParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }

        // lmSdkService.close(closeParams);
        JSONObject requestJson = JSONObject.fromObject(closeParams);

        JSONObject log = new JSONObject();
        log.put("request", requestJson);
        log.put("response", "{}");
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "close",
                closeParams.identity_id, closeParams.linkedme_key, closeParams.session_id, closeParams.retry_times, closeParams.is_debug,
                closeParams.sdk_version, log.toString()));

        return "{}";

    }

    @Path("/close_bak")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String close(CloseParams closeParams, @Context HttpServletRequest request) {
        if (Strings.isNullOrEmpty(closeParams.linkedme_key)) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM, closeParams.linkedme_key);
        }

//        String apiName = "/i/sdk/close";
//        if (!signAuthService.doAuth(apiName, closeParams.sign, String.valueOf(closeParams.identity_id), closeParams.linkedme_key, String.valueOf(closeParams.session_id), String.valueOf(closeParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }

        // lmSdkService.close(closeParams);
        JSONObject requestJson = JSONObject.fromObject(closeParams);

        JSONObject log = new JSONObject();
        log.put("request", requestJson);
        log.put("response", "{}");
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "close",
                closeParams.identity_id, closeParams.linkedme_key, closeParams.session_id, closeParams.retry_times, closeParams.is_debug,
                closeParams.sdk_version, log.toString()));

        return "{}";
    }

    @Path("/preInstall")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String preInstall(PreInstallParams preInstallParams, @Context HttpServletRequest request) {
        preInstallParams.clientIP = request.getHeader("x-forwarded-for");
        String identityId = lmSdkService.preInstall(preInstallParams);
        String result = "{\"identity_id\":" + identityId + "}";
        return result;
    }

    @Path("/preOpen")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String preOpen(PreOpenParams preOpenParams, @Context HttpServletRequest request) {
        ApiLogger.info("sdk/preOpen,deepLinkId:" + Base62.decode(preOpenParams.click_id) + ",destination:" + preOpenParams.destination
                + ",lkme_tag:" + preOpenParams.lkme_tag);
        return "{}";
    }

    @Path("/applist")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String storeAppList(AppListParams appListParams, @Context HttpServletRequest request) {
//        String apiName = "/i/sdk/applist";
//        if (!signAuthService.doAuth(apiName, appListParams.sign, String.valueOf(appListParams.identity_id), appListParams.linkedme_key, appListParams.os, String.valueOf(appListParams.timestamp))) {
//            throw new LMException(LMExceptionFactor.LM_AUTH_FAILED);
//        }

        ArrayList<AppListInfo> appListInfos = new ArrayList<AppListInfo>();
        for (int i = 0; i < appListParams.apps_data.size(); i++) {
            AppListInfo appListInfo = new AppListInfo();
            JSONObject jsonObject = appListParams.apps_data.getJSONObject(i);
            appListInfo.setIdentityId(appListParams.identity_id);
            appListInfo.setDeviceFingerprintId(appListParams.device_fingerprint_id);
            appListInfo.setAppName(jsonObject.get("name").toString());
            appListInfo.setAppIdentifier(jsonObject.get("app_identifier").toString());
            appListInfo.setUriScheme(jsonObject.get("uri_scheme").toString());
            appListInfo.setPublicSourceDir(jsonObject.get("public_source_dir").toString());
            appListInfo.setSourceDir(jsonObject.get("source_dir").toString());
            appListInfo.setInstallDate(jsonObject.get("install_date").toString());
            appListInfo.setLastUpdateDate(jsonObject.get("last_update_date").toString());
            appListInfo.setVersionCode(jsonObject.get("version_code").toString());
            appListInfo.setVersionName(jsonObject.get("version_name").toString());
            appListInfo.setOs(jsonObject.get("os").toString());
            appListInfo.setSdkVersion(appListParams.sdk_version);
            appListInfo.setRetryTimes(appListParams.retry_times);
            appListInfo.setLinkedmeKey(appListParams.linkedme_key);
            appListInfo.setSign(appListParams.sign);

            appListInfos.add(appListInfo);
        }

        int result = appListService.addAppList(appListInfos);
        if (result > 0)
            return "{\"ret\":\"true\"}";
        else
            return "{\"ret\":\"error\"}";
    }

}
