package cc.linkedme.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cc.linkedme.commons.useragent.Client;
import cc.linkedme.commons.useragent.Parser;

/**
 * Created by LinkedME01 on 16/4/2.
 */

public class UrlTestServlet extends HttpServlet {

    private Parser userAgentParser;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        userAgentParser = (Parser) context.getBean("userAgentParser");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userAgent = request.getHeader("user-agent");
        Client client = userAgentParser.parse(userAgent);
        // String userAgentFamily = client.userAgent.family;
        // String userAgentMajor = client.userAgent.major;
        // String osFamily = client.os.family;
        // String osMajor = client.os.major;
        // String deviceFamily = client.device.family;

        // String location = "intent://linkedme?click_id=" + uriArr[2] + "#Intent;scheme=" + scheme
        // + ";package=" + appInfo.getAndroid_package_name() + ";S.browser_fallback_url=" + url +
        // ";end";
        String location = "http://www.baidu.com";
        response.setStatus(307);
        response.setHeader("Location", location);
        // response.sendRedirect(location);
        // request.getRequestDispatcher("/index1.jsp").forward(request,response);
    }
}
