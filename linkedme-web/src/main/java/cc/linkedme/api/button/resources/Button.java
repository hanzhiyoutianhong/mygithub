package cc.linkedme.api.button.resources;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.data.model.params.ButtonParams;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by LinkedME01 on 16/4/7.
 */

@Path("btn")
@Component
public class Button {
    @Path("/create_btn")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createApp(ButtonParams buttonParams, @Context HttpServletRequest request) {

        return null;
    }
}
