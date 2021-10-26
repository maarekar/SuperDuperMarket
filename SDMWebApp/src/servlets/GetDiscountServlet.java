package servlets;

import api.DTODiscount;
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

@WebServlet(name = "GetDiscountServlet", urlPatterns = {"/discounts"})
public class GetDiscountServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
            ISuperDuperMarket zone = marketZones.getZone(request.getParameter(Constants.ZONE_NAME));
            String itemsMap = request.getParameter(Constants.ITEMS_CART);
            String storesString = request.getParameter(Constants.STORE_LIST);
            String mode = request.getParameter(Constants.MODE);

            Type empMapType2 = new TypeToken<Map<Integer, DTOItem>>() {}.getType();

            Map<Integer, DTOItem> items = gson.fromJson(itemsMap, empMapType2);



            List<DTODiscount> discounts;
            if(mode.equals("1")){
                Type empMapType1 = new TypeToken<List<DTOStore>>() {}.getType();

                List<DTOStore> stores = gson.fromJson(storesString, empMapType1);
                 discounts = new ArrayList<>(zone.getDiscounts(items, stores).values());

            }
            else{
                DTOStore store = gson.fromJson(storesString, DTOStore.class);
                 discounts = new ArrayList<>(zone.getDiscounts(items, store).values());
            }





            String json = gson.toJson(discounts);
            out.println(json);
            out.flush();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private class Stam{
        List<DTOStore> stores;
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
