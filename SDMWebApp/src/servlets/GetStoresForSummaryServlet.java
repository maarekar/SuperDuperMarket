package servlets;

import api.DTOItem;
import api.DTOOffer;
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

@WebServlet(name = "GetStoresForSummaryServlet", urlPatterns = {"/summary"})
public class GetStoresForSummaryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
            ISuperDuperMarket zone = marketZones.getZone(request.getParameter(Constants.ZONE_NAME));
            String storesString = request.getParameter(Constants.STORE_LIST);
            String offersString = request.getParameter(Constants.OFFERS);
            String mode = request.getParameter(Constants.MODE);



            Type empMapType2 = new TypeToken<List<DTOOffer>>() {}.getType();

            List<DTOOffer> offers = gson.fromJson(offersString, empMapType2);

            List<DTOStore> storesList;

            synchronized (getServletContext()) {
                if(mode.equals("1")){
                    Type empMapType1 = new TypeToken<List<DTOStore>>() {}.getType();

                    List<DTOStore> stores = gson.fromJson(storesString, empMapType1);
                    storesList = zone.getStoresForSummary(stores, offers);

                }
                else{
                    DTOStore store = gson.fromJson(storesString, DTOStore.class);
                    storesList = zone.getStoresForSummary(store, offers);
                }
            }



            String json = gson.toJson(storesList);
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
