package cc.lkme.api.test.resources;

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
    @Path("/getJson.json")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getTestJson(@QueryParam("input") String param) {
        return "{\"hello\":\"linkedME\",\"input\":\"" + param + "\"}";
    }

    @Path("/update.json")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public String update(@FormParam("input") String param) {
        return "{\"hello\":\"linkedME\",\"data\":\"" + param + "\"}";
    }

    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        
        //get request
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
    
    public static final ThreadPoolExecutor DOWN_STREAM_REDIS_POOL = new TraceableThreadExecutor(50, 50, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
    @Autowired
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
