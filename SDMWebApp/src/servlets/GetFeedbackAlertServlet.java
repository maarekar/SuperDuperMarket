package servlets;


import api.users.NewFeedbackAlert;
import api.users.NewStoreAlert;
import api.users.UserManager;
import com.google.gson.Gson;
import constants.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetFeedbackAlertServlet", urlPatterns = {"/feedback-alert"})
public class GetFeedbackAlertServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");


        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String feedbackVersionString = request.getParameter(Constants.VERSION);

        int currentFeedbackVersion= Integer.parseInt(feedbackVersionString);

        int feedbacksVersion = 0;
        List<NewFeedbackAlert> feedbacksAlertEntries;
        synchronized (getServletContext()) {
            feedbacksVersion = userManager.getUser(SessionUtils.getUsername(request)).getFeedbackVersion();
            feedbacksAlertEntries = userManager.getUser(SessionUtils.getUsername(request)).getFeedbacksAlerts(currentFeedbackVersion);
        }

        // log and create the response json string
        AlertAndVersion aav = new AlertAndVersion(feedbacksAlertEntries, feedbacksVersion);
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(aav);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

    private static class AlertAndVersion {

        final private List<NewFeedbackAlert> entries;
        final private int version;

        public AlertAndVersion(List<NewFeedbackAlert> entries, int version) {
            this.entries = entries;
            this.version = version;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
