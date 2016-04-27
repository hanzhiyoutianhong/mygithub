package cc.linkedme.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cc.linkedme.commons.util.ArrayUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.linkedme.commons.util.MD5Utils;

import com.google.common.base.Joiner;

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

    public boolean doAuth(String... params) {
        if(params == null || params.length <= 2) {
            return false;
        }
        String apiName = params[0];
        String sign = params[1];
        List<String> paramsArr = new ArrayList<>(params.length);
        for(int i = 2; i < params.length; i++) {
            paramsArr.add(params[i]);
        }
        Collections.sort(paramsArr);
        Joiner joiner = Joiner.on("&").skipNulls();
        String signParamsStr = joiner.join(paramsArr);
        signParamsStr = apiName + ":" + signParamsStr;
        String token = MD5Utils.md5(signParamsStr); //TODO 打md5值是否考虑用secret字段,现在sign里带了时间戳,每次调接口sign都不一样
        return token.equals(sign);

    }
}
