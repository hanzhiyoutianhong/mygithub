package cc.linkedme.auth;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AuthFilter implements Filter {
    @Resource
    private SignAuthService signAuthService;

    @Resource
    private LinkedWebAuthService linkedWebAuthService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        //登录、注册、下载demo不需要token
        if (path.contains("/user/login") || path.contains("/user/register") || path.contains("/v1/request_demo")) {
            chain.doFilter(request, response);
            return;
        }

        String authInfo = httpRequest.getHeader("Authorization");
        String[] authInfos = authInfo.split(":");
        String authMethod = authInfos[0] == null ? "" : authInfos[0];
        String[] authInfos1 = authInfos[1].trim().split(" ");
        String token = authInfos1[0] == null ? "" : authInfos1[0];
        String user_id = authInfos1[1] == null ? "" : authInfos1[1];
        if(authMethod.trim().equals("Sign")){
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
        if (authMethod.trim().equals("Token")) {
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
