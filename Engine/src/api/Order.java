package api;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order  {
    private int id;
    //private LocalDate date;
    private String date;
    private transient Map<Integer, Store> stores;
    private  Map<Integer, Double> products;
    private List<Order> subOrders;
    private int numberOfItems;
    private double priceProducts;
    private double priceDelivery;
    private double totalPrice;
    protected static int serialNumber =1;
    private List<DTOStore> storesFinishOrder;
    private int X;
    private int Y;
    private String customerName;
    private List<DTOItem> allTheItemsOfTheOrder;


    public Order(String date, Map<Integer, Store> stores, Map<Integer, Double> products, int id, int numberOfItems,double priceProducts, double priceDelivery,double totalPrice) {
        this.date = date;
        this.stores = stores;
        this.products = products;
        this.id=id;
        this.numberOfItems =numberOfItems;
        this.priceProducts = priceProducts;
        this.priceDelivery = priceDelivery;
        this.totalPrice=totalPrice;
        serialNumber++;
    }

    public Order(String date, Map<Integer, Store> stores, Map<Integer, Double> products, int numberOfItems, double priceProducts, double priceDelivery, double totalPrice, List<DTOStore> storesFinishOrder) {
        this.date = date;
        this.stores = stores;
        this.products = products;
        this.numberOfItems =numberOfItems;
        this.priceProducts = priceProducts;
        this.priceDelivery = priceDelivery;
        this.totalPrice=totalPrice;
        this.id = serialNumber;
        this.storesFinishOrder =storesFinishOrder;
        serialNumber++;
    }

    public Order(String date, Map<Integer, Store> stores, Map<Integer, Double> products, List<Order> subOrders, int numberOfItems,double priceProducts, double priceDelivery,double totalPrice, List<DTOStore> storesFinishOrder) {
        this.date = date;
        this.stores = stores;
        this.products = products;
        this.subOrders = subOrders;
        this.numberOfItems =numberOfItems;
        this.priceProducts = priceProducts;
        this.priceDelivery = priceDelivery;
        this.totalPrice=totalPrice;
        this.id = serialNumber;
        this.storesFinishOrder =storesFinishOrder;
        serialNumber++;
    }


    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public Map<Integer, Double> getProducts() {
        return products;
    }

    public List<Order> getSubOrders() {
        return subOrders;
    }

    public double getPriceProducts() {
        return priceProducts;
    }

    public double getPriceDelivery() {
        return priceDelivery;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public List<DTOStore> getStoresFinishOrder() {
        return storesFinishOrder;
    }

    public static int getSerialNumber() {
        return serialNumber;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<DTOItem> getAllTheItemsOfTheOrder() {
        return allTheItemsOfTheOrder;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setAllTheItemsOfTheOrder(List<DTOItem> allTheItemsOfTheOrder) {
        this.allTheItemsOfTheOrder = allTheItemsOfTheOrder;
    }

    public void setId(int id) {
        this.id = id;
    }
}
