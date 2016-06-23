package cc.linkedme.uber.rides.service;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.useragent.Constants;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.GetBtnStatusParams;
import cc.linkedme.data.model.params.InitUberButtonParams;
import cc.linkedme.service.webapi.BtnService;
import cc.linkedme.service.webapi.ConsumerService;

import com.google.api.client.repackaged.com.google.common.base.Strings;

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
    
    public String getBtnStatus(GetBtnStatusParams getBtnStatusParams){
        
        ButtonInfo btnInfo = btnService.getBtnInfo(getBtnStatusParams.getBtnId());
        ConsumerAppInfo consumerAppInfo = consumerService.getConsumerAppInfo(btnInfo.getConsumerAppId());
        
        JSONObject json = new JSONObject();
        json.put("online_status", btnInfo.getOnlineStatus() == 1);
        json.put("scheme_url", consumerAppInfo.getSchemeUrl());
        
        return json.toString();
    }
    
    public String initButton(InitUberButtonParams initUberButtonParams) {
        
        // 根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(initUberButtonParams.btn_id);
        
        JSONObject json = new JSONObject();
        if(buttonInfo.getOnlineStatus() == 0){       
            json.put("online_status", false);
            return json.toString();
        }
        
        ConsumerAppInfo consumerAppInfo = consumerService.getConsumerAppInfo(buttonInfo.getConsumerAppId());

        // 根据buttonInfo.getConsumerAppId()获取变现方的app信息
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
        String btnCountKey =
                DateFormatUtils.format(new Date(), "yyyyMMdd") + "_" + buttonInfo.getAppId() + "_" + initUberButtonParams.btn_id + "_"
                        + consumerAppInfo.getAppId();
        String hashField;
        if ("ios".equals(initUberButtonParams.getSource())) {
            hashField ="ios_view";
        } else if ("android".equals(initUberButtonParams.getSource())) {
            hashField = "android_view";
        } else if("web".equals(initUberButtonParams.getSource())) {
            hashField = "web_view";
        } else{
            hashField = "other_view";
        }
        ApiLogger.btnCount(btnCountKey);
        JedisPort btnCountClient = btnCountShardingSupport.getClient(btnCountKey);
        btnCountClient.hincrBy(btnCountKey, hashField, 1);

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

        json.put("online_status", true);
        
        JSONObject btn_title = new JSONObject();
        btn_title.put("btn_icon", buttonIcon);
        String btn_msg = "距离" + distance + ",需花费约" + price;
        btn_title.put("btn_msg", btn_msg);
        btn_title.put("scheme_url", formatSchemeUrl);
        
        String custom_url = String.format(customUrl  + "client_id=%s&action=setPickup&pickup[latitude]=%s&pickup[longitude]=%s&pickup[formatted_address]=%s&dropoff[latitude]=%s&dropoff[longitude]=%s&dropoff[formatted_address]=%s",
                clientId, startLat, startLng, initUberButtonParams.pickup_label, endLat, endLng, initUberButtonParams.dropoff_label);
        btn_title.put("custom_url", custom_url);
        btn_title.put("default_url", defaultUrl);
        btn_title.put("click_url", cc.linkedme.commons.util.Constants.BTN_CLICK_URL);
        
        json.put("btn_title", btn_title);
       
        return json.toString();
    }

    public void clickBtn(ClickBtnParams clickBtnParams) {
        // 根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(clickBtnParams.btn_id);

        String btnCountKey =
                DateFormatUtils.format(new Date(), "yyyyMMdd") + "_" + buttonInfo.getAppId() + "_" + buttonInfo.getBtnId() + "_"
                        + buttonInfo.getConsumerAppId();
        
        String hashField;
        if ("ios".equals(clickBtnParams.source)) {
            hashField ="ios_click";
        } else if ("android".equals(clickBtnParams.source)) {
            hashField = "android_click";
        } else if("web".equals(clickBtnParams.source)) {
            hashField = "web_click";
        } else{
            hashField = "other_click";
        }
        ApiLogger.btnCount(btnCountKey);
        JedisPort btnCountClient = btnCountShardingSupport.getClient(btnCountKey);
        btnCountClient.hincrBy(btnCountKey, hashField, 1);
        
//        String incomeSuffix = ".income";

        // app <-> btn计数
//        String btnHashKey = buttonInfo.getAppId() + clickBtnParams.btn_id;
//        clickCount(btnHashKey, clickSuffix, incomeSuffix, 0);   //TODO 后续金额改成实际值

        // app <-> consumer_app计数
//        String hashKey = String.valueOf(buttonInfo.getAppId()) + buttonInfo.getConsumerAppId();
//        clickCount(hashKey, clickSuffix, incomeSuffix, 0);  //TODO 后续金额改成实际值

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
