package cc.linkedme.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cc.linkedme.commons.util.Util;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.linkedme.commons.util.MD5Utils;

import com.google.common.base.Joiner;

@Service
public class SignAuthService {
    public boolean doAuth(ServletRequest request, ServletResponse response) {
        String sign = request.getParameter("sign");
        String linkedmeKey = request.getParameter("linkedme_key");
        if (Strings.isNullOrEmpty(linkedmeKey)) {
            return false;
        }
        String source = request.getParameter("source");
        if (!Strings.isNullOrEmpty(source)) {
            source = source.trim().toLowerCase();
            // TODO Android sdk 还没有认证功能,后续需要加上
            // web sdk和dashboard因为是js代码,加上认证会暴露认证算法,所以先去掉
            if (source.equals("android") || source.equals("web") || source.equals("dashboard")) {
                return true;
            }
        }

        // TODO 后续不区分live/test key,这句要去掉
        linkedmeKey = Util.formatLinkedmeKey(linkedmeKey);

        // 838212907f1a18565f85a63ed2508774 LinkedME
        // 03d6139e11d1b757a454ad7e07e3a295 创头条
        // 7e289a2484f4368dbafbd1e5c7d06903 LinkedME-Demo
        // 4e3bbeda8fbc770b2f03f398a5402ded starucan
        // 443898f27af1957b16e42882b3ffc8db 在看
        // a9a595a9ae75e2a01a81d2c22558d211 Testtt
        // 2765cc86fd85d204bc14e990fae66612 LinkedME-Demo
        // bd43469124c939000db65995ebfe5b33 小酱油
        if (linkedmeKey.equals("838212907f1a18565f85a63ed2508774") || linkedmeKey.equals("03d6139e11d1b757a454ad7e07e3a295")
                || linkedmeKey.equals("7e289a2484f4368dbafbd1e5c7d06903") || linkedmeKey.equals("4e3bbeda8fbc770b2f03f398a5402ded")
                || linkedmeKey.equals("443898f27af1957b16e42882b3ffc8db") || linkedmeKey.equals("a9a595a9ae75e2a01a81d2c22558d211")
                || linkedmeKey.equals("2765cc86fd85d204bc14e990fae66612") || linkedmeKey.equals("bd43469124c939000db65995ebfe5b33")) {
            return true;
        }
        Enumeration paramNames = request.getParameterNames();
        List<String> signParams = new ArrayList<>();
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
        if (params == null || params.length <= 2) {
            return false;
        }
        String apiName = params[0];
        String sign = params[1];
        List<String> paramsArr = new ArrayList<>(params.length);
        for (int i = 2; i < params.length; i++) {
            paramsArr.add(params[i]);
        }
        Collections.sort(paramsArr);
        Joiner joiner = Joiner.on("&").skipNulls();
        String signParamsStr = joiner.join(paramsArr);
        signParamsStr = apiName + ":" + signParamsStr;
        String token = MD5Utils.md5(signParamsStr); // TODO
                                                    // 打md5值是否考虑用secret字段,现在sign里带了时间戳,每次调接口sign都不一样
        return token.equals(sign);

    }
}
