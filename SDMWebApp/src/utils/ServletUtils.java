package utils;


/*import engine.chat.ChatManager;
import engine.users.UserManager;*/

import api.chat.ChatManager;
import api.users.MarketWithZones;
import api.users.UserManager;
import constants.Constants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/*import static chat.constants.Constants.INT_PARAMETER_ERROR;*/

public class ServletUtils {

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    private static final String MARKET_ZONE_ATTRIBUTE_NAME = "marketZone";
    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

    /*
    Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
    the actual fetch of them is remained un-synchronized for performance POV
     */

    private static final Object userManagerLock = new Object();
    private static final Object marketZoneLock = new Object();
    private static final Object chatManagerLock = new Object();

    public static UserManager getUserManager(ServletContext servletContext) {

        synchronized (userManagerLock) {
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
            }
        }
        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static MarketWithZones getMarketZone(ServletContext servletContext) {

        synchronized (marketZoneLock) {
            if (servletContext.getAttribute(MARKET_ZONE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(MARKET_ZONE_ATTRIBUTE_NAME, new MarketWithZones());
            }
        }
        return (MarketWithZones) servletContext.getAttribute(MARKET_ZONE_ATTRIBUTE_NAME);
    }

    public static ChatManager getChatManager(ServletContext servletContext) {
        synchronized (chatManagerLock) {
            if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
            }
        }
        return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
    }

    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return Constants.INT_PARAMETER_ERROR;
    }
}

