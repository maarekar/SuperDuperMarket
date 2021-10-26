package servlets;

import api.ISuperDuperMarket;
import api.users.MarketWithZones;
import api.users.Transaction;
import api.users.User;
import api.users.UserManager;
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
import java.text.SimpleDateFormat;

@WebServlet(name = "AddFeedbackServlet", urlPatterns = {"/addFeedback"})
public class AddFeedbackServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text");
        PrintWriter out = response.getWriter();
        try{
            UserManager userManager = ServletUtils.getUserManager(getServletContext());
            MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
            ISuperDuperMarket zone = marketZones.getZone(request.getParameter(Constants.ZONE_NAME));
            String storeID = request.getParameter(Constants.STORE_LIST);
            String dateString = request.getParameter(Constants.DATE);
            String username = SessionUtils.getUsername(request);
            String grade =  request.getParameter(Constants.RATE);
            String message =  request.getParameter(Constants.MESSAGE);



            synchronized (getServletContext()) {
                zone.addFeedback(Integer.parseInt(storeID), username, dateString, Integer.parseInt(grade),message, userManager );
            }


            out.print("Feedback was added succesfully.");
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
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
