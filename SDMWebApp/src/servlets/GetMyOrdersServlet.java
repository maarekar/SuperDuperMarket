package servlets;

import api.DTOItem;
import api.DTOOrder;
import api.ISuperDuperMarket;
import api.users.MarketWithZones;
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

@WebServlet(name = "GetMyOrdersServlet", urlPatterns = {"/my-orders"})
public class GetMyOrdersServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
            ISuperDuperMarket zone = marketZones.getZone(request.getParameter(Constants.ZONE_NAME));

            List<DTOOrder> ordersList = new ArrayList<>(zone.getMyOrders(SessionUtils.getUsername(request)));
            String json;
            if(ordersList.size() == 0){
                json = null;
            } else{
                json = gson.toJson(ordersList);
            }

            out.println(json);
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
