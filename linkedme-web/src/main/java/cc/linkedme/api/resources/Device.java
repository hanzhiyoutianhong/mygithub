package cc.linkedme.api.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.data.model.DeviceInfo;
import cc.linkedme.service.webapi.DeviceService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by LinkedME07 on 16/7/17.
 */

@Path("device")
@Component
public class Device {

    @Resource
    private DeviceService deviceService;

    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String AddDevice(@FormParam("app_id") long appId, @FormParam("device_id") String deviceId,
            @FormParam("device_name") String deviceName, @FormParam("platform") Integer platform,
            @FormParam("device_info") String description, @Context HttpServletRequest request) {

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAppId(appId);
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setDeviceName(deviceName);
        deviceInfo.setPlatform(platform);
        deviceInfo.setDescription(description);

        checkParams(appId, new String[] {deviceId});

        if (platform == null || platform < 0 || platform > 1) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "platform is invalid");
        }

        Integer result = deviceService.addDevice(deviceInfo);
        return resultToJson(result);

    }

    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDevice(@FormParam("app_id") long appId, @FormParam("device_id") String[] deviceId,
            @Context HttpServletRequest request) {
        checkParams(appId, deviceId);
        Integer result = deviceService.delDevice(appId, deviceId);
        return resultToJson(result);
    }

    @Path("/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateDevice(@FormParam("app_id") long appId, @FormParam("device_id") String deviceId,
            @FormParam("device_name") String deviceName, @FormParam("platform") Integer platform,
            @FormParam("device_info") String description, @Context HttpServletRequest request) {

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setAppId(appId);
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setDeviceName(deviceName);
        deviceInfo.setPlatform(platform);
        deviceInfo.setDescription(description);

        checkParams(appId, new String[] {deviceId});

        if (platform == null || platform < 0 || platform > 1) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "platform is invalid");
        }

        Integer result = deviceService.updateDevice(deviceInfo);
        return resultToJson(result);

    }

    @Path("/query")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getDevice(@FormParam("app_id") long appId, @FormParam("device_id") String deviceId, @Context HttpServletRequest request) {
        checkParams(appId, new String[] {deviceId});
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
    public String getDevices(@FormParam("app_id") long appId, @Context HttpServletRequest request) {
        if (appId <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "invalid app_id");
        }
        List<DeviceInfo> deviceInfos = deviceService.listDevice(appId);

        if (CollectionUtils.isEmpty(deviceInfos)) {
            return "[]";
        }
        JSONArray jsonarray = JSONArray.fromObject(deviceInfos);
        return jsonarray.toString();
    }


    private void checkParams(long appId, String[] deviceIds) {
        if (appId <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }

        for (String deviceId : deviceIds) {
            if (Strings.isNullOrEmpty(deviceId)) {
                throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "device_id is null");
            }
        }
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

}
