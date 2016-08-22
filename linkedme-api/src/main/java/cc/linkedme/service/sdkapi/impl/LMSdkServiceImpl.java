package cc.linkedme.service.sdkapi.impl;

import javax.annotation.Resource;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.service.webapi.impl.DeviceServiceImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.ArrayUtil;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.commons.util.DeepLinkUtil;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.util.Util;
import cc.linkedme.commons.util.UuidHelper;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.FingerPrintInfo;
import cc.linkedme.data.model.params.CloseParams;
import cc.linkedme.data.model.params.InstallParams;
import cc.linkedme.data.model.params.OpenParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.data.model.params.WebCloseParams;
import cc.linkedme.data.model.params.WebInitParams;
import cc.linkedme.mcq.ClientMsgPusher;
import cc.linkedme.mcq.DeepLinkMsgPusher;
import cc.linkedme.mcq.FingerPrintMsgPusher;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.sdkapi.LMSdkService;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public class LMSdkServiceImpl implements LMSdkService {
    @Resource
    private UuidCreator uuidCreator;

    @Resource
    private DeepLinkService deepLinkService;

    @Resource
    private DeepLinkMsgPusher deepLinkMsgPusher;

    @Resource
    private ClientMsgPusher clientMsgPusher;

    @Resource
    private FingerPrintMsgPusher fingerPrintMsgPusher;

    @Resource
    private ShardingSupportHash<JedisPort> deepLinkShardingSupport;

    @Resource
    private ShardingSupportHash<JedisPort> clientShardingSupport;

    @Resource
    private ShardingSupportHash<JedisPort> linkedmeKeyShardingSupport;

    @Resource
    public ClientDao clientDao;

    @Resource
    private ShardingSupportHash<JedisPort> deepLinkCountShardingSupport;

    @Resource
    private MemCacheTemplate<String> browserFingerprintIdForYYBMemCache;

    private static ThreadPoolExecutor deepLinkCountThreadPool = new ThreadPoolExecutor(20, 20, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(300), new ThreadPoolExecutor.DiscardOldestPolicy());

    public final static float UNIVERSE_LINK_IOS_VERSION = 8;

    public final static int ANDROID_APP_LINKS_VERSION = 23;


    public String webinit(WebInitParams webInitParams) {

        String identityId = webInitParams.getIdentityId();
        if (Strings.isNullOrEmpty(identityId) || !StringUtils.isNumeric(identityId) || !UuidHelper.isValidId(Long.parseLong(identityId))) {
            identityId = String.valueOf(uuidCreator.nextId(1));
            webInitParams.setIdentityId(identityId);
        }


        long sessionId = System.currentTimeMillis();
        JSONObject resultJson = new JSONObject();
        resultJson.put("identity_id", identityId);
        resultJson.put("session_id", sessionId);

        JedisPort linkedmeKeyClient = linkedmeKeyShardingSupport.getClient(webInitParams.getLinkedmeKey());
        String appId = linkedmeKeyClient.hget(webInitParams.getLinkedmeKey(), "appid");

        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", webInitParams.getClientIP(), "webinit", appId, webInitParams.getLinkedmeKey(),
                webInitParams.getIdentityId(),webInitParams.getType()));

        return resultJson.toString();

    }

    public void webClose(WebCloseParams webCloseParams) {

        JedisPort linkedmeKeyClient = linkedmeKeyShardingSupport.getClient(webCloseParams.getLinkedmeKey());
        String appId = linkedmeKeyClient.hget(webCloseParams.getLinkedmeKey(), "appid");

        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", webCloseParams.getClientIP(), "webclose", appId,
                webCloseParams.getLinkedmeKey(), webCloseParams.getIdentityId(), webCloseParams.getSessionId(),
                webCloseParams.getTimestamp(),webCloseParams.getType()));

    }

    private String createDeviceInfo(ClientInfo clientInfo) {
        String res = "";
        res += clientInfo.getDeviceBrand() + "_" + clientInfo.getDeviceModel() + "_" + clientInfo.getosVersionDetail() + "_"
                + clientInfo.getScreenDpi() + "_" + clientInfo.getScreenHeight() + "_" + clientInfo.getScreenWidth();
        return res;
    }

    private boolean isValidAndroidId(String androidId) {
        if (StringUtils.isBlank(androidId)) {
            return false;
        }
        String regex = "[0-9a-f]{15,16}";

        return Pattern.matches(regex, androidId) && !androidId.equals("000000000000000") && !androidId.equals("0000000000000000")
                && !androidId.equals("9774d56d682e549c");
    }

    private boolean isValidAndroidSerialNumber(String androidSerialNumber) {
        if (StringUtils.isBlank(androidSerialNumber)) {
            return false;
        }
        return !StringUtils.isBlank(androidSerialNumber) && androidSerialNumber != "unknown";
    }

    private boolean isValidImei(String imei) {
        if (StringUtils.isBlank(imei)) {
            return false;
        }
        String regex = "\\d{15}|\\d{17}";
        return Pattern.matches(regex, imei) && !imei.equals("0000000000000000") && !imei.equals("000000000000000000");
    }

    public String getDeviceId(ClientInfo clientInfo) {
        String deviceId;

        if (clientInfo.getosVersionDetail() < ANDROID_APP_LINKS_VERSION && isValidImei(clientInfo.getiMei())) {
            if (isValidAndroidId(clientInfo.getAndroidId())) {
                deviceId = MD5Utils.md5(clientInfo.getiMei() + "IA" + clientInfo.getAndroidId());
            } else if (isValidAndroidSerialNumber(clientInfo.getSerialNumber())) {
                deviceId = MD5Utils.md5(clientInfo.getiMei() + "IS" + clientInfo.getSerialNumber());
            } else {
                deviceId = MD5Utils.md5(clientInfo.getiMei() + createDeviceInfo(clientInfo));
            }
        } else {
            if (isValidAndroidId(clientInfo.getAndroidId()) && isValidAndroidSerialNumber(clientInfo.getSerialNumber())) {
                deviceId = MD5Utils.md5(clientInfo.getAndroidId() + "AS" + clientInfo.getSerialNumber());
            } else if (isValidAndroidId(clientInfo.getAndroidId())) {
                deviceId = MD5Utils.md5(clientInfo.getAndroidId() + "AI" + createDeviceInfo(clientInfo));
            } else if (isValidAndroidSerialNumber(clientInfo.getSerialNumber())) {
                deviceId = MD5Utils.md5(clientInfo.getSerialNumber() + "SI" + createDeviceInfo(clientInfo));
            } else {
                deviceId = MD5Utils.md5(createDeviceInfo(clientInfo));
            }
        }
        clientInfo.setDeviceId(deviceId);

        return deviceId;
    }

    public void addClientInfo(ClientInfo clientInfo) {
        clientMsgPusher.addClient(clientInfo);
    }

    public String install(InstallParams installParams) {
        JSONObject requestJson = JSONObject.fromObject(installParams);

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setDeviceId(installParams.device_id);
        clientInfo.setiMei(installParams.device_imei);
        clientInfo.setAndroidId(installParams.android_id);
        clientInfo.setSerialNumber(installParams.serial_number);
        clientInfo.setDeviceMac(installParams.device_mac);
        clientInfo.setDeviceFingerPrint(installParams.finger_print);
        clientInfo.setLinkedmeKey(installParams.linkedme_key);
        clientInfo.setDeviceType(installParams.device_type);
        clientInfo.setDeviceModel(installParams.device_model);
        clientInfo.setDeviceBrand(installParams.device_brand);
        clientInfo.setHasBlutooth(installParams.has_bluetooth);
        clientInfo.setHasNfc(installParams.has_nfc);
        clientInfo.setHasSim(installParams.has_sim);
        clientInfo.setOs(installParams.os);
        clientInfo.setosVersionDetail(installParams.os_version_detail);
        clientInfo.setOsVersion(installParams.os_version);
        clientInfo.setScreenDpi(installParams.screen_dpi);
        clientInfo.setScreenHeight(installParams.screen_height);
        clientInfo.setScreenWidth(installParams.screen_width);
        clientInfo.setIsWifi(installParams.is_wifi);
        clientInfo.setIsReferable(installParams.is_referable);
        clientInfo.setLatVal(installParams.lat_val);
        clientInfo.setCarrier(installParams.carrier);
        clientInfo.setAppVersion(installParams.app_version);
        clientInfo.setSdkUpdate(installParams.sdk_update);
        clientInfo.setSdkVersion(installParams.sdk_version);
        clientInfo.setIosTeamId(installParams.ios_team_id);
        clientInfo.setIosBundleId(installParams.ios_bundle_id);

        // 根据linkedme_key获取appid
        JedisPort linkedmeKeyClient = linkedmeKeyShardingSupport.getClient(installParams.linkedme_key);
        String appIdStr = linkedmeKeyClient.hget(installParams.linkedme_key, "appid");
        long appId = 0;
        if (appIdStr != null) {
            appId = Long.parseLong(appIdStr);
        }
        String scanPrefix = "";

        String deviceId = installParams.device_id;
        if ("android".equals(installParams.os.trim().toLowerCase())) {
            deviceId = getDeviceId(clientInfo);
        }

        JedisPort clientRedisClient = clientShardingSupport.getClient(deviceId);
        String identityIdStr = clientRedisClient.get(deviceId);
        long identityId = 0;
        long newIdentityId = 0;
        long deepLinkId = 0;
        FingerPrintInfo.OperationType operationType = FingerPrintInfo.OperationType.NONE;
        DeepLink deepLink = null;
        String browserFingerprintId = "b";
        String installType = "other";
        String deviceModel = installParams.device_model;
        if ("ios".equals(installParams.os.trim().toLowerCase())) {
            deviceModel = "iphone";
        }
        String clientIp = getClientIp(installParams.clientIP);

        String deviceFingerprintId = createFingerprintId(String.valueOf(appId), installParams.os, installParams.os_version,
                deviceModel.trim().toLowerCase(), clientIp);

        if (Strings.isNullOrEmpty(identityIdStr)) { // 之前不存在<device, identityId>
            operationType = FingerPrintInfo.OperationType.ADD;
            // device_fingerprint_id 与 browse_fingerprint_id匹配逻辑
            JedisPort dfpIdRedisClient = clientShardingSupport.getClient(deviceFingerprintId);
            identityIdStr = dfpIdRedisClient.hget(deviceFingerprintId, "iid");
            String deepLinkIdStr = dfpIdRedisClient.hget(deviceFingerprintId, "did");

            if (identityIdStr != null && deepLinkIdStr != null) { // 匹配成功
                identityId = Long.parseLong(identityIdStr);
                deepLinkId = Long.parseLong(deepLinkIdStr);
                deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
                // 匹配成功,删除deferred deep linking记录
                dfpIdRedisClient.hdelAll(deviceFingerprintId);
            } else {
                // 匹配不成功, 生成identity_id
                identityId = uuidCreator.nextId(1); // 1表示发号器的identity_id业务
            }
            if ((("ios".equals(installParams.os.trim().toLowerCase())) && (installParams.device_type == 24))
                    || (("android".equals(installParams.os.trim().toLowerCase())) && (installParams.device_type == 12))) {
                clientRedisClient.set(deviceId, identityId);// 记录<device_id, identity_id>
                JedisPort identityRedisClient = clientShardingSupport.getClient(identityId);
                identityRedisClient.set(identityId + ".di", deviceId); // 记录<identity_id, device_id>
            }
        } else { // 之前存在<device, identityId>
            identityId = Long.parseLong(identityIdStr);
            JedisPort identityRedisClient = clientShardingSupport.getClient(identityId);
            String deepLinkIdStr = identityRedisClient.get(identityIdStr + ".dpi");
            if (Strings.isNullOrEmpty(deepLinkIdStr)) { // 之前存在identityId,但是没有identityId与deepLinkId的键值对
                // device_fingerprint_id 与 browse_fingerprint_id匹配逻辑
                // 如果匹配上了,更新<device_id, identity_id>记录，并把<device_id, identity_id>放在历史库;
                JedisPort dfpIdRedisClient = clientShardingSupport.getClient(deviceFingerprintId);
                identityIdStr = dfpIdRedisClient.hget(deviceFingerprintId, "iid");
                deepLinkIdStr = dfpIdRedisClient.hget(deviceFingerprintId, "did");

                if (identityIdStr != null && deepLinkIdStr != null) { // 匹配成功
                    operationType = FingerPrintInfo.OperationType.UPDATE;
                    newIdentityId = Long.parseLong(identityIdStr);
                    deepLinkId = Long.parseLong(deepLinkIdStr);
                    deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);

                    // 匹配成功,删除deferred deep linking记录
                    dfpIdRedisClient.hdelAll(deviceFingerprintId);

                    if ((("ios".equals(installParams.os.trim().toLowerCase())) && (installParams.device_type == 24))
                            || (("android".equals(installParams.os.trim().toLowerCase())) && (installParams.device_type == 12))) {
                        clientRedisClient.sadd(deviceId + ".old", String.valueOf(identityId));
                        clientRedisClient.set(deviceId, newIdentityId); // 更新<device_id,identity_id>
                        clientRedisClient.set(identityId + ".di", deviceId);
                    }
                }
            } else { // 之前存在identityId, 并有identityId与deepLink的键值对
                operationType = FingerPrintInfo.OperationType.NONE;
                deepLinkId = Long.parseLong(deepLinkIdStr);
                deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);

                if (identityRedisClient.exists(identityIdStr + ".scan")) {
                    scanPrefix = "pc_";
                }

                // 清理redis中对应的identityId.dpi和identityId.scan
                identityRedisClient.del(identityIdStr + ".dpi", identityIdStr + ".scan");
            }
        }

        if (deepLink == null) {
            String deepLinkIdStr = browserFingerprintIdForYYBMemCache.get(deviceFingerprintId + ".yyb");
            if (!Strings.isNullOrEmpty(deepLinkIdStr)) {
                browserFingerprintIdForYYBMemCache.delete(deviceFingerprintId + ".yyb"); // 匹配成功后删除记录
                deepLinkId = Long.parseLong(deepLinkIdStr);
                deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
            }
        }

        String params = getParamsFromDeepLink(deepLink);
        long fromDeepLinkId = deepLinkId; // 用于统计一个deeplink带来的下载量
        if (Strings.isNullOrEmpty(params)) {
            fromDeepLinkId = 0;
            params = "";
        } else {
            browserFingerprintId = deviceFingerprintId;

            JedisPort dfpIdRedisClient = clientShardingSupport.getClient(deviceFingerprintId);
            if (StringUtils.isBlank(scanPrefix) && dfpIdRedisClient.hexists(deviceFingerprintId, "scan")) {
                scanPrefix = "pc_";
            }
            installType = scanPrefix + DeepLinkCount.getCountTypeFromOs(installParams.os, "install");
            final String type = scanPrefix + DeepLinkCount.getCountTypeFromOs(clientInfo.getOs(), "install");

            String date = Util.getCurrDate();
            deepLinkMsgPusher.addDeepLinkCount(deepLinkId, (int) appId, date, type);

            long countDeepLinkId = deepLinkId;
            deepLinkCountThreadPool.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        // TODO 对deeplink_id的有效性做判断
                        JedisPort countClient = deepLinkCountShardingSupport.getClient(countDeepLinkId);
                        countClient.hincrBy(String.valueOf(countDeepLinkId), type, 1);
                    } catch (Exception e) {
                        ApiLogger.warn("LMSdkServiceImpl.install deepLinkCountThreadPool count failed", e);
                    }
                    return null;
                }
            });
        }
        // 写mcq
        clientInfo.setIdentityId(identityId);
        clientMsgPusher.addClient(clientInfo);

        // 写mcq,存储键值对
        FingerPrintInfo fingerPrintInfo = toFingerPrintInfo(identityId, newIdentityId, deviceId, installParams.device_type, operationType);
        fingerPrintMsgPusher.updateFingerPrint(fingerPrintInfo);


        String sessionId = String.valueOf(System.currentTimeMillis());
        JSONObject resultJson = new JSONObject();
        resultJson.put("device_id", deviceId);
        resultJson.put("session_id", sessionId);
        resultJson.put("identity_id", String.valueOf(identityId));
        resultJson.put("device_fingerprint_id", deviceFingerprintId);
        resultJson.put("browser_fingerprint_id", browserFingerprintId);
        resultJson.put("link", "");
        resultJson.put("deeplink_id", fromDeepLinkId);
        resultJson.put("params", params);
        resultJson.put("is_first_session", true);
        resultJson.put("clicked_linkedme_link", !Strings.isNullOrEmpty(params));

        JSONObject log = new JSONObject();
        log.put("request", requestJson);
        log.put("response", resultJson);
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", installParams.clientIP, "install", installType, appId,
                deepLinkId, identityId, installParams.linkedme_key, sessionId, installParams.retry_times, installParams.is_debug,
                installParams.sdk_version, log.toString()));

        return resultJson.toString();
    }

    private FingerPrintInfo toFingerPrintInfo(long identityId, long newIdentityId, String deviceId, int deviceType,
            FingerPrintInfo.OperationType operationType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();

        FingerPrintInfo fingerPrintInfo = new FingerPrintInfo();
        fingerPrintInfo.setCurrentTime(sdf.format(currentTime));
        fingerPrintInfo.setIdentityId(identityId);
        fingerPrintInfo.setNewIdentityId(newIdentityId);
        fingerPrintInfo.setDeviceId(deviceId);
        fingerPrintInfo.setDeviceType(deviceType);
        fingerPrintInfo.setOperationType(operationType);
        return fingerPrintInfo;
    }

    private static String createFingerprintId(String appId, String os, String os_version, String deviceModel, String clientIP) {
        Joiner joiner = Joiner.on("&").skipNulls();
        // TODO 等Android sdk新版上线后,添加deviceModel字段
        String deviceParamsStr = joiner.join(appId, os, os_version, null, clientIP);
        return MD5Utils.md5(deviceParamsStr);
    }

    private static String getClientIp(String ip) {
        if (Strings.isNullOrEmpty(ip)) {
            return ip;
        }
        String[] ipArr = ip.split(",");
        return ipArr[ipArr.length - 1];
    }

    private static String getClickIdFromUri(String deepLinkUrl) {
        String clickId = "";
        if (Strings.isNullOrEmpty(deepLinkUrl)) {
            return "0";
        }
        if (deepLinkUrl.startsWith("http") || deepLinkUrl.startsWith("https")) {
            clickId = DeepLinkUtil.getDeepLinkFromUrl(deepLinkUrl);
        } else {
            try {
                URI uri = new URI(deepLinkUrl);
                String paramStr = uri.getQuery();
                String[] params = paramStr.split("&");
                for (String param : params) {
                    if (param.split("=")[0].equals("click_id")) {
                        clickId = param.split("=")[1];
                        break;
                    }
                }
            } catch (Exception e) {
                ApiLogger.warn("LMSdkServiceImpl.getClickIdFromUri failed, deepLinkUrl = " + deepLinkUrl);
                return "0";
            }
        }
        return clickId;
    }

    public String open(OpenParams openParams) {
        JSONObject requestJson = JSONObject.fromObject(openParams);

        String deepLinkUrl = "";
        boolean isDirectForward = false;
        boolean isScan = false;
        if ("Android".equals(openParams.os)) {
            deepLinkUrl = openParams.external_intent_uri;
            if ((!Strings.isNullOrEmpty(deepLinkUrl)) && deepLinkUrl.startsWith("http")) {
                isDirectForward = true;
            }
        } else if ("iOS".equals(openParams.os)) {
            String[] osVersionArr = openParams.os_version.split("\\.");
            String osMajorVersion = osVersionArr[0];
            if (Integer.parseInt(osMajorVersion) >= UNIVERSE_LINK_IOS_VERSION) {
                deepLinkUrl = openParams.universal_link_url;
                if ((!Strings.isNullOrEmpty(deepLinkUrl)) && deepLinkUrl.startsWith("http") && (!deepLinkUrl.contains("visit_id"))) {
                    isDirectForward = true;
                }
            }

            if (Strings.isNullOrEmpty(deepLinkUrl)) {
                deepLinkUrl = openParams.extra_uri_data;
            }
        }

        long deepLinkId = 0;
        String params = "";
        boolean clicked_linkedme_link = false;
        String openTypeForLog = "other";
        long appId = 0;
        if (!Strings.isNullOrEmpty(deepLinkUrl) || !Strings.isNullOrEmpty(openParams.spotlight_identifier)
                || "Android".equals(openParams.os)) {
            // 根据linkedme_key获取appid
            JedisPort linkedmeKeyClient = linkedmeKeyShardingSupport.getClient(openParams.linkedme_key);
            String appIdStr = linkedmeKeyClient.hget(openParams.linkedme_key, "appid");
            if (appIdStr != null) {
                appId = Long.parseLong(appIdStr);
            }

            String clickId = getClickIdFromUri(deepLinkUrl);
            if (!Strings.isNullOrEmpty(openParams.spotlight_identifier)) {
                clickId = DeepLinkUtil.getDeepLinkFromUrl(openParams.spotlight_identifier);
            }
            deepLinkId = Base62.decode(clickId);
            DeepLink deepLink = null;
            if (deepLinkId > 0 && appId > 0) {
                clicked_linkedme_link = true;
                deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
            }

            // yyb + deferred deep linking
            if (deepLink == null && "Android".equals(openParams.os) && appId > 0) {
                String deviceModel = null;
                if (!Strings.isNullOrEmpty(openParams.device_model)) {
                    deviceModel = openParams.device_model.trim().toLowerCase();
                }
                String clientIp = getClientIp(openParams.clientIP);
                String deviceFingerprintId =
                        createFingerprintId(String.valueOf(appId), openParams.os, openParams.os_version, deviceModel, clientIp);
                String deepLinkIdStr = browserFingerprintIdForYYBMemCache.get(deviceFingerprintId + ".yyb");
                if (!Strings.isNullOrEmpty(deepLinkIdStr)) {
                    deepLinkId = Long.parseLong(deepLinkIdStr);
                }
                if (deepLinkId > 0) {
                    clicked_linkedme_link = true;
                    deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
                    browserFingerprintIdForYYBMemCache.delete(deviceFingerprintId + ".yyb"); // 匹配成功后删除记录
                }
            }

            if (deepLink != null) {
                params = getParamsFromDeepLink(deepLink);

                // count
                // TODO 如果是pc扫描过来的,需要在openType前边加上 "pc_",eg: pc_ios_open
                if (!Strings.isNullOrEmpty(deepLinkUrl) && deepLinkUrl.startsWith("http") && deepLinkUrl.contains("scan=1")) {
                    isScan = true;
                }
                String scanPrefix = "";
                if (isScan) {
                    scanPrefix = "pc_";
                }
                final String openType = scanPrefix + DeepLinkCount.getCountTypeFromOs(openParams.os, "open");
                openTypeForLog = openType;
                final String clickType = DeepLinkCount.getCountTypeFromOs(openParams.os, "click");
                boolean isUpdateClickCount = isDirectForward;
                long dpId = deepLinkId;
                final int countAppId = (int) appId;

                String date = Util.getCurrDate();
                deepLinkMsgPusher.addDeepLinkCount(deepLinkId, (int) appId, date, openType);

                deepLinkCountThreadPool.submit(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        try {
                            // TODO 对deeplink_id的有效性做判断
                            JedisPort countClient = deepLinkCountShardingSupport.getClient(dpId);
                            countClient.hincrBy(String.valueOf(dpId), openType, 1); // 统计open总计数
                            // 如果是universe link 或者是app links,要记录click计数
                            if (isUpdateClickCount) {
                                countClient.hincrBy(String.valueOf(dpId), clickType, 1);

                                deepLinkMsgPusher.addDeepLinkCount(dpId, countAppId, date, clickType);
                            }
                        } catch (Exception e) {
                            ApiLogger.warn("LMSdkServiceImpl.open deepLinkCountThreadPool count failed", e);
                        }
                        return null;
                    }
                });

                // universe link或者Android直接跳转,没有经过urlServlet,在此处添加点击日志
                if (isDirectForward) {
                    ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", openParams.clientIP, "click", clickType, appId, deepLinkId,
                            "direct forward from:" + openParams.os));
                }
            }
        }

        String sessionId = String.valueOf(System.currentTimeMillis());
        JSONObject resultJson = new JSONObject();
        resultJson.put("session_id", sessionId);
        resultJson.put("identity_id", openParams.identity_id);
        resultJson.put("device_fingerprint_id", openParams.device_fingerprint_id);
        resultJson.put("browser_fingerprint_id", "");
        resultJson.put("link", openParams.extra_uri_data);
        resultJson.put("deeplink_id", deepLinkId);
        resultJson.put("params", params);
        resultJson.put("is_first_session", false);
        resultJson.put("clicked_linkedme_link", clicked_linkedme_link);

        JSONObject log = new JSONObject();
        log.put("request", requestJson);
        log.put("response", resultJson);
        ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", openParams.clientIP, "open", openTypeForLog, appId,
                deepLinkId, openParams.identity_id, openParams.linkedme_key, sessionId, openParams.retry_times, openParams.is_debug,
                openParams.sdk_version, log.toString()));

        return resultJson.toString();
    }

    private static String getParamsFromDeepLink(DeepLink deepLink) {
        if (deepLink == null) {
            return null;
        }
        String param = deepLink.getParams();
        if (Strings.isNullOrEmpty(param)) {
            return null;
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.fromObject(param);
        } catch (Exception e) {
            ApiLogger.warn("LMSdkServiceImpl.getParamsFromDeepLink failed, param json is invalid", e);
            return null;
        }

        String[] tags = new String[0];
        String[] channel = new String[0];
        String[] feature = new String[0];
        String[] stage = new String[0];
        if (!Strings.isNullOrEmpty(deepLink.getTags())) {
            tags = deepLink.getTags().split(",");
        }
        if (!Strings.isNullOrEmpty(deepLink.getChannel())) {
            channel = deepLink.getChannel().split(",");
        }
        if (!Strings.isNullOrEmpty(deepLink.getFeature())) {
            feature = deepLink.getFeature().split(",");
        }
        if (!Strings.isNullOrEmpty(deepLink.getStage())) {
            stage = deepLink.getStage().split(",");
        }

        JSONArray tagsJson = JSONArray.fromObject(tags);
        JSONArray channelJson = JSONArray.fromObject(channel);
        JSONArray featureJson = JSONArray.fromObject(feature);
        JSONArray stageJson = JSONArray.fromObject(stage);

        jsonObject.put("tags", tagsJson);
        jsonObject.put("channel", channelJson);
        jsonObject.put("feature", featureJson);
        jsonObject.put("stage", stageJson);
        return jsonObject.toString();
    }

    public String url(UrlParams urlParams) {
        Joiner joiner = Joiner.on("&").skipNulls();
        Joiner joiner2 = Joiner.on(",").skipNulls();
        // linkedme_key & tags & channel & feature & stage & params TODO 添加identity_id信息
        // 区分用户和设备
        String urlParamsStr = joiner.join(urlParams.linkedme_key, joiner2.join(urlParams.tags), joiner2.join(urlParams.channel),
                joiner2.join(urlParams.feature), joiner2.join(urlParams.stage), urlParams.params);

        // dashboard创建的短链,添加时间戳信息,保证每次都不一样
        if ("Dashboard".equals(urlParams.source)) {
            urlParamsStr = urlParamsStr + "&" + System.currentTimeMillis();
        }
        String deepLinkMd5 = urlParams.deepLinkMd5;
        if (Strings.isNullOrEmpty(deepLinkMd5)) {
            deepLinkMd5 = MD5Utils.md5(urlParamsStr);
        }
        // 从redis里查找md5是否存在
        // 如果存在,找出对应的deeplink_id,base62进行编码,
        // 根据linkedmeKey从redis里查找出appId,生成短链,返回 //http://lkme.cc/abc/qwerk
        // 如果不存在,发号deeplink_id,在redis里保存md5和deeplink_id的键值对
        // 然后把消息写入队列, 返回短链
        JedisPort redisClient = deepLinkShardingSupport.getClient(deepLinkMd5);
        String id = redisClient.get(deepLinkMd5);

        long appId = urlParams.app_id; // web创建url传appid, sdk创建url不传appid
        if (appId <= 0) {
            if (Strings.isNullOrEmpty(urlParams.linkedme_key)) {
                throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "linkedme_key is invalid");
            }
            JedisPort linkedmeKeyClient = linkedmeKeyShardingSupport.getClient(urlParams.linkedme_key);
            String appIdStr = linkedmeKeyClient.hget(urlParams.linkedme_key, "appid");
            if (appIdStr != null) {
                appId = Long.parseLong(appIdStr);
            }
        }
        if (appId <= 0) {
            String msg = ("Dashboard".equals(urlParams.source)) ? "app_id = 0" : "linkedme_key is invalid";
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, msg);
        }
        if (id != null) {
            String link = Base62.encode(Long.parseLong(id));
            return Constants.DEEPLINK_HTTPS_PREFIX + "/" + Base62.encode(appId) + "/" + link;
        }

        Map<String, List<Long>> testDeviceMap = DeviceServiceImpl.whiteDeviceMap.get();

        boolean isTestDevice = !MapUtils.isEmpty(testDeviceMap) && testDeviceMap.containsKey(urlParams.device_id);
        boolean isTestApp =
                !CollectionUtils.isEmpty(testDeviceMap.get(urlParams.device_id)) && testDeviceMap.get(urlParams.device_id).contains(appId);

        String urlType = "live";
        if (isTestDevice && isTestApp) {
            urlType = "test";
        } else if (!Strings.isNullOrEmpty(urlParams.type) && urlParams.type.equals("test")) {
            urlType = "test";
        }

        long deepLinkId = uuidCreator.nextId(0); // 0表示发号器的deepLink业务
        String params = urlParams.params == null ? "" : urlParams.params.toString();
        DeepLink link = new DeepLink(deepLinkId, deepLinkMd5, urlParams.app_id, urlParams.linkedme_key, urlParams.identity_id,
                ArrayUtil.strArrToString(urlParams.tags), urlParams.alias, ArrayUtil.strArrToString(urlParams.channel),
                ArrayUtil.strArrToString(urlParams.feature), ArrayUtil.strArrToString(urlParams.stage),
                ArrayUtil.strArrToString(urlParams.campaign), params, urlParams.source, urlParams.sdk_version);
        link.setLink_label(urlParams.link_label);
        link.setIos_use_default(urlParams.ios_use_default);
        link.setIos_custom_url(urlParams.ios_custom_url);
        link.setAndroid_use_default(urlParams.android_use_default);
        link.setAndroid_custom_url(urlParams.android_custom_url);
        link.setDesktop_use_default(urlParams.desktop_use_default);
        link.setDesktop_custom_url(urlParams.desktop_custom_url);
        link.setType(urlType);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createTime = UuidHelper.getDateFromId(deepLinkId);
        link.setCreateTime(df.format(createTime)); // 设置deeplink的创建时间
        link.setAppId(appId);
        // 写redis
        redisClient.set(deepLinkMd5, deepLinkId);
        // set mc
        deepLinkService.addDeepLinkToCache(link);
        // 写消息队列
        deepLinkMsgPusher.addDeepLink(link);
        String result = Constants.DEEPLINK_HTTPS_PREFIX + "/" + Base62.encode(appId) + "/" + Base62.encode(deepLinkId);

        return result;
    }

    public void close(CloseParams closeParams) {
        ApiLogger.info(closeParams.device_fingerprint_id + ", " + closeParams.linkedme_key + " close");// 记录日志
    }
}
