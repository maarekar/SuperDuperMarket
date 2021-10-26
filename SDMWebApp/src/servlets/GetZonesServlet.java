package servlets;

import api.DTOUser;
import api.DTOZone;
import api.users.MarketWithZones;
import api.users.UserManager;
import com.google.gson.Gson;
import constants.Constants;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@WebServlet(name = "GetZonesServlet", urlPatterns = {"/zones"})
public class GetZonesServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
            List<DTOZone> zonesList;
            synchronized (getServletContext()) {
                zonesList = marketZones.getAllTheZones();
            }

            String json = gson.toJson(zonesList);
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
