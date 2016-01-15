package sdk;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("v1")
@Component
public class InstallResources {
    @Path("/install")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getTestJson(@QueryParam("ad_tracking_enabled") String adTrackingEnabled,
                              @QueryParam("app_version") String AppVersion,
                              @QueryParam("linkedMe_key") String linkedMeKey,
                              @QueryParam("brand") String brand,
                              @QueryParam("carrier") String carrier,
                              @QueryParam("debug") String debug,
                              @QueryParam("hardware_id") String hardwareId,
                              @QueryParam("ios_bundle_id") String iOSBundleId,
                              @QueryParam("is_hardware_id_real") String isHardwareIdReal,
                              @QueryParam("is_referable") String isReferable,
                              @QueryParam("os") String os,
                              @QueryParam("osVersion") String osVersion,
                              @QueryParam("retryNumber") String retryNumber,
                              @QueryParam("screenHeight") String screenHeight,
                              @QueryParam("screenWidth") String screenWidth,
                              @QueryParam("sdk") String sdk,
                              @QueryParam("update") String update,
                              @QueryParam("uri_scheme") String uri_scheme,
                              @QueryParam("ios_team_id") String iOSTeamId,
                              @QueryParam("universal_link_url") String universalLinkUrl,
                              @QueryParam("spotlight_identifier") String spotlightIdentifier,
                              @QueryParam("lat_val") String latVal,
                              @QueryParam("wifi") String wifi,
                              @QueryParam("has_nfc") String hasNFC,
                              @QueryParam("has_telephone") String hasTelephone,
                              @QueryParam("bluetooth") String bluetooth,
                              @QueryParam("screen_dpi") String screenDpi
                              ) {

        return "{\"hello\":\"linkedME\",\"input\":\"" + wifi + "\"}";
    }

    @Path("/update")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String update(@FormParam("input") String param) {
        return "{\"hello\":\"linkedME\",\"data\":\"" + param + "\"}";
    }

    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        
        //get request
        HttpMethod method = new GetMethod("http://localhost:8080/v1/install?wifi=LinkedME");
        try {
            client.executeMethod(method);
            System.out.println(method.getStatusLine());
            System.out.println(method.getResponseBodyAsString());
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        method.releaseConnection();

        //post
        PostMethod postMethod = new PostMethod("http://localhost:8080/test/update");
        try {
            postMethod.addParameter("input", "send");
            client.executeMethod(postMethod);
            String response = new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
            System.out.println(postMethod.getStatusLine());
            System.out.println(response);
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        postMethod.releaseConnection();

    }
}
