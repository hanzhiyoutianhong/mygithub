package cc.linkedme.common.auth;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Resource
    private SignAuthService signAuthService;

    @Resource
    private LinkedWebAuthService linkedWebAuthService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

/*        if (signAuthService.doAuth(request, response)) {
            chain.doFilter(request, response);
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String responseBody = "{\"sign auth failed, " + httpRequest.getRequestURI() + "\"}";
            byte[] responseData = responseBody.getBytes();
            ServletOutputStream output = response.getOutputStream();
            output.write(responseData);
            output.flush();
            output.close();
        }*/
        if (linkedWebAuthService.doAuth(request, response)) {
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String responseBody = "{\"base auth failed, " + httpRequest.getRequestURI() + "\"}";
            byte[] responseData = responseBody.getBytes();
            ServletOutputStream output = response.getOutputStream();
            output.write(responseData);
            output.flush();
            output.close();
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
