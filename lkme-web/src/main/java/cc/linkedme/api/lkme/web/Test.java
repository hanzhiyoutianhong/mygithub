package cc.linkedme.api.lkme.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cc.linkedme.commons.switcher.Switcher;
import cc.linkedme.commons.switcher.SwitcherManagerFactoryLoader;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupport;
import cc.linkedme.commons.thread.ExecutorServiceUtil;
import cc.linkedme.commons.thread.TraceableThreadExecutor;

@Path("test")
@Component
public class Test {

    private static Switcher testSwitcher =
            SwitcherManagerFactoryLoader.getSwitcherManagerFactory().getSwitcherManager().registerSwitcher("linkedme.test.enable", true);
    @Path("/tomcatTest")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String linkedmeTest(){
        return "lkme-web tomcat-test!";
    }

    @Path("/getJson.json")
    @GET
    @Produces({"application/json"})
    public String getTestJson(@QueryParam("input") String param) {
        return "{\"hello\":\"linkedME\",\"input\":\"" + param + "\"}";
    }

    @Path("/update.json")
    @POST
    @Produces({"application/json"})
    public String update(@FormParam("input") String param) {
        return "{\"hello\":\"linkedME\",\"data\":\"" + param + "\"}";
    }

    @Path("install")
    @POST
    @Produces({"application/json"})
    public String getInstall(@FormParam("device_id") String device_id, @FormParam("device_type") int device_type, @FormParam("device_brand") String device_brand, @FormParam("has_bluetooth") boolean has_bluetooth, @FormParam("has_nfc") boolean has_nfc, @FormParam("has_sin") boolean has_sin, @FormParam("os") String os, @FormParam("os_version") String os_version, @FormParam("screen_dpi") int screen_dpi, @FormParam("screen_height") int screen_height, @FormParam("screen_width") int screen_width, @FormParam("is_wifi") boolean is_wifi, @FormParam("is_referrable") boolean is_referrable, @FormParam("is_debug") boolean is_debug, @FormParam("lat_val") int lat_val, @FormParam("carrier") String carrier, @FormParam("app_version") String app_version, @FormParam("sdk_update") int sdk_update, @FormParam("sdk_version") String sdk_version, @FormParam("ios_team_id") String ios_team_id, @FormParam("ios_bundle_id") String ios_bundle_id, @FormParam("retry_times") int retry_times, @FormParam("linkedme_key") String linkedme_key, @FormParam("sign") String sign) {
        return "{\"session_id\":\"234847414514735930\",\"identity_id\":\"234847414580711796\",\"device_fingerprint_id\":\"234847414538808942\",\"browser_fingerprint_id\":null, \"link\":\"https://lkme.cc/a/key_test_hdcBLUy1xZ1JD0tKg7qrLcgirFmPPVJc?%24identity_id=234847414580711796\",\"params\":{},\"is_first_session\":true,\"clicked_linkedme_link\":true}";
    }

    @Path("open")
    @POST
    @Produces({"application/json"})
    public String getOpen(@FormParam("device_fingerprint_id") String device_fingerprint_id, @FormParam("identity_id") String identity_id, @FormParam("is_referrable") boolean is_referrable, @FormParam("app_version") String app_version, @FormParam("os_version") String os_version, @FormParam("sdk_update") int sdk_update, @FormParam("os") String os, @FormParam("is_debug") boolean is_debug, @FormParam("lat_val") int lat_val, @FormParam("sdk_version") String sdk_version, @FormParam("retry_times") int retry_times, @FormParam("linkedme_key") String linkedme_key, @FormParam("sign") String sign) {
        return "{\"session_id\":\"234847414514735930\",\"identity_id\":\"234847414580711796\",\"device_fingerprint_id\":\"234847414538808942\",\"browser_fingerprint_id\":null,\"link\":\"https://lkme.cc/a/key_test_hdcBLUy1xZ1JD0tKg7qrLcgirFmPPVJc?%24identity_id=234847414580711796\",\"params\":{},\"is_first_session\":true,\"clicked_linkedme_link\":true}";
    }

    @Path("url")
    @POST
    @Produces({"application/json"})
    public String getUrl(@FormParam("identity_id") String identity_id) {
        return "{\"url\":\"https://lkme.cc/Ojqd/C2nn7wMvr\"}";
    }

    @Path("close")
    @POST
    @Produces({"application/json"})
    public String getClose(@FormParam("device_fingerprint_id") String device_fingerprint_id, @FormParam("identity_id") String identity_id, @FormParam("session_id") String session_id, @FormParam("sdk_version") String sdk_version, @FormParam("retry_times") int retry_times, @FormParam("linkedme_key") String linkedme_key, @FormParam("sign") String sign) {
        return "{\"statusCode\":200}";
    }

    @Path("profile")
    @POST
    @Produces({"application/json"})
    public String getProfile( @FormParam("identity_id") String identity_id,
                              @FormParam("device_fingerprint_id") String device_fingerprint_id,
                              @FormParam("session_id") String session_id,
                              @FormParam("identity") String identity,
                              @FormParam("sdk_version") String sdk_version,
                              @FormParam("retry_times") int retry_times,
                              @FormParam("linkedme_key") String linkedme_key )
    {
        return "{\"session_id\":\"236130579389750418\",\"link_click_id\":\"189949600243664477\",\"identity_id\":\"189922892383798607\",\"link\":\"https://lkme.cc/a/key_test_hdcBLUy1xZ1JD0tKg7qrLcgirFmPPVJc?%24identity_id=189922892383798607\"}";
    }

    @Path("logout")
    @POST
    @Produces({"application/json"})
    public String getLogout( @FormParam("identity_id") String identity_id,
                             @FormParam("device_fingerprint_id") String device_fingerprint_id,
                             @FormParam("session_id") String session_id,
                             @FormParam("sdk_version") String sdk_version,
                             @FormParam("retry_times") int retry_times,
                             @FormParam("linkedme_key") String linkedme_key )
    {
        return "{\"session_id\":\"236136491359116418\",\"identity_id\":\"236136491259036703\",\"link\":\"https://lkme.cc/a/key_test_hdcBLUy1xZ1JD0tKg7qrLcgirFmPPVJc?%24identity_id=236136491259036703\"}";
    }

    public static void main(String[] args) {
        HttpClient client = new HttpClient();

        // get request
        HttpMethod method = new GetMethod("http://localhost:8080/test/getJson?input=receive");
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

        // post
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

    // Redis test code

    public static final ThreadPoolExecutor DOWN_STREAM_REDIS_POOL = new TraceableThreadExecutor(50, 50, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());

    private ShardingSupport<JedisPort> mgetShardingSupport;

    public ShardingSupport<JedisPort> getMgetShardingSupport() {
        return mgetShardingSupport;
    }

    public void setMgetShardingSupport(ShardingSupport<JedisPort> mgetShardingSupport) {
        this.mgetShardingSupport = mgetShardingSupport;
    }

    public boolean set(long key, String value) {
        int db = mgetShardingSupport.getDbTable(key).getDb();
        JedisPort client = mgetShardingSupport.getClientByDb(db);
        return client.set(String.valueOf(key), value);
    }

    public Map<Long, String> mutilGet(long[] uids) {
        final ConcurrentHashMap<Long, String> allUidValues = new ConcurrentHashMap<Long, String>();
        try {
            final Map<Integer, List<Long>> dbUidsMap = mgetShardingSupport.getDbSharding(uids);
            List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
            for (Map.Entry<Integer, List<Long>> entry : dbUidsMap.entrySet()) {
                final Integer db = entry.getKey();
                final List<Long> uidList = entry.getValue();
                final List<String> uidStrList = new ArrayList<String>();
                for (int i = 0; i < uidList.size(); i++) {
                    uidStrList.add(String.valueOf(uidList.get(i)));
                }
                Callable<Boolean> task = new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        JedisPort client = mgetShardingSupport.getClientByDb(db);
                        List<String> values = client.mget(uidStrList);

                        for (int i = 0; i < uidList.size(); i++) {
                            if (values.get(i) != null) {
                                allUidValues.put(uidList.get(i), values.get(i));
                            }
                        }
                        return true;
                    }
                };
                tasks.add(task);
            }
            ExecutorServiceUtil.invokes(DOWN_STREAM_REDIS_POOL, tasks, 5000, TimeUnit.MILLISECONDS, true);
        } catch (Exception e) {
            System.out.println("Exception");
        }

        return allUidValues;
    }
}
