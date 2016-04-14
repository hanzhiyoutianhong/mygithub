package cc.linkedme.api.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.params.ButtonParams;
import cc.linkedme.service.webapi.ConsumerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/4/7.
 */

@Path("consumer")
@Component
public class ConsumerApp {
    @Resource
    ConsumerService consumerService;

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createApp(ButtonParams buttonParams, @Context HttpServletRequest request) {
        return null;
    }

    @Path("/get_app_info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAppInfo(@QueryParam("user_id") long user_id,
                             @QueryParam("consumer_app_id") long app_id) {
        if (app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }

        ConsumerAppInfo consumerApp = consumerService.getConsumerAppInfo(app_id);
        if(consumerApp != null) {
            return consumerApp.toJson();
        }
        throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app not exist, app_id=" + app_id);
    }

    @Path("/get_apps")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getConsumerApps(@QueryParam("user_id") long user_id,
                                  @QueryParam("app_id") long app_id) {
        List<ConsumerAppInfo> consumerAppInfoList = consumerService.getAllConsumerApp();

        List<String> consumerAppInfoJsons = new ArrayList<String>(consumerAppInfoList.size());
        for (ConsumerAppInfo consumerAppInfo : consumerAppInfoList) {
            if (consumerAppInfo != null) {
                consumerAppInfoJsons.add(consumerAppInfo.toJson());
            }
        }

        return new StringBuilder().append("[").append(StringUtils.join(consumerAppInfoJsons, ",")).append("]").toString();
    }
}
