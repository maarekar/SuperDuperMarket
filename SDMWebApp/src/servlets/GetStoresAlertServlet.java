package servlets;

import api.DTOFeedback;
import api.ISuperDuperMarket;
import api.users.MarketWithZones;
import api.users.NewOrderAlert;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetStoresAlertServlet", urlPatterns = {"/stores-alert"})
public class GetStoresAlertServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");


        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String orderVersionString = request.getParameter(Constants.VERSION);

        int currentOrderVersion= Integer.parseInt(orderVersionString);

        int ordersVersion = 0;
        List<NewStoreAlert> ordersAlertEntries;
        synchronized (getServletContext()) {
            ordersVersion = userManager.getUser(SessionUtils.getUsername(request)).getNewStoreVersion();
            ordersAlertEntries = userManager.getUser(SessionUtils.getUsername(request)).getNewStoresAlertEntries(currentOrderVersion);
        }

        // log and create the response json string
        AlertAndVersion aav = new AlertAndVersion(ordersAlertEntries, ordersVersion);
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(aav);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

    private static class AlertAndVersion {

        final private List<NewStoreAlert> entries;
        final private int version;

        public AlertAndVersion(List<NewStoreAlert> entries, int version) {
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
