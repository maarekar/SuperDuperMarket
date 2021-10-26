package api;

import java.io.Serializable;
import java.util.*;

public class Store {

    private int ID;
    private  String storeName;
    private Map<Integer, Sell> products;
    private List<Order> orders = new ArrayList<>();
    private Map<Integer, Store> stores;
    private Coordinates location;
    private int numberProductsSold;
    private int PPK;
    private double priceOfAllDeliveries;
    private List<Discount> discounts = new ArrayList<>();
    private String ownerName;
    protected static int serialNumber;
    private int idForOrder =1;



    public Store(int ID, String storeName, int PPK, Map<Integer, Sell> products, Coordinates location, List<Discount> discounts, String ownerName ){
        this.ID=ID;
        this.storeName=storeName;
        this.PPK=PPK;
        this.products=products;
        this.location=location;
        this.discounts=discounts;
        this.ownerName = ownerName;
    }

    public Store(int ID, String storeName, int PPK, Map<Integer, Sell> products, Coordinates location){
        this.ID=ID;
        this.storeName=storeName;
        this.PPK=PPK;
        this.products=products;
        this.location=location;
    }

    // constructor for add new Store
    public Store(String storeName, int PPK, Map<Integer, Sell> products, Coordinates location){
        this.storeName=storeName;
        this.PPK=PPK;
        this.products=products;
        this.location=location;
        this.ID = serialNumber;
        serialNumber++;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public void addOrder(Order newOrder){
        newOrder.setId(idForOrder);
        idForOrder++;
        this.orders.add(newOrder);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getPPK() {
        return PPK;
    }

    public void setPPK(int PPK) {
        this.PPK = PPK;
    }

    public Map<Integer, Sell> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Sell> products) {
        this.products = products;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public double getPriceOfAllDeliveries() {
        return priceOfAllDeliveries;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public int getNumberProductsSold() {
        return numberProductsSold;
    }

    public void setNumberProductsSold(int numberProductsSold) {
        this.numberProductsSold = numberProductsSold;
    }

    public void setPriceOfAllDeliveries(double priceOfAllDeliveries) {
        this.priceOfAllDeliveries = priceOfAllDeliveries;
    }

    public Map<Integer, Store> getStores() {
        return stores;
    }

    public void addNumberProductsSold(int number){
        this.numberProductsSold += number;
    }

    public void addPriceOfAllDeliveries(double price){
        this.priceOfAllDeliveries += price;
    }

    public void addNewProduct(Product newProduct, int price){
        Sell newSell = new Sell(newProduct, price);

        this.products.put(newProduct.getSerialNumber(), newSell);
    }

    public void addDiscount(Discount newDiscount){
        this.discounts.add(newDiscount);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
