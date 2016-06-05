package cc.linkedme.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.linkedme.commons.cookie.CookieHelper;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.profile.ProfileType;
import cc.linkedme.commons.profile.ProfileUtil;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.useragent.Client;
import cc.linkedme.commons.useragent.Parser;
import cc.linkedme.commons.useragent.UserAgent;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.util.UuidHelper;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.webapi.AppService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.common.base.Joiner;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by LinkedME01 on 16/4/1.
 */
public class UrlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Parser userAgentParser;

    private DeepLinkService deepLinkService;

    private AppService appService;

    private ShardingSupportHash<JedisPort> clientShardingSupport;

    private ShardingSupportHash<JedisPort> deepLinkCountShardingSupport;

    private UuidCreator uuidCreator;

    private static ThreadPoolExecutor deepLinkCountThreadPool = new ThreadPoolExecutor(20, 20, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(300), new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        userAgentParser = (Parser) context.getBean("userAgentParser");
        deepLinkService = (DeepLinkService) context.getBean("deepLinkService");
        appService = (AppService) context.getBean("appService");
        clientShardingSupport = (ShardingSupportHash) context.getBean("clientShardingSupport");
        deepLinkCountShardingSupport = (ShardingSupportHash) context.getBean("deepLinkCountShardingSupport");
        uuidCreator = (UuidCreator) context.getBean("uuidCreator");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long start = System.currentTimeMillis();
        // TODO Auto-generated method stub
        // eg, https://lkme.cc/hafzh/fhza80af?scan=0; appId, deeplinkId;
        String uri = request.getRequestURI();
        String urlScanParam = request.getParameter("scan");

        String[] uriArr = uri.split("/");
        if (uriArr.length < 3) {
            response.sendRedirect("/index.jsp"); // TODO 重定向为默认配置页面
            // request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        long appId = Base62.decode(uriArr[1]);
        long deepLinkId = Base62.decode(uriArr[2]);

        if (appId < 10000 || appId > 510000 || (!UuidHelper.isValidId(deepLinkId))) {
            // 无效的appId或者无效的短链. TODO app数量超过50w后,修改阀值
            response.sendRedirect("/index.jsp"); // TODO 重定向为默认配置页面
            return;
        }

        // 根据deepLinkId获取deepLink信息
        DeepLink deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
        AppInfo appInfo = appService.getAppById(appId); // 根据appId获取app信息

        if (deepLink == null || appInfo == null) {
            response.sendRedirect("/index.jsp"); // TODO 重定向为默认配置页面
            return;
        }

        // useAgent
        // 使用yaml解析user agent,测试匹配优先级,速度,打日志统计时间,优化正则表达式(单个正则表达式,优先级);
        String userAgent = request.getHeader("user-agent");
        Client client = userAgentParser.parseUA(userAgent);

        // old:userAgent只会匹配一个family, eg,ua里既带wechat信息,又带chrome信息,返回结果只有chrome,导致后边分支判断不准
        // new:把userAgent匹配结果变成List
        List<UserAgent> userAgentList = client.userAgent;
        Map<String, UserAgent> uaMap = new HashMap<String, UserAgent>(userAgentList.size());
        for (UserAgent ua : userAgentList) {
            uaMap.put(ua.family, ua);
        }
        String osFamily = client.os.family;
        String osMajor = client.os.major;
        String osVersion = Joiner.on(".").skipNulls().join(client.os.major, client.os.minor, client.os.patch);

        // 如果没有cookie,设置cookie
        String identityId = CookieHelper.getCookieValue(request, CookieHelper.getCookieName());
        boolean isValidIdentity = false;
        if (Strings.isNullOrEmpty(identityId)) {
            identityId = String.valueOf(uuidCreator.nextId(1));
            CookieHelper.setCookie(response, CookieHelper.getCookieName(), identityId);
        } else {
            // 如果有cookie,查询库里是否有identity_id的记录;
            JedisPort identityRedisClient = clientShardingSupport.getClient(identityId);
            String deviceId = identityRedisClient.get(identityId + ".di");
            isValidIdentity = !Strings.isNullOrEmpty(deviceId);
        }

        // 生成browser_fingerprint_id
        String clientIP = request.getHeader("x-forwarded-for");
        String browserFingerprintId = MD5Utils.md5(Joiner.on("&").skipNulls().join(appId, osFamily, osVersion, clientIP));

        boolean isUniversalLink = false;
        boolean isDownloadDirectly = false;
        boolean isCannotDeeplink = false; // What do you means for CannotDeepLink?
        boolean isCannotGetWinEvent = false; // TODO
        boolean isCannotGoMarket = false;
        boolean isForceUseScheme = false;

        String url = "";
        String scheme = "";
        boolean isIOS = false;
        boolean isAndroid = false;
        boolean yybAppDownloadAvailable = false;
        String countType;
        if (osFamily.equals("iOS")) {
            isIOS = true;

            if (appInfo.hasIos()) {
                if ("apple_store".equals(appInfo.getIos_search_option())) {
                    url = appInfo.getIos_store_url();
                    isDownloadDirectly = true;
                } else if ("custom_url".equals(appInfo.getIos_search_option())) {
                    url = appInfo.getIos_custom_url();
                    isCannotGoMarket = true;
                    isDownloadDirectly = true;
                }
                scheme = appInfo.getIos_uri_scheme();
                // universe link是否需要team_id, appInfo.getIos_team_id() != null
                if (appInfo.getIos_bundle_id() != null && Integer.parseInt(osMajor) >= 9) {
                    isUniversalLink = true;
                }
            } else {
                url = appInfo.getIos_not_url();
                isCannotGoMarket = true;
            }

            if ("1".equals(urlScanParam)) {
                countType = "pc_ios_scan";
            } else {
                countType = "ios_click";
            }

            if (deepLink.getSource() != null && deepLink.getSource().trim().toLowerCase().equals("dashboard")
                    && !deepLink.isIos_use_default() && deepLink.getIos_custom_url() != null) {
                clickCount(deepLinkId, countType);
                ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click", countType, appId,
                        deepLinkId, userAgent));
                response.sendRedirect(formatCustomUrl(deepLink.getIos_custom_url()));
                //invoke ProfileUtil
                recordClickIntoProfile(start, countType);
                return;
            }

        } else if (osFamily.equals("Android")) {
            isAndroid = true;
            if (appInfo.hasAndroid()) {
                if ("google_play".equals(appInfo.getAndroid_search_option())) {
                    url = appInfo.getGoogle_play_url();
                    isDownloadDirectly = false;
                } else if ("custom_url".equals(appInfo.getAndroid_search_option())) {
                    url = appInfo.getAndroid_custom_url();
                    isDownloadDirectly = true;
                }
                scheme = appInfo.getAndroid_uri_scheme();
            } else {
                url = appInfo.getAndroid_not_url();

            }

            if ("1".equals(urlScanParam)) {
                countType = "pc_adr_scan";
            } else {
                countType = "adr_click";
            }

            if (deepLink.getSource() != null && deepLink.getSource().trim().toLowerCase().equals("dashboard")
                    && !deepLink.isAndroid_use_default() && deepLink.getAndroid_custom_url() != null) {
                clickCount(deepLinkId, countType);
                ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click", countType, appId,
                        deepLinkId, userAgent));
                response.sendRedirect(formatCustomUrl(deepLink.getAndroid_custom_url()));
                //invoke ProfileUtil
                recordClickIntoProfile(start, countType);
                return;
            }

        } else {
            // 点击计数else,暂时都计pc
            countType = "pc_click";

            if (deepLink.getSource() != null && deepLink.getSource().trim().toLowerCase().equals("dashboard")
                    && !deepLink.isDesktop_use_default() && deepLink.getDesktop_custom_url() != null) {
                clickCount(deepLinkId, countType);
                ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click", countType, appId,
                        deepLinkId, userAgent));
                response.sendRedirect(formatCustomUrl(deepLink.getDesktop_custom_url()));
                return;
            }

            // TODO 显示二维码代码 CodeServlet code.jsp
            String location = "https://lkme.cc/code.jsp";
            String codeUrl = Constants.DEEPLINK_HTTPS_PREFIX + request.getRequestURI() + "?scan=1";

            clickCount(deepLinkId, countType);
            ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click", countType, appId,
                    deepLinkId, userAgent));

            response.sendRedirect(location + "?code_url=" + codeUrl);
            //invoke ProfileUtil
            recordClickIntoProfile(start, countType);
            return;
        }

        // iPad

        // 如果连接里包含"visit_id",说明之前已经记录过一次计数和日志
        String visitId = request.getParameter("visit_id");
        if (visitId == null) {
            // 点击计数
            clickCount(deepLinkId, countType);
            // 记录日志
            ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click", countType, appId,
                    deepLinkId, userAgent));
        }

        boolean isWechat = false;
        boolean isWeibo = false;
        boolean isQQ = false;
        boolean isQQBrowser = false;
        boolean isFirefox = false;
        boolean isChrome = false;
        boolean isUC = false;

        // DEBUG MODE
        boolean DEBUG = false;

        int userAgentMajor = 0;
        if (uaMap.containsKey("Chrome")) {
            isChrome = true;
            UserAgent ua = uaMap.get("Chrome");
            try {
                userAgentMajor = Integer.valueOf(ua.major);
            } catch (NumberFormatException e) {
                ApiLogger.warn("UrlServlet userAgentMajor is not a number, userAgentMajor=" + userAgentMajor);
            }
        } else if (uaMap.containsKey("Firefox")) {
            isFirefox = true;
        }
        if (uaMap.containsKey("QQ Browser")) {
            isQQBrowser = true;
        }
        if (uaMap.containsKey("QQInner")) {
            isQQ = true;
        }

        if (uaMap.containsKey("WeChat")) {
            isWechat = true;
        }
        if (uaMap.containsKey("Weibo")) {
            isWeibo = true;
        }
        if (uaMap.containsKey("UC Browser")) {
            isUC = true;
        }

        String appLogo = "../img/icon.png";
        if (appInfo.getApp_logo() != null && appInfo.getApp_logo().startsWith("http")) {
            appLogo = appInfo.getApp_logo();
        }

        request.setAttribute("AppName", appInfo.getApp_name());
        request.setAttribute("Pkg", appInfo.getAndroid_package_name());
        request.setAttribute("BundleID", appInfo.getIos_bundle_id());
        request.setAttribute("AppID", appId);
        request.setAttribute("IconUrl", appLogo);
        request.setAttribute("Url", url);
        request.setAttribute("Match_id", uriArr[2]);

        request.setAttribute("Download_msg", ""); // TODO
        request.setAttribute("Download_btn_text", ""); // TODO
        request.setAttribute("Download_title", ""); // TODO


        request.setAttribute("Chrome_major", userAgentMajor);
        request.setAttribute("Ios_major", osMajor);
        request.setAttribute("Redirect_url", "http://www.linkedme.cc"); // TODO

        request.setAttribute("qq_app_download", "http://a.app.qq.com/o/simple.jsp?pkgname=" + appInfo.getAndroid_package_name());
        request.setAttribute("Scheme", scheme);
        request.setAttribute("Host", "linkedme"); // TODO
        request.setAttribute("AppInsStatus", 0); // TODO
        request.setAttribute("TimeStamp", System.currentTimeMillis()); // deepLink 创建时间?
        request.setAttribute("visitId", "visitId"); // TODO

        request.setAttribute("isIOS", isIOS);
        request.setAttribute("isAndroid", isAndroid);

        request.setAttribute("isWechat", isWechat);
        request.setAttribute("isWeibo", isWeibo);
        request.setAttribute("isQQ", isQQ);
        request.setAttribute("isQQBrowser", isQQBrowser);
        request.setAttribute("isFirefox", isFirefox);
        request.setAttribute("isChrome", isChrome);
        request.setAttribute("isUC", isUC);
        request.setAttribute("isYYBAppDownloadAvailable", yybAppDownloadAvailable );

        request.setAttribute("isUniversalLink", isUniversalLink);
        request.setAttribute("isDownloadDirectly", isDownloadDirectly);
        request.setAttribute("isCannotDeeplink", isCannotDeeplink);
        request.setAttribute("isCannotGetWinEvent", isCannotGetWinEvent);
        request.setAttribute("isCannotGoMarket", isCannotGoMarket);
        request.setAttribute("isForceUseScheme", isForceUseScheme);

        request.setAttribute("deepLinkId", deepLinkId);
        request.setAttribute("browserFingerprintId", browserFingerprintId);
        request.setAttribute("identityId", identityId);
        request.setAttribute("isValidIdentity", isValidIdentity);

        request.setAttribute("DEBUG", DEBUG);

        if ((!isWechat) && (!isWeibo) && isAndroid && isChrome && userAgentMajor >= 25) {
            String location = "intent://linkedme?click_id=" + uriArr[2] + "#Intent;scheme=" + scheme + ";package="
                    + appInfo.getAndroid_package_name() + ";S.browser_fallback_url=" + url + ";end";
            response.setStatus(307);
            response.setHeader("Location", location);
            // response.sendRedirect(location);
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/linkedme.jsp");
        dispatcher.forward(request, response);

        //invoke ProfileUtil
        recordClickIntoProfile(start, countType);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private void clickCount(long deepLinkId, String countType) {
        // TODO 如果短链的访问量急剧增长,线程池扛不住,后续考虑推消息队列
        deepLinkCountThreadPool.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    // TODO 对deeplink_id的有效性做判断
                    JedisPort countClient = deepLinkCountShardingSupport.getClient(deepLinkId);
                    countClient.hincrBy(String.valueOf(deepLinkId), countType, 1);
                } catch (Exception e) {
                    ApiLogger.warn("UrlServlet deepLinkCountThreadPool count failed", e);
                }
                return null;
            }
        });
    }

    private String formatCustomUrl(String url) {
        if (Strings.isNullOrEmpty(url) || url.startsWith("http")) {
            return url;
        }
        return "http://" + url;
    }


    private void recordClickIntoProfile(long start, String countType) {
        long end = System.currentTimeMillis();
        long cost = end - start;
        countType = "/click/" + countType;
        ProfileUtil.accessStatistic(ProfileType.API.value(), countType, end, cost);
    }
}
