package cc.lkme.api.test.resources;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

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
        HttpMethod method = new GetMethod("http://localhost:8080/three/test/getJson?input=receive");
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
        PostMethod postMethod = new PostMethod("http://localhost:8080/three/test/update");
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
