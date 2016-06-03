package cc.linkedme.uber.rides.service;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.InitUberButtonParams;
import cc.linkedme.service.webapi.BtnService;
import cc.linkedme.service.webapi.ConsumerService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by LinkedME01 on 16/4/8.
 */

@Service
public class UberService {
    @Resource
    BtnService btnService;

    @Resource
    ConsumerService consumerService;

    @Resource
    ShardingSupportHash<JedisPort> btnCountShardingSupport;

    public String initButton(InitUberButtonParams initUberButtonParams) {
        // 根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(initUberButtonParams.btn_id);
        ConsumerAppInfo consumerAppInfo = consumerService.getConsumerAppInfo(buttonInfo.getConsumerAppId());

        // 根据buttonInfo.getConsumerAppId()获取变现方的app信息
        String bundleId = consumerAppInfo.getBundleId();
        String packageName = consumerAppInfo.getPackageName();
        String schemeUrl = consumerAppInfo.getSchemeUrl(); // TODO 有可能iOS和Android的schemeUrl不一样
        String customUrl = consumerAppInfo.getCustomUrl();
        String defaultUrl = consumerAppInfo.getDefaultUrl();
        String buttonIcon = consumerAppInfo.getAppLogoUrl();
        String clientId = consumerAppInfo.getClientId();
        String serverToken = consumerAppInfo.getServerToken();

        double startLat = initUberButtonParams.getPickup_lat();
        double startLng = initUberButtonParams.getPickup_lng();
        double endLat = initUberButtonParams.getDropoff_lat();
        double endLng = initUberButtonParams.getDropoff_lng();

        // 计数
        String hashKey = buttonInfo.getAppId() + initUberButtonParams.btn_id;
        String viewCountKey = hashKey + ".view"; // TODO 后续suffix统一成枚举类型
        JedisPort btnCountClient = btnCountShardingSupport.getClient(hashKey);
        btnCountClient.incr(viewCountKey);

        String url = "https://api.uber.com.cn/v1/estimates/price?";
        String param =
                String.format("start_latitude=%s&start_longitude=%s&end_latitude=%s&end_longitude=%s", startLat, startLng, endLat, endLng);
        HttpClient httpClient = new HttpClient();
        HttpMethod httpMethod = new GetMethod(url + param);
        httpMethod.setRequestHeader("Authorization", "Token " + serverToken);
        httpMethod.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        String httpResult = null;
        try {
            httpClient.executeMethod(httpMethod);
            httpResult = new String(httpMethod.getResponseBodyAsString().getBytes("UTF-8"));
            httpMethod.releaseConnection();
        } catch (IOException e) {
            ApiLogger.warn("UberService.initButton get price and distance from uber failed", e);
        }

        String productId = "";
        String price = "";
        double distance = 0;

        if (!Strings.isNullOrEmpty(httpResult) && httpResult.contains("prices")) {
            JSONObject httpResultJson = JSONObject.fromObject(httpResult);
            JSONArray jsonArray = httpResultJson.getJSONArray("prices");
            if (jsonArray != null && jsonArray.size() > 0) {
                JSONObject priceJson = jsonArray.getJSONObject(0);
                productId = priceJson.getString("product_id");
                price = priceJson.getString("estimate");
                if(Strings.isNullOrEmpty(price)) {
                    price = "";
                }
                distance = priceJson.getDouble("distance");
            }
        }

        String formatSchemeUrl = String.format(
                schemeUrl
                        + "client_id=%s&action=setPickup&pickup[latitude]=%s&pickup[longitude]=%s&pickup[formatted_address]=%s&dropoff[latitude]=%s&dropoff[longitude]=%s&dropoff[formatted_address]=%s",
                clientId, startLat, startLng, initUberButtonParams.pickup_label, endLat, endLng, initUberButtonParams.dropoff_label);
        if (!Strings.isNullOrEmpty(productId)) {
            formatSchemeUrl = formatSchemeUrl + "&product_id=" + productId;
        }

        JSONObject btnMsg = new JSONObject();
        btnMsg.put("price", price);
        btnMsg.put("distance", distance);

        JSONObject json = new JSONObject();
        json.put("bundle_id", bundleId);
        json.put("package_name", packageName);
        json.put("scheme_url", formatSchemeUrl);
        json.put("custom_url", customUrl);
        json.put("default_url", defaultUrl);
        json.put("button_icon", buttonIcon);

        json.put("btn_msg", btnMsg);
        return json.toString();
    }

    public void clickBtn(ClickBtnParams clickBtnParams) {
        // 根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(clickBtnParams.btn_id);

        String clickSuffix;
        if ("app".equals(clickBtnParams.open_type)) {
            clickSuffix = ".app";
        } else if ("web".equals(clickBtnParams.open_type)) {
            clickSuffix = ".web";
        } else {
            clickSuffix = ".other";
        }
        String incomeSuffix = ".income";

        // app <-> btn计数
        String btnHashKey = buttonInfo.getAppId() + clickBtnParams.btn_id;
        clickCount(btnHashKey, clickSuffix, incomeSuffix, 0);   //TODO 后续金额改成实际值

        // app <-> consumer_app计数
        String hashKey = String.valueOf(buttonInfo.getAppId()) + buttonInfo.getConsumerAppId();
        clickCount(hashKey, clickSuffix, incomeSuffix, 0);  //TODO 后续金额改成实际值

    }

    private void clickCount(String hashKey, String clickSuffix, String incomeSuffix, float price) {
        JedisPort appCountClient = btnCountShardingSupport.getClient(hashKey);
        appCountClient.incr(hashKey + clickSuffix);
        String appIncome = appCountClient.get(hashKey + incomeSuffix);
        if (Strings.isNullOrEmpty(appIncome)) {
            appCountClient.set(hashKey + incomeSuffix, price);
        } else {
            float newIncome = Float.parseFloat(appIncome) + price;
            appCountClient.set(hashKey + incomeSuffix, newIncome);
        }
    }
}
