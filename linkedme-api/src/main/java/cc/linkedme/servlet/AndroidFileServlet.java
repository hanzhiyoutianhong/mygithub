package cc.linkedme.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.linkedme.commons.util.Base62;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;


/**
 * Created by LinkedME01 on 16/4/1.
 */
public class AndroidFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ShardingSupportHash<JedisPort> linkedmeKeyShardingSupport;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        linkedmeKeyShardingSupport = (ShardingSupportHash) context.getBean("linkedmeKeyShardingSupport");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray appsJson = new JSONArray();
        JedisPort client = linkedmeKeyShardingSupport.getClient(0);
        Map<String, String> appDetails = client.hgetAll("applinks.adr");
        if (!CollectionUtils.isEmpty(appDetails)) {
            for (Map.Entry<String, String> entry : appDetails.entrySet()) {
                JSONArray relationJson = new JSONArray();
                relationJson.add("delegate_permission/common.handle_all_urls");

                JSONObject targetJson = new JSONObject();
                targetJson.put("namespace", "android_app");
                String[] arr = entry.getValue().split("\\|");
                if (arr.length != 2) {
                    continue;
                }
                targetJson.put("package_name", arr[0]);

                JSONArray fingerprints = new JSONArray();
                fingerprints.add(arr[1]);
                targetJson.put("sha256_cert_fingerprints", fingerprints);

                JSONObject appItem = new JSONObject();
                appItem.put("appID", Base62.decode(entry.getKey()));
                appItem.put("relation", relationJson);
                appItem.put("target", targetJson);
                appsJson.add(appItem);
            }
        }

        response.getWriter().write(appsJson.toString());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
