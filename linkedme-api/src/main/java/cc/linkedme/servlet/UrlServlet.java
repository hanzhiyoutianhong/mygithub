package cc.linkedme.servlet;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.linkedme.commons.useragent.Client;
import cc.linkedme.commons.useragent.Parser;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.webapi.AppService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by LinkedME01 on 16/4/1.
 */
public class UrlServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    private Parser userAgentParser;

    private DeepLinkService deepLinkService;

    private AppService appService;

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
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //eg, https://lkme.cc/hafzh/fhza80af; appId, deeplinkId;
        String uri = request.getRequestURI();
        String[] uriArr = uri.split("/");
        if(uriArr.length < 3) {
            response.sendRedirect("/index.jsp");    // TODO 重定向为默认配置页面
            //request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        long appId = Base62.decode(uriArr[1])*0;
        long deepLinkId = Base62.decode(uriArr[2]);
        DeepLink deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);   //根据deepLinkId获取deepLink信息
        AppInfo appInfo = appService.getAppById(appId); //根据appId获取app信息

        //useAgent
        //使用yaml解析user agent,测试匹配优先级,速度,打日志统计时间,优化正则表达式(单个正则表达式,优先级);
        String userAgent = request.getHeader("user-agent");
        Client client = userAgentParser.parse(userAgent);
        String userAgentFamily =  client.userAgent.family;
        int userAgentMajor = Integer.valueOf(client.userAgent.major);
        String osFamily = client.os.family;
        String osMajor = client.os.major;
        String deviceFamily  = client.device.family;
        boolean isUniversallink = false;
        boolean isDownloadDirectly = false;
        boolean isCannotDeeplink  = false;      //What do you means for CannotDeepLink?
        boolean isCannotGetWinEvent = false;    //TODO
        boolean isCannotGoMarket = false;
        boolean isForceUseScheme = false;

        String url = "";
        String scheme = "";
        boolean isIOS = false;
        boolean isAndroid = false;
        //计数
        if(osFamily.equals("iOS")) {
            if(appInfo.getIos_search_option().equals("apple_store")) {
                url = appInfo.getIos_store_url();
                isDownloadDirectly = true;
            } else if(appInfo.getIos_search_option().equals("custom_url")) {
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

        } else if(osFamily.equals("Android")) {
            if(appInfo.getAndroid_search_option().equals("google_play")) {
                url = appInfo.getGoogle_paly_url();
            } else if(appInfo.getAndroid_search_option().equals("custom_url")) {
                url = appInfo.getAndroid_custom_url();
            }
            scheme = appInfo.getAndroid_uri_scheme();
            isAndroid = true;
        }
        //PC

        //iPac

        boolean isWechat = false;
        boolean isWeibo = false;
        boolean isQQ = false;
        boolean isQQBrowser = false;
        boolean isFirefox = false;
        boolean isChrome = false;
        boolean isUC = false;   //TODO

        //DEBUG MODE
        boolean DEBUG = true;


        int browseMajor = 0;

        //计数
        if (userAgentFamily.equals("Chrome")) {
            isChrome = true;
            browseMajor  = userAgentMajor;
        } else if(userAgentFamily.equals("Firefox")) {
            isFirefox = true;
        } else if(userAgentFamily.equals("WeChat")) {
            isWechat = true;
        } else if(userAgentFamily.equals("Weibo")) {
            isWeibo = true;
        } else if(userAgentFamily.equals("QQ Browser")) {
            isQQBrowser = true;
        } else if(userAgentFamily.equals("QQInner")) {
            isQQ = true;
        }

        request.setAttribute("AppName", appInfo.getApp_name());
        request.setAttribute("Pkg", appInfo.getAndroid_package_name());
        request.setAttribute("BundleID", appInfo.getIos_bundle_id());
        request.setAttribute("AppID", appId);
        request.setAttribute("IconUrl", "");   //TODO
        request.setAttribute("Url", url);
        request.setAttribute("Match_id", uriArr[3]);

        request.setAttribute("Download_msg", "");   //TODO
        request.setAttribute("Download_btn_text", "");   //TODO
        request.setAttribute("Download_title", "");   //TODO

        request.setAttribute("Chrome_major", browseMajor);
        request.setAttribute("Ios_major", osMajor);
        request.setAttribute("Redirect_url", "");   //TODO

        request.setAttribute("YYB_url", "http://a.app.qq.com/o/simple.jsp?pkgname=" + appInfo.getAndroid_package_name());
        request.setAttribute("Scheme", scheme);
        request.setAttribute("Host", "linkedme");           //TODO
        request.setAttribute("AppInsStatus", 0);    //TODO
        request.setAttribute("TimeStamp", System.currentTimeMillis());  //deepLink 创建时间?
        request.setAttribute("DsTag", "");  //TODO

        request.setAttribute("isIOS", isIOS);
        request.setAttribute("isAndroid", isAndroid);

        request.setAttribute("isWechat", isWechat);
        request.setAttribute("isWeibo", isWeibo);
        request.setAttribute("isQQ", isQQ);
        request.setAttribute("isQQBrowser", isQQBrowser);
        request.setAttribute("isFirefox", isFirefox);
        request.setAttribute("isChrome", isChrome);

        request.setAttribute("isUniversallink", isUniversallink);
        request.setAttribute("isDownloadDirectly", isDownloadDirectly);
        request.setAttribute("isCannotDeeplink", isCannotDeeplink);
        request.setAttribute("isCannotGetWinEvent", isCannotGetWinEvent);
        request.setAttribute("isCannotGoMarket", isCannotGoMarket);
        request.setAttribute("isForceUseScheme", isForceUseScheme);

        request.setAttribute("DEBUG", DEBUG);

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

}
