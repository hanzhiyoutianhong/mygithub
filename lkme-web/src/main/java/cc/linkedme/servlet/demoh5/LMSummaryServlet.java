package cc.linkedme.servlet.demoh5;

import cc.linkedme.servlet.AbstractServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by qipo on 15/10/8.
 */
public class LMSummaryServlet extends AbstractServlet {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/demojsp/intro.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String validateParamsAndBuild(HttpServletRequest request) {
        return null;
    }
}
