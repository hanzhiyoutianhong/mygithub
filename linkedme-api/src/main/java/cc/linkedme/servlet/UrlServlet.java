package cc.linkedme.servlet;

import cc.linkedme.commons.useragent.Client;
import cc.linkedme.commons.useragent.Parser;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.service.DeepLinkService;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by LinkedME01 on 16/4/1.
 */
public class UrlServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Resource
    private Parser userAgentParser;

    @Resource
    private DeepLinkService deepLinkService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String uri = request.getRequestURI();
        String[] uriArr = uri.split("/");
        if(uriArr.length < 5) {
            return; //error page
        }
        long appId = Long.parseLong(uriArr[3]);
        long deepLinkId = Long.parseLong(uriArr[4]);
        DeepLink deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);   //根据deepLinkId获取短链对象(mc mysql)
        AppInfo appInfo = null;

        //useAgent
        String userAgent = request.getHeader("user-agent");
        Client client = userAgentParser.parse(userAgent);
        String userAgentFamily =  client.userAgent.family;
        String userAgentMajor = client.userAgent.major;
        String osFamily = client.os.family;
        String osMajor = client.os.major;
        String deviceFamily  = client.device.family;


        if (osFamily.equals("iOS")) {

        }


        request.setAttribute("url", deepLink.getAndroid_custom_url());
        request.getRequestDispatcher("/openApp.jsp").forward(request,response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
