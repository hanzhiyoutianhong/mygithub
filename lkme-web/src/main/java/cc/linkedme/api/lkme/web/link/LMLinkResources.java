package cc.linkedme.api.lkme.web.link;

import cc.linkedme.service.LMLinkService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Component
public class LMLinkResources {

    private LMLinkService lmLinkService;

    public LMLinkService getLmLinkService() {
        return lmLinkService;
    }

    public void setLmLinkService(LMLinkService lmLinkService) {
        this.lmLinkService = lmLinkService;
    }

    @Path("/l")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String deepLink(@QueryParam("link_click_id") String linkClickId,
                           @Context HttpServletRequest request) {

        if (Strings.isNullOrEmpty(linkClickId)) {

        }

        String result = lmLinkService.deepLink(linkClickId);
        return result;

    }


}
