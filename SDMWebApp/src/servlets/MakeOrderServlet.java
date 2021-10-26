package servlets;

import api.DTOOffer;
import api.DTOStore;
import api.ISuperDuperMarket;
import api.users.MarketWithZones;
import api.users.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "MakeOrderServlet", urlPatterns = {"/make-order"})
public class MakeOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
            ISuperDuperMarket zone = marketZones.getZone(request.getParameter(Constants.ZONE_NAME));
            String storesString = request.getParameter(Constants.STORE_LIST);
            String date = request.getParameter(Constants.DATE);
            String x = request.getParameter(Constants.X_LOCATION);
            String y = request.getParameter(Constants.Y_LOCATION);


            Type empMapType1 = new TypeToken<List<DTOStore>>() {}.getType();

            List<DTOStore> stores = gson.fromJson(storesString, empMapType1);


            String usernameFromSession = SessionUtils.getUsername(request);
            UserManager userManager = ServletUtils.getUserManager(getServletContext());

            synchronized (getServletContext()) {
                zone.makeOrder(stores, usernameFromSession,date, userManager, Integer.parseInt(x), Integer.parseInt(y));
            }

            out.println();
            out.flush();
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
