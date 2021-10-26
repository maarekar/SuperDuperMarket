package api;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DTOOrder implements Serializable {
    private String date;
    private int numberOfItems;
    private double totalPriceOfItems;
    private double deliveryCost;
    private int serialNumber;
    private double totalPriceOfOrder;
    private Map<Integer, String> stores;
    private Map<Integer, Double> items;
    private List<DTOOrder> subOrders;
    private List<DTOStore> storesFinishOrder;
    private String location;
    private int numberOfStores;
    private List<DTOItem> myItems;
    //private DecimalFormat df = new DecimalFormat("#.##");



    public DTOOrder() {

    }

    public DTOOrder(int serialNumber, List<DTOStore> storesFinishOrder, String date){
        this.serialNumber = serialNumber;
        this.storesFinishOrder = storesFinishOrder;
        this.date = date;
    }
     // contructor of get data orders
    public DTOOrder(String date, int numberOfItems, double totalPriceOfItems, double deliveryCost, double totalPriceOfOrder, int serialNumber, Map<Integer, String> stores, Map<Integer, Double> items, String location, List<DTOItem> myItems ) {
        this.date = date;
        this.numberOfItems = numberOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.deliveryCost = deliveryCost;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.serialNumber = serialNumber;
        this.stores = stores;
        this.items = items;
        this.location = location;
        this.myItems = myItems;
    }
    public DTOOrder(String date, int numberOfItems, double totalPriceOfItems, double deliveryCost, double totalPriceOfOrder, int serialNumber, Map<Integer, String> stores, Map<Integer, Double> items, List<DTOStore> storesFinishOrder) {
        this.date = date;
        this.numberOfItems = numberOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.deliveryCost = deliveryCost;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.serialNumber = serialNumber;
        this.stores = stores;
        this.items = items;
        this.storesFinishOrder = storesFinishOrder;
    }

    public DTOOrder(String date, List<DTOOrder> subOrders,int numberOfItems, double totalPriceOfItems, double deliveryCost, double totalPriceOfOrder, int serialNumber, Map<Integer, String> stores, Map<Integer, Double> items) {
        this.date = date;
        this.subOrders = subOrders;
        this.numberOfItems = numberOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.deliveryCost = deliveryCost;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.serialNumber = serialNumber;
        this.stores = stores;
        this.items = items;
    }

    // constructor for my Own Orders
    public DTOOrder(int serialNumber, String date, String location, int numberOfStores, int numberOfItems, double totalPriceOfItems, double deliveryCost, double totalPriceOfOrder, List<DTOItem> myItems ){
        this.serialNumber =serialNumber;
        this.date = date;
        this.location = location;
        this.numberOfStores = numberOfStores;
        this.numberOfItems = numberOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.deliveryCost = deliveryCost;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.myItems = myItems;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public double getTotalPriceOfItems() {
        return totalPriceOfItems;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public double getTotalPriceOfOrder() {
        return totalPriceOfOrder;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public Map<Integer, String> getStores() {
        return stores;
    }

    public Map<Integer, Double> getItems() {
        return items;
    }

    public List<DTOOrder> getSubOrders() {
        return subOrders;
    }

    public List<DTOStore> getStoresFinishOrder() {
        return storesFinishOrder;
    }


}
