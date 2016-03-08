package cc.linkedme.commons.auth;

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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (signAuthService.doAuth(request, response)) {
            chain.doFilter(request, response);
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String responseBody = "{\"auth failed, " + httpRequest.getRequestURI() + "\"}";
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
