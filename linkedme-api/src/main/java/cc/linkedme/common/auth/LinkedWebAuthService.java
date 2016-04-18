package cc.linkedme.common.auth;

import cc.linkedme.dao.webapi.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by puyangsky on 2016/4/17.
 */
@Service
public class LinkedWebAuthService {
    @Resource
    private UserDao userDao;

    public boolean doAuth(ServletRequest servletRequest, String user_id, String token) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI();
        //登录、注册、下载demo不需要token
        if (path.contains("/user/login") || path.contains("/user/register") || path.contains("/v1/request_demo")) {
            return true;
        }
        //从数据库里取token，若跟前台发送来的token相同则放行，否则返回一个提示拒绝请求的json
        if (userDao.getToken(user_id) != null)
            return token.equals(userDao.getToken(user_id));
        else
            return false;
//        String token = "";
//        String email = "";
//        System.out.println("method: " + request.getMethod());
//        if (request.getMethod().equalsIgnoreCase("get")) {
//            token = request.getParameter("token");
//            email = request.getParameter("email");
//        }else if (request.getMethod().equalsIgnoreCase("post")) {
//            try {
//                BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
//                StringBuffer sb = new StringBuffer("");
//                String tmp;
//                while ((tmp = br.readLine()) != null) {
//                    sb.append(tmp);
//                }
//                br.reset();
//                br.close();
//                String json = sb.toString();
//                if (!json.equals("")) {
//                    JSONObject jsonObject = JSONObject.parseObject(json);
//                    token = jsonObject.get("token").toString();
//                    email = jsonObject.get("email").toString();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("token :" + token);
//        System.out.println("email :" + email);

    }
}
