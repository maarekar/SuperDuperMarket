package servlets;

import Exceptions.MessageException;
import api.ISuperDuperMarket;
import api.SuperDuperMarketLogic;
import api.users.MarketWithZones;
import constants.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadServlet extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text");
        PrintWriter out = response.getWriter();

        MarketWithZones marketZones = ServletUtils.getMarketZone(getServletContext());
        String uploaderNameFromSession = SessionUtils.getUsername(request);
        Part part = request.getPart(Constants.ZONE_FILE);

        synchronized (this) {
            ISuperDuperMarket newZone = new SuperDuperMarketLogic();
            if (part != null) {
                try{
                    newZone.loadFile(part.getInputStream(), uploaderNameFromSession);
                    if (marketZones.isZoneExists(newZone.getZoneName())) {
                        out.print("A zone with this name already exists.");
                    }
                    else{
                        newZone.setOwnerName(uploaderNameFromSession);
                        marketZones.addZone(newZone.getZoneName(), newZone);
                        out.print("Zone uploaded successfully.");
                    }
                } catch (MessageException e){
                    out.print(e.getMessage());
                } catch (JAXBException e) {
                    out.print("Error parsing zone.");
                }finally {
                    out.flush();
                }
            }
            else {
                out.print("File cannot be empty.");
                out.flush();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


}


