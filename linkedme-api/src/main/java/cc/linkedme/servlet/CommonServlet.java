package cc.linkedme.servlet;

import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.uri.UriComponent;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.container.servlet.WebConfig;
import com.sun.jersey.spi.spring.container.SpringComponentProviderFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by LinkedME01 on 16/4/2.
 */
public class CommonServlet extends ServletContainer {

    private static final long serialVersionUID = 5686655395749077671L;
    private static final Logger LOGGER = Logger.getLogger(CommonServlet.class.getName());
    public static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    public CommonServlet() {
    }

    protected ResourceConfig getDefaultResourceConfig(Map<String, Object> props, WebConfig webConfig) throws ServletException {
        return new DefaultResourceConfig();
    }

    protected void initiate(ResourceConfig rc, WebApplication wa) {
        try {
            wa.initiate(rc, new SpringComponentProviderFactory(rc, this.getContext()));
        } catch (RuntimeException var4) {
            LOGGER.log(Level.SEVERE, "Exception occurred when intialization", var4);
            throw var4;
        }
    }

    protected ConfigurableApplicationContext getContext() {
        String contextConfigLocation = this.getWebConfig().getInitParameter("contextConfigLocation");
        if(contextConfigLocation == null) {
            LOGGER.info("Using default applicationContext");
            return this.getDefaultContext();
        } else {
            LOGGER.info("Creating new child context from " + contextConfigLocation);
            return this.getChildContext(contextConfigLocation);
        }
    }

    protected ConfigurableApplicationContext getDefaultContext() {
        WebApplicationContext springWebContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        ConfigurableApplicationContext springContext = (ConfigurableApplicationContext)springWebContext;
        return springContext;
    }

    protected ConfigurableApplicationContext getChildContext(String contextConfigLocation) {
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setParent(this.getDefaultContext());
        ctx.setServletContext(this.getServletContext());
        ctx.setConfigLocation(contextConfigLocation);
        ctx.refresh();
        return ctx;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {
            /**
             * There is an annoying edge case where the service method is
             * invoked for the case when the URI is equal to the deployment URL
             * minus the '/', for example http://locahost:8080/HelloWorldWebApp
             */
            final String servletPath = request.getServletPath();
            String pathInfo = request.getPathInfo();
            StringBuffer requestURL = request.getRequestURL();
            String requestURI = request.getRequestURI();
            final boolean checkPathInfo = pathInfo == null || pathInfo.isEmpty() || pathInfo.equals("/");
            final boolean checkServletPath = servletPath == null || servletPath.isEmpty();
            if (checkPathInfo && checkServletPath && !request.getRequestURI().endsWith("/")) {
//                if (webComponent.getResourceConfig().getFeature(ResourceConfig.FEATURE_REDIRECT)) {
//                    URI l = UriBuilder.fromUri(request.getRequestURL().toString()).
//                            path("/").
//                            replaceQuery(request.getQueryString()).build();
//
//                    response.setStatus(307);
//                    response.setHeader("Location", l.toASCIIString());
//                    return;
//                } else {
                    pathInfo = "/";
                    requestURL.append("/");
                    requestURI += "/";
//                }
            }

            /**
             * The HttpServletRequest.getRequestURL() contains the complete URI
             * minus the query and fragment components.
             */
            UriBuilder absoluteUriBuilder = UriBuilder.fromUri(
                    requestURL.toString());

            /**
             * The HttpServletRequest.getPathInfo() and
             * HttpServletRequest.getServletPath() are in decoded form.
             *
             * On some servlet implementations the getPathInfo() removed
             * contiguous '/' characters. This is problematic if URIs
             * are embedded, for example as the last path segment.
             * We need to work around this and not use getPathInfo
             * for the decodedPath.
             */
            final String decodedBasePath = (pathInfo != null)
                    ? request.getContextPath() + servletPath + "/"
                    : request.getContextPath() + "/";

            final String encodedBasePath = UriComponent.encode(decodedBasePath,
                    UriComponent.Type.PATH);

            if (!decodedBasePath.equals(encodedBasePath)) {
                throw new ContainerException("The servlet context path and/or the " +
                        "servlet path contain characters that are percent enocded");
            }

            final URI baseUri = absoluteUriBuilder.replacePath(encodedBasePath).
                    build();

            String queryParameters = request.getQueryString();
            if (queryParameters == null) {
                queryParameters = "";
            }

            final URI requestUri = absoluteUriBuilder.replacePath(requestURI).
                    replaceQuery(queryParameters).
                    build();

            service(baseUri, requestUri, request, response);
        }
    }
}
