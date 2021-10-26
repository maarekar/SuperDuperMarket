package servlets;


import api.DTOItem;
import api.DTOStore;
import api.ISuperDuperMarket;
import api.users.MarketWithZones;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Constants;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UpdateStoreDataServlet", urlPatterns = {"/static-order"})
public class UpdateStoreDataServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
            ISuperDuperMarket zone = marketZones.getZone(request.getParameter(Constants.ZONE_NAME));
            String itemsMap = request.getParameter(Constants.ITEMS_CART);
            String x = request.getParameter(Constants.X_LOCATION);
            String y = request.getParameter(Constants.Y_LOCATION);
            String storeString = request.getParameter(Constants.STORE_LIST);


            Type empMapType = new TypeToken<Map<Integer, DTOItem>>() {}.getType();

            Map<Integer, DTOItem> items = gson.fromJson(itemsMap, empMapType);

            DTOStore store = gson.fromJson(storeString, DTOStore.class);
            DTOStore newStore;
            synchronized (getServletContext()) {
                 newStore = zone.getStoreForOrder(items, store, Integer.parseInt(x), Integer.parseInt(y));
            }



            String json = gson.toJson(newStore);
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
