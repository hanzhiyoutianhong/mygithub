package cc.linkedme.commons.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by qipo on 15/10/15.
 */
public class LogUtils {

    public static long MEMORYCACHE_FIRE_TIME = 200; //MC操作超时
    public static long DATABASE_FIRE_TIME = 500; //DB操作超时
    public static long REDIS_FIRE_TIME = 300; //Redis操作超时


    /**
     * 格式化request和response
     *
     * @param request
     * @param response
     * @return
     */

    public static JSONObject logFormat(JSONObject request, JSONObject response) {
        JSONObject resultJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        String requestStr = "request";
        String responseStr = "response";
        jsonObject.put(requestStr, request);
        jsonObject.put(responseStr, response);
        resultJson.put("results", jsonObject.toJSONString());
        return resultJson;
    }

    public static JSONObject logFormat(JSONArray request, JSONArray response) {
        JSONObject resultJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        String requestStr = "request";
        String responseStr = "response";
        jsonObject.put(requestStr, request);
        jsonObject.put(responseStr, response);
        resultJson.put("results", jsonObject.toJSONString());
        return resultJson;
    }


}
