package cc.linkedme.uber.rides.service;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.ws.rs.core.HttpHeaders;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.util.Util;
import cc.linkedme.data.model.ButtonCount;
import cc.linkedme.mcq.ButtonCountMsgPusher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import cc.linkedme.commons.constants.DateFormatConstants;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.Ride;
import cc.linkedme.data.model.params.ClickBtnParams;
import cc.linkedme.data.model.params.GetBtnStatusParams;
import cc.linkedme.enums.BtnCountType;
import cc.linkedme.enums.TerminatorPlatform;
import cc.linkedme.service.webapi.BtnService;
import cc.linkedme.service.webapi.ConsumerService;

import com.google.api.client.repackaged.com.google.common.base.Strings;

/**
 * Created by LinkedME01 on 16/4/8.
 */
@Service
public class RideService {

    @Resource
    BtnService btnService;

    @Resource
    ConsumerService consumerService;

    @Resource
    ShardingSupportHash<JedisPort> btnCountShardingSupport;

    @Resource
    private ButtonCountMsgPusher btnCountMsgPusher;

    
    public String getBtnStatus(GetBtnStatusParams getBtnStatusParams) {

        ButtonInfo btnInfo = btnService.getBtnInfo(getBtnStatusParams.getBtnId());
        ConsumerAppInfo consumerAppInfo = consumerService.getConsumerAppInfo(btnInfo.getConsumerAppId());

        JSONObject json = new JSONObject();
        json.put("online_status", btnInfo.getOnlineStatus() == 1);
        json.put("scheme_url", consumerAppInfo.getSchemeUrl());

        return json.toString();
    }

    
    public String initButton(Ride ride, String btnId, String source) {

        ButtonInfo button = btnService.getBtnInfo(btnId);

        if( button == null ) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid button id!");
        }

        ConsumerAppInfo consumerApp = consumerService.getConsumerAppInfo(button.getConsumerAppId());

        // 计数view
        countView(button, source);
        
        UberReturn uberReturn = getUberReturn(ride, consumerApp);
       
        String uberParams = String.format("client_id=%s&action=setPickup&pickup[latitude]=%s&pickup[longitude]=%s"
                + "&pickup[formatted_address]=%s&dropoff[latitude]=%s&dropoff[longitude]=%s&dropoff[formatted_address]=%s",
                consumerApp.getAppLogoUrl(), ride.getPickupLatitude(), ride.getPickupLongitude(), ride.getPickupLabel(),
                ride.getDropoffLatitude(), ride.getDropoffLongitude(), ride.getDropoffLabel());
        String schemeUrl =consumerApp.getSchemeUrl() + uberParams;
        
        if (!Strings.isNullOrEmpty(uberReturn.getProductId())) {
            schemeUrl = schemeUrl + "&product_id=" + uberReturn.getProductId();
        }
        
        JSONObject json = new JSONObject();

        boolean onlineStatus = (button.getOnlineStatus() == 0) ? false : true;
        json.put("online_status", onlineStatus);

        JSONObject btnTitle = new JSONObject();
        btnTitle.put("btn_icon", consumerApp.getAppLogoUrl());
        String btnMsg = "距离" + uberReturn.getDistance() + "公里,需花费约" + uberReturn.getPrice();
        btnTitle.put("btn_msg", btnMsg);
        btnTitle.put("scheme_url", schemeUrl);

        String customUrl = consumerApp.getCustomUrl() + uberParams;
        btnTitle.put("custom_url", customUrl);
        btnTitle.put("default_url", consumerApp.getDefaultUrl());
        btnTitle.put("click_url", cc.linkedme.commons.util.Constants.BTN_CLICK_URL);

        json.put("btn_title", btnTitle);

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
        JedisPort btnCountClient = btnCountShardingSupport.getClient(btnCountKey);
        btnCountClient.hincrBy(btnCountKey, hashField, 1);
        

    }
 
    
    private void countView(ButtonInfo button, String source){
        
        String countType;
        switch (TerminatorPlatform.enumOf(source.toLowerCase())) {
            case ANDROID:
                countType = TerminatorPlatform.ANDROID.value + BtnCountType.VIEW.value;
                break;
            case IOS:
                countType = TerminatorPlatform.IOS.value + BtnCountType.VIEW.value;
                break;
            case WEB:
                countType = TerminatorPlatform.WEB.value + BtnCountType.VIEW.value;
                break;
            default:
                countType = TerminatorPlatform.OTHER.value + BtnCountType.VIEW.value;
        }
        
        ButtonCount buttonCount = new ButtonCount();
        buttonCount.setAppId(button.getAppId());
        buttonCount.setBtnId(button.getBtnId());
        buttonCount.setCountType(countType);
        buttonCount.setConsumerId(button.getConsumerAppId());
        buttonCount.setCountValue(1);

        String date = Util.getCurrDate();
        buttonCount.setDate(date);

        btnCountMsgPusher.addButtonCount(buttonCount);
        
        String today = DateFormatUtils.format(new Date(), DateFormatConstants.yyyyMMdd);
        String btnCountKey = today + "_" + button.getAppId() + "_" + button.getBtnId() + "_" + button.getConsumerAppId();

        JedisPort btnCountClient = btnCountShardingSupport.getClient(btnCountKey);
        btnCountClient.hincrBy(btnCountKey, countType, 1);
        
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

        btnCountMsgPusher.addButtonCount(buttonCount);
    }
    
    
    private UberReturn getUberReturn(Ride ride, ConsumerAppInfo consumerApp) {

        String requestUrl =
                String.format(
                        "https://api.uber.com.cn/v1/estimates/price?start_latitude=%s&start_longitude=%s&end_latitude=%s&end_longitude=%s",
                        ride.getPickupLatitude(), ride.getPickupLongitude(), ride.getDropoffLatitude(), ride.getDropoffLongitude());
        
        HttpClient httpClient = new HttpClient();
        HttpMethod httpMethod = new GetMethod(requestUrl);
        httpMethod.setRequestHeader(HttpHeaders.AUTHORIZATION, "Token " + consumerApp.getServerToken());
        httpMethod.setRequestHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        String httpResult = null;
        try {
            httpClient.executeMethod(httpMethod);
            httpResult = new String(httpMethod.getResponseBodyAsString().getBytes("UTF-8"));
            httpMethod.releaseConnection();
        } catch (IOException e) {
            ApiLogger.warn("UberService.initButton get price and distance from uber failed", e);
        }

        UberReturn uberReturn = new UberReturn();
        if (!Strings.isNullOrEmpty(httpResult) && httpResult.contains("prices")) {
            JSONObject httpResultJson = JSONObject.fromObject(httpResult);
            JSONArray jsonArray = httpResultJson.getJSONArray("prices");
            if (jsonArray != null && jsonArray.size() > 0) {
                JSONObject priceJson = jsonArray.getJSONObject(0);
                uberReturn.setProductId(priceJson.getString("product_id"));
                String price = priceJson.getString("estimate");
                if (Strings.isNullOrEmpty(price)) {
                    price = "";
                }
                uberReturn.setPrice(price);
                
                double distance = priceJson.getDouble("distance");
                //uber返回英里，转换成公里
                uberReturn.setDistance(String.format("%.2f", distance * 1.6093));
            }
        }

        return uberReturn;
    }
  
    
    class UberReturn{
        
        private String productId = StringUtils.EMPTY;
        private String price = StringUtils.EMPTY;
        private String distance;
        
        public String getProductId() {
            return productId;
        }
        public void setProductId(String productId) {
            this.productId = productId;
        }
        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }
        public String getDistance() {
            return distance;
        }
        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
