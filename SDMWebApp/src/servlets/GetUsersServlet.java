package servlets;

import api.DTOUser;
import api.users.User;
import com.google.gson.Gson;


import constants.Constants;
import utils.ServletUtils;
import api.users.UserManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import static constants.Constants.USERNAME;

@WebServlet(name = "GetUsersServlet", urlPatterns = {"/users"})
public class GetUsersServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            UserManager userManager = ServletUtils.getUserManager(getServletContext());
            List<DTOUser> usersList;
            synchronized (getServletContext()) {
                 usersList = userManager.getUsersData();
            }

            String json = gson.toJson(usersList);
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
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String userTypeFromParameter = request.getSession().getAttribute(Constants.ACCOUNT_TYPE).toString();
            String json = gson.toJson(userTypeFromParameter);
            out.println(json);
            out.flush();
        }
    }
}
