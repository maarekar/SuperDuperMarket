package servlets;

import api.users.Transaction;
import api.users.User;
import api.users.UserManager;
import constants.Constants;
import utils.ServletUtils;
import Enum.TransactionType;
import utils.SessionUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static constants.Constants.CREDIT;
import static constants.Constants.USERNAME;

@WebServlet(name = "ChargeCreditServlet", urlPatterns = {"/chargeCredit"})
public class ChargeCreditServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text");
        PrintWriter out = response.getWriter();
        try{
            UserManager userManager = ServletUtils.getUserManager(getServletContext());
            String credit = request.getParameter(Constants.CREDIT);
            String dateString = request.getParameter(Constants.DATE);
            String username = SessionUtils.getUsername(request);
            User user = userManager.getUser(username);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //Date date = format.parse(dateString);
            user.getBalance().addTransaction(new Transaction(Double.parseDouble(credit), TransactionType.CHARGE, dateString));

            out.print("Credit was charged succesfully.");
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
