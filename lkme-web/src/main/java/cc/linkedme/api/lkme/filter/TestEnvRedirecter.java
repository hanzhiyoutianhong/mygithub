package cc.linkedme.api.lkme.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.HeaderGroup;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.http.MultiReadHttpServletRequest;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.service.webapi.impl.DeviceServiceImpl;

public class TestEnvRedirecter implements Filter {

    private ShardingSupportHash<JedisPort> linkedmeKeyShardingSupport;

    private static final String TEST_ENV_HTTP = "http://lkme.cc";

    private static final String LIVE_REQUEST_RREFIX = "/i/";
    private static final String TEST_REQUEST_PREFIX = "/t/";

    private static final HeaderGroup hopByHopHeaders;

    static {
        hopByHopHeaders = new HeaderGroup();
        String[] headers =
                new String[] {"Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization", "TE", "Trailers",
                        "Transfer-Encoding", "Upgrade"};
        for (String header : headers) {
            hopByHopHeaders.addHeader(new BasicHeader(header, null));
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        linkedmeKeyShardingSupport = (ShardingSupportHash<JedisPort>)ctx.getBean("linkedmeKeyShardingSupport");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        MultiReadHttpServletRequest httpRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String deviceId = httpRequest.getParameter("device_id");
        String linkedmeKey = httpRequest.getParameter("linkedme_key");
        
        if(StringUtils.isBlank(deviceId) || StringUtils.isBlank(linkedmeKey)){
            chain.doFilter(httpRequest, response);
            return;
        }
        
        JedisPort linkedmeKeyClient = linkedmeKeyShardingSupport.getClient(linkedmeKey);
        String appIdStr = linkedmeKeyClient.hget(linkedmeKey, "appid");
        
        String requestUri = httpRequest.getRequestURI();
        Map<String, List<Long>> testDeviceMap = DeviceServiceImpl.whiteDeviceMap.get();

        boolean isTestDevice = !MapUtils.isEmpty(testDeviceMap) && testDeviceMap.containsKey(deviceId);
        boolean isTestApp = !CollectionUtils.isEmpty(testDeviceMap.get(deviceId)) && 
                testDeviceMap.get(deviceId).contains(Long.valueOf(appIdStr));
        
        if(isTestDevice && isTestApp && requestUri.startsWith(LIVE_REQUEST_RREFIX)) {

            try (CloseableHttpClient proxyClient = HttpClients.createDefault()) {

                String requestMethod = httpRequest.getMethod();

                String targetUri = TEST_ENV_HTTP + requestUri.replaceFirst(LIVE_REQUEST_RREFIX, TEST_REQUEST_PREFIX);
                String proxyRequestUri = rewriteUrlFromRequest(httpRequest, targetUri);
                HttpRequest proxyRequest;

                // 如果header中有两项中的任何一项，说明http有信息entity
                if (httpRequest.getHeader(HttpHeaders.CONTENT_LENGTH) != null
                        || httpRequest.getHeader(HttpHeaders.TRANSFER_ENCODING) != null) {

                    HttpEntityEnclosingRequest eProxyRequest = new BasicHttpEntityEnclosingRequest(requestMethod, proxyRequestUri);
                    eProxyRequest.setEntity(new InputStreamEntity(httpRequest.getInputStream(), httpRequest.getContentLength()));
                    proxyRequest = eProxyRequest;
                } else {
                    proxyRequest = new BasicHttpRequest(requestMethod, proxyRequestUri);
                }

                copyRequestHeaders(httpRequest, proxyRequest);

                HttpResponse proxyResponse = null;
                try {
                    HttpHost targetHost = URIUtils.extractHost(new URI(targetUri));
                    proxyResponse = proxyClient.execute(targetHost, proxyRequest);

                    int statusCode = proxyResponse.getStatusLine().getStatusCode();
                    httpResponse.setStatus(statusCode);

                    copyResponseHeaders(proxyResponse, httpRequest, httpResponse);
                    copyResponseEntity(proxyResponse, httpResponse);

                } catch (Exception e) {
                    ApiLogger.error("重定向测试请求失败", e);
                    throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "系统异常");
                } finally {
                    if (proxyResponse != null) {
                        EntityUtils.consume(proxyResponse.getEntity());
                    }
                }
            }

        } else {
            chain.doFilter(httpRequest, response);
        }

    }

    private String rewriteUrlFromRequest(HttpServletRequest httpRequest, String targetUri) {

        StringBuilder uri = new StringBuilder(500);
        uri.append(targetUri);

        String queryString = httpRequest.getQueryString();
        String fragment = null;

        if (queryString != null) {
            int fragmentIdx = queryString.indexOf('#');
            if (fragmentIdx >= 0) {
                fragment = queryString.substring(fragmentIdx + 1);
                queryString = queryString.substring(0, fragmentIdx);
            }
        }

        // 把query string挂到新的uri上
        if (StringUtils.isNotBlank(queryString)) {
            if (uri.indexOf("?") == -1) {
                uri.append('?');
            } else {
                uri.append('&');
            }
            uri.append(encodeUriQuery(queryString));
        }

        // 把fragment挂到uri上
        if (fragment != null) {
            uri.append('#');
            uri.append(encodeUriQuery(fragment));
        }

        return uri.toString();
    }


    private void copyRequestHeaders(HttpServletRequest httpRequest, HttpRequest proxyRequest) {

        Enumeration headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();

            // 前面已经设置过
            if (headerName.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)) {
                continue;
            }

            // http路由相关的header不需要复制
            if (hopByHopHeaders.containsHeader(headerName)) {
                continue;
            }

            Enumeration headers = httpRequest.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = (String) headers.nextElement();
                proxyRequest.addHeader(headerName, headerValue);
            }
        }
    }


    private void copyResponseHeaders(HttpResponse proxyResponse, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        for (Header header : proxyResponse.getAllHeaders()) {
            if (hopByHopHeaders.containsHeader(header.getName())) {
                continue;
            }

            servletResponse.addHeader(header.getName(), header.getValue());
        }
    }

    private void copyResponseEntity(HttpResponse proxyResponse, HttpServletResponse httpResponse) throws IOException {

        HttpEntity entity = proxyResponse.getEntity();
        if (entity != null) {
            OutputStream servletOutputStream = httpResponse.getOutputStream();
            entity.writeTo(servletOutputStream);
        }
    }


    private CharSequence encodeUriQuery(CharSequence in) {

        StringBuilder outBuf = null;
        Formatter formatter = null;
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            boolean escape = true;
            if (c < 128) {
                if (asciiQueryChars.get((int) c)) {
                    escape = false;
                }
            } else if (!Character.isISOControl(c) && !Character.isSpaceChar(c)) {
                escape = false;
            }
            if (!escape) {
                if (outBuf != null) {
                    outBuf.append(c);
                }
            } else {
                if (outBuf == null) {
                    outBuf = new StringBuilder(in.length() + 5 * 3);
                    outBuf.append(in, 0, i);
                    formatter = new Formatter(outBuf);
                }
                formatter.format("%%%02X", (int) c);
            }
        }
        return outBuf != null ? outBuf : in;
    }


    private static final BitSet asciiQueryChars;
    static {
        char[] c_unreserved = "_-!.~'()*".toCharArray();
        char[] c_punct = ",;:$&+=".toCharArray();
        char[] c_reserved = "?/[]@".toCharArray();

        asciiQueryChars = new BitSet(128);
        for (char c = 'a'; c <= 'z'; c++) {
            asciiQueryChars.set((int) c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            asciiQueryChars.set((int) c);
        }
        for (char c = '0'; c <= '9'; c++) {
            asciiQueryChars.set((int) c);
        }
        for (char c : c_unreserved) {
            asciiQueryChars.set((int) c);
        }
        for (char c : c_punct) {
            asciiQueryChars.set((int) c);
        }
        for (char c : c_reserved) {
            asciiQueryChars.set((int) c);
        }

        asciiQueryChars.set((int) '%');
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }


}
