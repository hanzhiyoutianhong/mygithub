package cc.linkedme.auth;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cc.linkedme.commons.json.JsonBuilder;

public class SdkAuthFilter implements Filter {
    @Resource
    private SignAuthService signAuthService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean authResult = signAuthService.doAuth(request, response);
        if (authResult) {
            chain.doFilter(request, response);
        } else {
            response.getWriter().write(getAuthFailedMsg());
            return;
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

    private String getAuthFailedMsg() {
        JsonBuilder jb = new JsonBuilder();
        jb.append("error_code", 40100);
        jb.append("err_msg", "Auth failed!");
        return jb.flip().toString();
    }

}
