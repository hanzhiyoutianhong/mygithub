package cc.linkedme.servlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Created by LinkedME01 on 16/4/1.
 */
public class AppleFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ShardingSupportHash<JedisPort> linkedmeKeyShardingSupport;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppleFileServlet() {
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
        JSONObject appLinksJson = new JSONObject();
        appLinksJson.put("apps", new JSONArray());
        JSONArray appDetailsJson = new JSONArray();
        JedisPort client = linkedmeKeyShardingSupport.getClient(0);
        Map<String, String> appDetails = client.hgetAll("applinks.ios");
        if (CollectionUtils.isEmpty(appDetails)) {
            appLinksJson.put("details", appDetailsJson);
        } else {
            for (Map.Entry<String, String> entry : appDetails.entrySet()) {
                if (entry == null) {
                    continue;
                }
                JSONObject appJson = new JSONObject();
                JSONArray pathJson = new JSONArray();
                pathJson.add("/" + entry.getKey() + "/*");
                appJson.put("appID", entry.getValue());
                appJson.put("paths", pathJson);
                appDetailsJson.add(appJson);
                appLinksJson.put("details", appDetailsJson);
            }

        }
        JSONObject rootJson = new JSONObject();
        rootJson.put("applinks", appLinksJson);
        response.setContentType("application/json");
        response.getWriter().write(rootJson.toString());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
