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
import cc.linkedme.uber.rides.client.Session;
import cc.linkedme.uber.rides.client.UberRidesServices;
import cc.linkedme.uber.rides.client.UberRidesSyncService;
import cc.linkedme.uber.rides.client.error.NetworkException;
import cc.linkedme.uber.rides.client.model.PriceEstimate;
import cc.linkedme.uber.rides.client.model.PriceEstimatesResponse;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

        //根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(initUberButtonParams.btn_id);
        ConsumerAppInfo consumerAppInfo = consumerService.getConsumerAppInfo(buttonInfo.getConsumerAppId());

        //根据buttonInfo.getConsumerAppId()获取变现方的app信息
        String bundleId = consumerAppInfo.getBundleId();
        String packageName = consumerAppInfo.getPackageName();
        String schemeUrl = consumerAppInfo.getSchemeUrl();  //TODO 有可能iOS和Android的schemeUrl不一样
        String customUrl = consumerAppInfo.getCustomUrl();
        String defaultUrl = consumerAppInfo.getDefaultUrl();
        String buttonIcon = consumerAppInfo.getAppLogoUrl();
        String clientId = consumerAppInfo.getClientId();
        String serverToken = consumerAppInfo.getServerToken();

        float startLat = initUberButtonParams.getPickup_lat();
        float startLng = initUberButtonParams.getPickup_lng();
        float endLat = initUberButtonParams.getDropoff_lat();
        float endLng = initUberButtonParams.getDropoff_lng();

        //计数
        String hashKey = buttonInfo.getAppId() + initUberButtonParams.btn_id;
        String viewCountKey = hashKey + ".view";   //TODO 后续suffix统一成枚举类型
        JedisPort btnCountClient = btnCountShardingSupport.getClient(hashKey);
        btnCountClient.incr(viewCountKey);

        Session session = new Session.Builder()
                .setServerToken(serverToken)
                .setEnvironment(Session.Environment.PRODUCTION)
                .build();
        UberRidesSyncService service = UberRidesServices.createSync(session);
        String price = "";
        float distance = 0;
        try {
            PriceEstimatesResponse priceEstimatesResponse = service.getPriceEstimates(startLat, startLng, endLat, endLng).getBody();    //TODO 改成异步
            List<PriceEstimate> priceEstimateList = priceEstimatesResponse.getPrices();
            price = priceEstimateList.get(0).getEstimate();
            distance = priceEstimateList.get(0).getDistance();
        } catch (NetworkException e) {
            ApiLogger.warn("init_button get price failed");
        }

        JSONObject btnMsg = new JSONObject();
        btnMsg.put("price", price);
        btnMsg.put("distance", distance);

        JSONObject json = new JSONObject();
        json.put("bundle_id", bundleId);
        json.put("package_name", packageName);
        json.put("scheme_url", schemeUrl);
        json.put("custom_url", customUrl);
        json.put("default_url", defaultUrl);
        json.put("button_icon", buttonIcon);

        json.put("btn_msg", btnMsg);
        return json.toString();
    }

    public void clickBtn(ClickBtnParams clickBtnParams) {
        //根据btn_id获取button信息
        ButtonInfo buttonInfo = btnService.getBtnInfo(clickBtnParams.btn_id);

        String clickSuffix;
        if("app".equals(clickBtnParams.open_type)) {
            clickSuffix = ".app";
        } else if("web".equals(clickBtnParams.open_type)) {
            clickSuffix = ".web";
        } else {
            clickSuffix = ".other";
        }
        String incomeSuffix = ".income";

        //app <-> btn计数
        String btnHashKey = buttonInfo.getAppId() + clickBtnParams.btn_id;
        clickCount(btnHashKey, clickSuffix, incomeSuffix, clickBtnParams.price);

        //app <-> consumer_app计数
        String hashKey = String.valueOf(buttonInfo.getAppId()) + buttonInfo.getConsumerAppId();
        clickCount(hashKey, clickSuffix, incomeSuffix, clickBtnParams.price);

        ApiLogger.info("");
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
