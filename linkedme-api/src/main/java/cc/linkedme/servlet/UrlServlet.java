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

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.useragent.Client;
import cc.linkedme.commons.useragent.Parser;
import cc.linkedme.commons.useragent.UserAgent;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.webapi.AppService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
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

    private ShardingSupportHash<JedisPort> deepLinkCountShardingSupport;

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
        deepLinkCountShardingSupport = (ShardingSupportHash) context.getBean("deepLinkCountShardingSupport");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // eg, https://lkme.cc/hafzh/fhza80af?scan=0; appId, deeplinkId;
        String uri = request.getRequestURI();
        String[] uriArr = uri.split("/");
        if (uriArr.length < 3) {
            response.sendRedirect("/index.jsp"); // TODO 重定向为默认配置页面
            // request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        long appId = Base62.decode(uriArr[1]);
        long deepLinkId = Base62.decode(uriArr[2]);
        String urlScanParam = request.getParameter("scan");
        // DeepLink deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId); //
        // 根据deepLinkId获取deepLink信息
        AppInfo appInfo = appService.getAppById(appId); // 根据appId获取app信息 TODO 添加appInfo添加mc

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
        String deviceFamily = client.device.family;
        boolean isUniversallink = false;
        boolean isDownloadDirectly = false;
        boolean isCannotDeeplink = false; // What do you means for CannotDeepLink?
        boolean isCannotGetWinEvent = false; // TODO
        boolean isCannotGoMarket = false;
        boolean isForceUseScheme = false;

        String url = "";
        String scheme = "";
        boolean isIOS = false;
        boolean isAndroid = false;
        String countType;
        if (osFamily.equals("iOS")) {
            if (appInfo.getIos_search_option().equals("apple_store")) {
                url = appInfo.getIos_store_url();
                isDownloadDirectly = true;
            } else if (appInfo.getIos_search_option().equals("custom_url")) {
                url = appInfo.getIos_custom_url();
                isCannotGoMarket = true;
                isDownloadDirectly = true;
            } else if (appInfo.getIos_search_option().equals("not_url")) {
                isCannotGoMarket = true;
            }

            scheme = appInfo.getIos_uri_scheme();
            isIOS = true;
            if (appInfo.getIos_bundle_id() != null && appInfo.getIos_team_id() != null && Integer.parseInt(osMajor) >= 9) {
                isUniversallink = true;
            }

            if ("1".equals(urlScanParam)) {
                countType = "pc_ios_scan";
            } else {
                countType = "ios_click";
            }

        } else if (osFamily.equals("Android")) {
            if (appInfo.getAndroid_search_option().equals("google_play")) {
                url = appInfo.getGoogle_paly_url();
            } else if (appInfo.getAndroid_search_option().equals("custom_url")) {
                url = appInfo.getAndroid_custom_url();
            }
            scheme = appInfo.getAndroid_uri_scheme();
            isAndroid = true;

            if ("1".equals(urlScanParam)) {
                countType = "pc_adr_scan";
            } else {
                countType = "adr_click";
            }
        } else {
            // 点击计数else,暂时都计pc
            countType = "pc_click";

            // TODO 显示二维码代码 CodeServlet code.jsp
            String location = "https://lkme.cc/code.jsp";
            String codeUrl = "https://lkme.cc" + request.getRequestURI() + "?scan=1";

            clickCount(deepLinkId, countType);
            ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click", appId, deepLinkId,
                    countType, userAgent));

            response.sendRedirect(location + "?code_url=" + codeUrl);
            return;
        }

        // iPad

        //如果连接里包含"ds_tag",说明之前已经记录过一次计数和日志 TODO 把ds_tag改成lkme_tag(或者click_tag)
        String dsTag = request.getParameter("ds_tag");
        if (dsTag == null) {
            // 点击计数
            clickCount(deepLinkId, countType);
            // 记录日志
            ApiLogger.biz(String.format("%s\t%s\t%s\t%s\t%s\t%s", request.getHeader("x-forwarded-for"), "click", appId, deepLinkId,
                    countType, userAgent));
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

        request.setAttribute("AppName", appInfo.getApp_name());
        request.setAttribute("Pkg", appInfo.getAndroid_package_name());
        request.setAttribute("BundleID", appInfo.getIos_bundle_id());
        request.setAttribute("AppID", appId);
        request.setAttribute("IconUrl", "../img/icon.png"); // TODO
        request.setAttribute("Url", url);
        request.setAttribute("Match_id", uriArr[2]);

        request.setAttribute("Download_msg", "Download_msg"); // TODO
        request.setAttribute("Download_btn_text", "Download_btn_text"); // TODO
        request.setAttribute("Download_title", "Download_title"); // TODO


        request.setAttribute("Chrome_major", userAgentMajor);
        request.setAttribute("Ios_major", osMajor);
        request.setAttribute("Redirect_url", "http://www.baidu.com"); // TODO

        request.setAttribute("YYB_url", "http://a.app.qq.com/o/simple.jsp?pkgname=" + appInfo.getAndroid_package_name());
        request.setAttribute("Scheme", scheme);
        request.setAttribute("Host", "linkedme"); // TODO
        request.setAttribute("AppInsStatus", 0); // TODO
        request.setAttribute("TimeStamp", System.currentTimeMillis()); // deepLink 创建时间?
        request.setAttribute("DsTag", "DsTag"); // TODO

        request.setAttribute("isIOS", isIOS);
        request.setAttribute("isAndroid", isAndroid);

        request.setAttribute("isWechat", isWechat);
        request.setAttribute("isWeibo", isWeibo);
        request.setAttribute("isQQ", isQQ);
        request.setAttribute("isQQBrowser", isQQBrowser);
        request.setAttribute("isFirefox", isFirefox);
        request.setAttribute("isChrome", isChrome);
        request.setAttribute("isUC", isUC);

        request.setAttribute("isUniversallink", isUniversallink);
        request.setAttribute("isDownloadDirectly", isDownloadDirectly);
        request.setAttribute("isCannotDeeplink", isCannotDeeplink);
        request.setAttribute("isCannotGetWinEvent", isCannotGetWinEvent);
        request.setAttribute("isCannotGoMarket", isCannotGoMarket);
        request.setAttribute("isForceUseScheme", isForceUseScheme);

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

}
