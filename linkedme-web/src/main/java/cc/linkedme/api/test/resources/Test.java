package cc.linkedme.api.test.resources;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("test")
@Component
public class Test {
    @Path("/getJson")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getTestJson(@QueryParam("input") String param) {
        return "{\"hello\":\"linkedME\",\"input\":\"" + param + "\"}";
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
        org.apache.commons.httpclient.HttpMethod method = new GetMethod("http://localhost:8080/test/getJson?input=receive");
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
