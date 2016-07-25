package cc.linkedme.api.resources;

import cc.linkedme.data.model.DeviceInfo;
import cc.linkedme.service.webapi.DeviceService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by LinkedME07 on 16/7/17.
 */

@Path("device")
@Component
public class Device {

    @Autowired
    private DeviceService deviceService;

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String AddDevice(@FormParam("app_id") long appId, @FormParam("device_id") String deviceId,
            @FormParam("device_name") String deviceName, @FormParam("platform") int platform, @FormParam("device_info") String description,
            @Context HttpServletRequest request) {

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAppId(appId);
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setDeviceName(deviceName);
        deviceInfo.setPlatform(platform);
        deviceInfo.setDescription(description);

        JSONArray jsonArray = checkParams(appId, new String[] {deviceId});

        if (platform < 0 || platform > 1) {
            jsonArray.add(getErrorMsg("40001", "platform", "platform 只能是0或者1"));
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        Integer result = deviceService.addDevice(deviceInfo);
        return resultToJson(result);

    }

    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDevice(@FormParam("app_id") long appId, @FormParam("device_id") String deviceId,
            @Context HttpServletRequest request) {


        JSONArray jsonArray = new JSONArray();

        if (Strings.isNullOrEmpty(deviceId)) {
            jsonArray.add(getErrorMsg("40001", "device_id", "device_id 为空"));
        }

        if (appId <= 0) {
            jsonArray.add(getErrorMsg("40001", "app_id", "app_id 小于零"));
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        String[] deviceIds = deviceId.split(",");

        Integer result = deviceService.delDevice(appId, deviceIds);
        return resultToJson(result);
    }

    @Path("/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateDevice(@FormParam("app_id") long appId, @FormParam("device_id") String deviceId,
            @FormParam("device_name") String deviceName, @FormParam("platform") int platform, @FormParam("device_info") String description,
            @Context HttpServletRequest request) {

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAppId(appId);
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setDeviceName(deviceName);
        deviceInfo.setPlatform(platform);
        deviceInfo.setDescription(description);

        JSONArray jsonArray = checkParams(appId, new String[] {deviceId});

        if (platform < 0 || platform > 1) {
            jsonArray.add(getErrorMsg("40001", "platform", "platform 只能是0或者1"));
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        Integer result = deviceService.updateDevice(deviceInfo);
        return resultToJson(result);

    }

    @Path("/query")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDevice(@QueryParam("app_id") long appId, @QueryParam("device_id") String deviceId,
            @Context HttpServletRequest request) {

        JSONArray jsonArray = checkParams(appId, new String[] {deviceId});

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        DeviceInfo deviceInfo = deviceService.getDevice(appId, deviceId);

        if (deviceInfo == null) {
            return "{}";
        }

        JSONObject deviceInfoJson = JSONObject.fromObject(deviceInfo);
        return deviceInfoJson.toString();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDevices(@QueryParam("app_id") long appId, @Context HttpServletRequest request) {

        JSONArray jsonArray = new JSONArray();

        if (appId <= 0) {
            jsonArray.add(getErrorMsg("40001", "app_id", "app_id 小于零"));
        }

        if (jsonArray.size() > 0) {
            return jsonArray.toString();
        }

        List<DeviceInfo> deviceInfos = deviceService.listDevice(appId);

        if (CollectionUtils.isEmpty(deviceInfos)) {
            return "[]";
        }
        JSONArray jsonarray = JSONArray.fromObject(deviceInfos);
        return jsonarray.toString();
    }


    private JSONArray checkParams(long appId, String[] deviceIds) {

        JSONArray jsonArray = new JSONArray();

        if (appId <= 0) {
            jsonArray.add(getErrorMsg("40001", "app_id", "app_id 小于零"));
        }

        for (String deviceId : deviceIds) {
            if (Strings.isNullOrEmpty(deviceId)) {
                jsonArray.add(getErrorMsg("40001", "device_id", "device_id 为空"));
            }
        }
        return jsonArray;
    }

    private String resultToJson(Integer reslut) {

        JSONObject resultJson = new JSONObject();
        if (reslut > 0) {
            resultJson.put("ret", true);
        } else {
            resultJson.put("ret", false);
        }

        return resultJson.toString();
    }

    public JSONObject getErrorMsg(String errCode, String errParam, String errMsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("err_code", errCode);
        jsonObject.put("err_param", errParam);
        jsonObject.put("err_msg", errMsg);
        return jsonObject;
    }

}
