package cc.linkedme.common.auth;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthFilter implements Filter {
    @Resource
    private SignAuthService signAuthService;

    @Resource
    private LinkedWebAuthService linkedWebAuthService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authInfo = httpRequest.getHeader("Authorization");
        String[] authInfos = authInfo.split(":");
        String authMethod = authInfos[0] == null ? "" : authInfos[0];
        String[] authInfos1 = authInfos[1].split(" ");
        String token = authInfos1[0] == null ? "" : authInfos1[0];
        String user_id = authInfos1[1] == null ? "" : authInfos1[1];
        if(authMethod.equals("Sign")){
            if (signAuthService.doAuth(request, response)) {
                chain.doFilter(request, response);
            } else {
                httpRequest = (HttpServletRequest) request;
                String responseBody = "{\"sign auth failed, " + httpRequest.getRequestURI() + "\"}";
                byte[] responseData = responseBody.getBytes();
                ServletOutputStream output = response.getOutputStream();
                output.write(responseData);
                output.flush();
                output.close();
            }
        }
        if (authMethod.equals("Token")) {
            if (linkedWebAuthService.doAuth(request, user_id, token)) {
                try {
                    chain.doFilter(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                httpRequest = (HttpServletRequest) request;
                String responseBody = "{\"basic auth failed, " + httpRequest.getRequestURI() + "\"}";
                byte[] responseData = responseBody.getBytes();
                ServletOutputStream output = response.getOutputStream();
                output.write(responseData);
                output.flush();
                output.close();
            }
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
