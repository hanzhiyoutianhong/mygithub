package cc.linkedme.uber.rides.service;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import cc.linkedme.commons.util.Util;
import cc.linkedme.data.model.ButtonCount;
import cc.linkedme.mcq.ButtonCountMsgPusher;
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
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.Ride;
import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.GetBtnStatusParams;
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

    @Resource
    private ButtonCountMsgPusher buttonCountMsgPusher;

    public String getBtnStatus(GetBtnStatusParams getBtnStatusParams) {

        ButtonInfo btnInfo = btnService.getBtnInfo(getBtnStatusParams.getBtnId());
        ConsumerAppInfo consumerAppInfo = consumerService.getConsumerAppInfo(btnInfo.getConsumerAppId());

        JSONObject json = new JSONObject();
        json.put("online_status", btnInfo.getOnlineStatus() == 1);
        json.put("scheme_url", consumerAppInfo.getSchemeUrl());

        return json.toString();
    }

    public String initButton(Ride ride, String btnId, String source) {

        // 根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(btnId);

        JSONObject json = new JSONObject();
        // if(buttonInfo.getOnlineStatus() == 0){
        // json.put("online_status", false);
        // return json.toString();
        // }

        ConsumerAppInfo consumerAppInfo = consumerService.getConsumerAppInfo(buttonInfo.getConsumerAppId());

        // 根据buttonInfo.getConsumerAppId()获取变现方的app信息
        String schemeUrl = consumerAppInfo.getSchemeUrl(); // TODO 有可能iOS和Android的schemeUrl不一样
        String customUrl = consumerAppInfo.getCustomUrl();
        String defaultUrl = consumerAppInfo.getDefaultUrl();
        String buttonIcon = consumerAppInfo.getAppLogoUrl();
        String clientId = consumerAppInfo.getClientId();
        String serverToken = consumerAppInfo.getServerToken();

        double startLat = ride.getPickupLatitude();
        double startLng = ride.getPickupLongitude();
        double endLat = ride.getDropoffLatitude();
        double endLng = ride.getDropoffLongitude();

        // 计数
        String hashField;
        String sourceOs = source.toLowerCase();
        if ("ios".equals(sourceOs)) {
            hashField = "ios_view_count";
        } else if ("android".equals(sourceOs)) {
            hashField = "android_view_count";
        } else if ("web".equals(sourceOs)) {
            hashField = "web_view_count";
        } else {
            hashField = "other_view_count";
        }

        buttonCount(btnId, buttonInfo.getAppId(), buttonInfo.getConsumerAppId(), hashField, 1);


        String btnCountKey = DateFormatUtils.format(new Date(), "yyyyMMdd") + "_" + buttonInfo.getAppId() + "_" + btnId + "_"
                + consumerAppInfo.getAppId();

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
                if (Strings.isNullOrEmpty(price)) {
                    price = "";
                }
                distance = priceJson.getDouble("distance");
            }
        }

        String formatSchemeUrl = String.format(
                schemeUrl
                        + "client_id=%s&action=setPickup&pickup[latitude]=%s&pickup[longitude]=%s&pickup[formatted_address]=%s&dropoff[latitude]=%s&dropoff[longitude]=%s&dropoff[formatted_address]=%s",
                clientId, startLat, startLng, ride.getPickupLabel(), endLat, endLng, ride.getDropoffLabel());
        if (!Strings.isNullOrEmpty(productId)) {
            formatSchemeUrl = formatSchemeUrl + "&product_id=" + productId;
        }

        if (buttonInfo.getOnlineStatus() == 0) {
            json.put("online_status", false);
        } else {
            json.put("online_status", true);
        }

        JSONObject btn_title = new JSONObject();
        btn_title.put("btn_icon", buttonIcon);
        String btn_msg = "距离" + distance + ",需花费约" + price;
        btn_title.put("btn_msg", btn_msg);
        btn_title.put("scheme_url", formatSchemeUrl);

        String custom_url = String.format(
                customUrl
                        + "client_id=%s&action=setPickup&pickup[latitude]=%s&pickup[longitude]=%s&pickup[formatted_address]=%s&dropoff[latitude]=%s&dropoff[longitude]=%s&dropoff[formatted_address]=%s",
                clientId, startLat, startLng, ride.getPickupLabel(), endLat, endLng, ride.getDropoffLabel());
        btn_title.put("custom_url", custom_url);
        btn_title.put("default_url", defaultUrl);
        btn_title.put("click_url", cc.linkedme.commons.util.Constants.BTN_CLICK_URL);

        json.put("btn_title", btn_title);

        return json.toString();
    }

    public void clickBtn(ClickBtnParams clickBtnParams) {
        // 根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(clickBtnParams.btn_id);

        String btnCountKey = DateFormatUtils.format(new Date(), "yyyyMMdd") + "_" + buttonInfo.getAppId() + "_" + buttonInfo.getBtnId()
                + "_" + buttonInfo.getConsumerAppId();

        String hashField;
        String sourceOs = clickBtnParams.source.toLowerCase();
        if ("ios".equals(sourceOs)) {
            hashField = "ios_click_count";
        } else if ("android".equals(sourceOs)) {
            hashField = "android_click_count";
        } else if ("web".equals(sourceOs)) {
            hashField = "web_click_count";
        } else {
            hashField = "other_click_count";
        }

        buttonCount(buttonInfo.getBtnId(), buttonInfo.getAppId(), buttonInfo.getConsumerAppId(), hashField, 1);
        ApiLogger.btnCount(btnCountKey);
        JedisPort btnCountClient = btnCountShardingSupport.getClient(btnCountKey);
        btnCountClient.hincrBy(btnCountKey, hashField, 1);

        // String incomeSuffix = ".income";

        // app <-> btn计数
        // String btnHashKey = buttonInfo.getAppId() + clickBtnParams.btn_id;
        // clickCount(btnHashKey, clickSuffix, incomeSuffix, 0); //TODO 后续金额改成实际值

        // app <-> consumer_app计数
        // String hashKey = String.valueOf(buttonInfo.getAppId()) + buttonInfo.getConsumerAppId();
        // clickCount(hashKey, clickSuffix, incomeSuffix, 0); //TODO 后续金额改成实际值

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

    private void buttonCount(String buttonId, long appId, long consumerAppId, String countType, int value) {
        ButtonCount buttonCount = new ButtonCount();
        buttonCount.setAppId(appId);
        buttonCount.setBtnId(buttonId);
        buttonCount.setCountType(countType);
        buttonCount.setConsumerId(consumerAppId);
        buttonCount.setCountValue(value);

        String date = Util.getCurrDate();
        buttonCount.setDate(date);

        buttonCountMsgPusher.addButtonCount(buttonCount);
    }
}
