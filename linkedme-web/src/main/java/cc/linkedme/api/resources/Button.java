package cc.linkedme.api.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.params.ButtonParams;
import cc.linkedme.service.webapi.BtnService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
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

@Path("btn")
@Component
public class Button {
    @Resource
    BtnService btnService;

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createApp(ButtonParams buttonParams, @Context HttpServletRequest request) {
        if (buttonParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }
        if (buttonParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "user_id <= 0");
        }
        if(buttonParams.consumer_app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "consumer_app_id <= 0");
        }
        if (Strings.isNullOrEmpty(buttonParams.getButton_name())) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "button_name is null");
        }

        String btnId = btnService.createBtn(buttonParams);
        JsonBuilder json = new JsonBuilder();
        json.append("button_id", btnId);
        return json.flip().toString();
    }

    @Path("/get_btn_info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBtns(@QueryParam("user_id") long user_id,
                          @QueryParam("app_id") long app_id,
                          @QueryParam("button_id") String button_id) {

        if (user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "user_id <= 0");
        }

        if (app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }

        ButtonInfo btnInfo = btnService.getBtnInfo(button_id);
        if(btnInfo != null) {
            return btnInfo.toJson();
        }
        return "{}";
    }

    @Path("/get_btns")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBtns(@QueryParam("user_id") long user_id,
                          @QueryParam("app_id") long app_id) {

        if (user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "user_id <= 0");
        }

        if (app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }

        List<ButtonInfo> btns = btnService.getButtons(app_id);
        List<String> btnJsons = new ArrayList<String>(btns.size());
        for (ButtonInfo btn : btns) {
            if (btn != null) {
                btnJsons.add(btn.toJson());
            }
        }

        return new StringBuilder().append("[").append(StringUtils.join(btnJsons, ",")).append("]").toString();
    }

    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteBtn(ButtonParams buttonParams, @Context HttpServletRequest request) {

        if (buttonParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "user_id <= 0");
        }

        if (buttonParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }
        if(btnService.deleteButton(buttonParams.button_id)) {
            return "{\"ret\" : \"true\"}";
        } else {
            return "{\"ret\" : \"false\"}";
        }
    }

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateBtn(ButtonParams buttonParams, @Context HttpServletRequest request) {
        if (buttonParams.app_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "app_id <= 0");
        }
        if (buttonParams.user_id <= 0) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "user_id <= 0");
        }
        if (Strings.isNullOrEmpty(buttonParams.getButton_name())) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "button_name is null");
        }
        if (Strings.isNullOrEmpty(buttonParams.getButton_id())) {
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "button_id is null");
        }

        if(btnService.updateButtonByBtnId(buttonParams)) {
            return "{\"ret\" : \"true\"}";
        } else {
            return "{\"ret\" : \"false\"}";
        }
    }

}
