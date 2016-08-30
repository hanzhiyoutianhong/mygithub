package cc.linkedme.servlet.demoh5;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {
    protected static final Long REDIS_PERMANENT_EXPIRE_TIME = 2 * 7 * 24 * 3600L;
    protected static final Long REDIS_TEMPORARY_EXPIRE_TIME = 2 * 3600L;

    protected String className;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            execute(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }

    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public abstract <T> T validateParamsAndBuild(HttpServletRequest request);

    public void output(HttpServletResponse response, String result) throws IOException {
        response.getWriter().append(result);
    }
}
