package cc.linkedme.api.lkme.web.sdk;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.data.model.params.CloseParams;
import cc.linkedme.data.model.params.InstallParams;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;

import cc.linkedme.data.model.params.OpenParams;
import cc.linkedme.data.model.params.PreInstallParams;
import cc.linkedme.data.model.params.PreOpenParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.service.sdkapi.LMSdkService;
import com.google.common.base.Strings;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Path("sdk")
@Component
public class LMSdkResources {

    @Resource
    private LMSdkService lmSdkService;

    @Path("/install")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String install(InstallParams installParams, @Context HttpServletRequest request) {
        if (Strings.isNullOrEmpty(installParams.linkedme_key)) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM, installParams.linkedme_key);
        }

        String result = lmSdkService.install(installParams);

        JSONObject log = new JSONObject();
        JSONObject requestJson = JSONObject.fromObject(installParams);
        JSONObject responseJson = JSONObject.fromObject(result);
        log.put("request", requestJson);
        log.put("response", responseJson);
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "install", responseJson.get("identity_id"),
                installParams.linkedme_key, responseJson.get("deeplink_id"), responseJson.get("session_id"), installParams.retry_times,
                installParams.is_debug, installParams.sdk_version, log.toString()));

        return result;
    }

    @Path("/open")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String open(OpenParams openParams, @Context HttpServletRequest request) {
        // auth

        String response = lmSdkService.open(openParams);
        JSONObject responseJson = JSONObject.fromObject(response);
        long deepLinkId = responseJson.getLong("deeplink_id");
        long sessionId = responseJson.getLong("session_id");
        JSONObject log = new JSONObject();
        JSONObject requestJson = JSONObject.fromObject(openParams);
        log.put("request", requestJson);
        log.put("response", responseJson);
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "open", openParams.identity_id, openParams.linkedme_key,
                deepLinkId, sessionId, openParams.retry_times, openParams.is_debug, openParams.sdk_version, log.toString()));

        return response;
    }

    @Path("/url")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url(UrlParams urlParams, @Context HttpServletRequest request) {

        String url = lmSdkService.url(urlParams);
        String[] urlArr = url.split("/");
        long deepLinkId = 0;
        if (urlArr.length == 5) {
            deepLinkId = Base62.decode(urlArr[4]);
        }
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("url", url);

        JSONObject log = new JSONObject();
        JSONObject requestJson = JSONObject.fromObject(urlParams);
        log.put("request", requestJson);
        log.put("response", resultJson);

        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "url", urlParams.identity_id, urlParams.linkedme_key, deepLinkId,
                urlParams.session_id, urlParams.retry_times, urlParams.is_debug, urlParams.sdk_version, log.toString()));

        return resultJson.flip().toString();
    }

    @Path("/close")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String close(CloseParams closeParams, @Context HttpServletRequest request) {

        if (Strings.isNullOrEmpty(closeParams.linkedme_key)) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM, closeParams.linkedme_key);
        }

        lmSdkService.close(closeParams);

        JSONObject log = new JSONObject();
        JSONObject requestJson = JSONObject.fromObject(closeParams);
        log.put("request", requestJson);
        log.put("response", "{}");
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "close", closeParams.identity_id, closeParams.linkedme_key,
                closeParams.session_id, closeParams.retry_times, closeParams.is_debug, closeParams.sdk_version, log.toString()));

        return "{}";
    }

    @Path("/preInstall")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String preInstall(PreInstallParams preInstallParams, @Context HttpServletRequest request) {

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

    @Deprecated
    @Path("/install_bak")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String install_bak(@FormParam("linkedme_key") String linkedMEKey, @FormParam("device_id") String deviceId,
            @FormParam("device_type") Byte deviceType, @FormParam("device_brand") String deviceBrand,
            @FormParam("device_model") String deviceModel, @FormParam("has_bluetooth") boolean hasBluetooth,
            @FormParam("has_nfc") boolean hasNfc, @FormParam("has_sim") boolean hasSim, @FormParam("os") String os,
            @FormParam("os_version") String osVersion, @FormParam("screen_dpi") int screenDpi, @FormParam("screen_height") int screenHeight,
            @FormParam("screen_width") int screenWidth, @FormParam("is_wifi") boolean isWifi,
            @FormParam("is_referable") boolean isReferable, @FormParam("lat_val") String latVal, @FormParam("carrier") String carrier,
            @FormParam("app_version") String appVersion, @FormParam("sdk_update") String sdkUpdate,
            @FormParam("sdk_version") String sdkVersion, @FormParam("iOS_team_id") String iOSTeamId,
            @FormParam("iOS_bundle_id") String iOSBundleId, @FormParam("is_debug") boolean isDebug,
            @FormParam("retry_times") int retryTimes, @Context HttpServletRequest request) {
        return null;
    }

    @Deprecated
    @Path("/install_inputstream")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String install_inputstream(@Context HttpServletRequest request) {

        StringBuffer httpBody = new StringBuffer();
        try {
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while (line != null) {
                httpBody.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            inputStream.close();
            System.out.println(httpBody.toString());

        } catch (IOException e) {
            ApiLogger.error(e);
        }
        JSONObject json = JSONObject.fromObject(httpBody.toString());
        String linkedMEKey = json.getString("linkedme_key");
        String deviceId = json.getString("device_id");
        int deviceType = json.getInt("device_type");
        String deviceBrand = json.getString("device_brand");
        String deviceModel = json.getString("device_model");
        boolean hasBluetooth = json.getBoolean("has_bluetooth");
        boolean hasNfc = json.getBoolean("has_nfc");
        boolean hasSim = json.getBoolean("has_sim");
        String os = json.getString("os");
        String osVersion = json.getString("os_version");
        int screenDpi = json.getInt("screen_dpi");
        int screenHeight = json.getInt("screen_height");
        int screenWidth = json.getInt("screen_width");
        boolean isWifi = json.getBoolean("is_wifi");
        boolean isReferable = json.getBoolean("is_referable");
        String latVal = json.getString("lat_val");
        String carrier = json.getString("carrier");
        String appVersion = json.getString("app_version");
        String sdkUpdate = json.getString("sdk_update");
        String iOSTeamId = json.getString("iOS_team_id");
        String iOSBundleId = json.getString("iOS_bundle_id");
        String sdkVersion = json.getString("sdk_version");
        int retryTimes = json.getInt("retry_times");
        boolean isDebug = json.getBoolean("is_debug");

        if (Strings.isNullOrEmpty(linkedMEKey)) {
            throw new LMException(LMExceptionFactor.LM_MISSING_PARAM);
        }

        LMInstallParams lmInstallParams = new LMInstallParams(linkedMEKey, 0L, null, sdkVersion, retryTimes, isDebug, deviceId, deviceType,
                deviceBrand, deviceModel, hasBluetooth, hasNfc, hasSim, os, osVersion, screenDpi, screenHeight, screenWidth, isWifi,
                isReferable, latVal, carrier, appVersion, sdkUpdate, iOSTeamId, iOSBundleId);
        String result = ""; // lmSdkService.install(lmInstallParams);
        return result;
    }

    @Deprecated
    @Path("/url_form")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String url_form(@FormParam("linkedme_key") String linkedmeKey, @FormParam("identity_id") long identityId,
            @FormParam("device_fingerprint_id") String deviceFingerprintId, @FormParam("tags") String tags,
            @FormParam("alias") String alias, @FormParam("channel") String channel, @FormParam("feature") String feature,
            @FormParam("stage") String stage, @FormParam("campaign") String campaign, @FormParam("params") String params,
            @FormParam("source") String source, @FormParam("sdk_version") String sdkVersion, @FormParam("session_id") String sessionId,
            @FormParam("retry_times") int retryTimes, @FormParam("debug") boolean debug) {

        LMUrlParams lmUrlParams = new LMUrlParams(linkedmeKey, identityId, deviceFingerprintId, sdkVersion, retryTimes, debug, tags, alias,
                channel, feature, stage, campaign, params, source, sessionId);

        String url = ""; // lmSdkService.url(lmUrlParams);
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("url", url);
        return resultJson.flip().toString();

    }

    @Deprecated
    @Path("/open_form")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String openForm(@QueryParam("device_fingerprint_id") String device_fingerprint_id, @QueryParam("identity_id") long identity_id,
            @QueryParam("is_referable") boolean is_referable, @QueryParam("app_version") String app_version,
            @QueryParam("extra_uri_data") String extra_uri_data, @QueryParam("os_version") String os_version,
            @QueryParam("sdk_update") int sdk_update, @QueryParam("os") String os, @QueryParam("is_debug") boolean is_debug,
            @QueryParam("lat_val") String lat_val, @QueryParam("sdk_version") String sdk_version,
            @QueryParam("last_source") String last_source, @QueryParam("retry_times") int retry_times,
            @QueryParam("linkedme_key") String linkedme_key, @QueryParam("sign") String sign) {

        LMOpenParams lmOpenParams = new LMOpenParams(device_fingerprint_id, identity_id, is_referable, app_version, extra_uri_data,
                os_version, sdk_update, os, is_debug, lat_val, sdk_version, last_source, retry_times, linkedme_key);

        String deepLinkParam = "";
        boolean clicked_linkedme_link = false;
        if (!Strings.isNullOrEmpty(extra_uri_data)) {
            if (extra_uri_data.startsWith(Constants.DEEPLINK_HTTPS_PREFIX) || extra_uri_data.startsWith(Constants.DEEPLINK_HTTP_PREFIX)) {
                clicked_linkedme_link = true;
                deepLinkParam = ""; // lmSdkService.open(lmOpenParams);
            }
        }
        if (Strings.isNullOrEmpty(deepLinkParam)) {
            deepLinkParam = "";
        }
        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("session_id", System.currentTimeMillis());
        resultJson.append("identity_id", identity_id);
        resultJson.append("device_fingerprint_id", device_fingerprint_id);
        resultJson.append("browser_fingerprint_id", "");
        resultJson.append("link", extra_uri_data);
        resultJson.append("params", deepLinkParam);
        resultJson.append("is_first_session", true);
        resultJson.append("clicked_linkedme_link", clicked_linkedme_link);
        return resultJson.flip().toString();
    }

}
