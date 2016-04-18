package cc.linkedme.common.auth;

import cc.linkedme.commons.util.MD5Utils;
import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Service
public class SignAuthService {
    public boolean doAuth(ServletRequest request, ServletResponse response) {
        String sign = request.getParameter("sign");
        Enumeration paramNames = request.getParameterNames();
        List<String> signParams = new ArrayList<String>();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("sign".equals(paramName)) {
                continue;
            }
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues != null && paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (StringUtils.isNotBlank(paramValue)) {
                    signParams.add(paramValue);
                }
            }
        }
        Collections.sort(signParams);
        Joiner joiner = Joiner.on("&").skipNulls();
        String signParamsStr = joiner.join(signParams);
        String token = MD5Utils.md5(signParamsStr + "key");
        return token.equals(sign);
    }
}
