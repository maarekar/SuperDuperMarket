package api;

import Exceptions.*;
import api.users.UserManager;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISuperDuperMarket {
    void loadFile(InputStream path, String ownerName) throws MessageException, JAXBException, FileNotFoundException;
    Map<Integer, DTOStore> showStores();
    Map<Integer, DTOItem> showItems();
    Map<Integer, DTOOrder> showOrders();

    Map<Integer, DTOStore> getStoreForDynamicOrder(Map<Integer, DTOItem> items, int x, int y);
    DTOStore getStoreForOrder(Map<Integer, DTOItem> items, DTOStore store,int x, int y);
    Map<String, DTODiscount> getDiscounts(Map<Integer, DTOItem> items, List<DTOStore> storesOfDynamicOrderList);
    Map<String, DTODiscount> getDiscounts(Map<Integer, DTOItem> items, DTOStore store);
    List<DTOStore> getStoresForSummary(List<DTOStore> storesOfDynamicOrderList, List<DTOOffer> choosenOffers);
    List<DTOStore> getStoresForSummary(DTOStore storesOfDynamicOrderList, List<DTOOffer> choosenOffers);
    void makeOrder(List<DTOStore> storesFinishOrder, String username, String date, UserManager manager,int x, int y);


    void addItem(DTOItem newItem);
    //void addStore(DTOStore newStore);
    void addDiscount(DTODiscount newDiscount);

    boolean checkIDStore(int id);
    boolean checkNameStore(String name);
    boolean checkLocation(int x, int y);
    boolean checkIDItem(int id);
    boolean checkNameItem(String name);
    boolean checkNameDiscount(String name);

    String getZoneName();
    void setOwnerName(String ownerName);
    String getOwnerName();
    int getNumberOfItems();
    int getNumberOfStores();
    int getNumberOfOrders();
    double getAveragePriceOrders();

    List<DTOOrder> getMyOrders(String userName);
    List<DTOStore> getMyStores(String userName);
    List<DTOFeedback> getMyFeedbacks(String userName);

    void addFeedback(int storeID, String customerName, String date, int grade, String message, UserManager manager);
    void addNewStore(String newOwner,String storeName, Map<Integer, DTOItem> items, int ppk, int x, int y, UserManager manager);
}
