package api;

import api.DTOItem;
import api.DTOOrder;

import java.util.*;

public class DTOStore {
    private int id;
    private String name;
    private Map<Integer, DTOItem> items;
    private List<DTOItem> itemsList;
    private List<DTOOrder> orders;
    private int PPK;
    private double allDeliveries;
    private int x;
    private int y;
    private List<DTODiscount> discounts;
    private int numberOfItems;
    private double price;
    private String location;
    private double distance;
    private double delivery;
    private List<DTOItem> itemsFromOrder = new ArrayList<>();
    private List<DTOItem> itemsFromOffer = new ArrayList<>();
    private Map<Integer, Integer> idAndPriceItems = new HashMap<>();
    private int priceItem;
    private int numberOfOrders;
    private double priceItemSold;
    private String ownerName;


    // constructor for add Store
    public DTOStore(int id, String name, int PPK,int x, int y, Map<Integer, Integer> idAndPriceItems ){
        this.id = id;
        this.name = name;
        this.PPK = PPK;
        this.x=x;
        this.y=y;
        this.idAndPriceItems = idAndPriceItems;
    }


    public DTOStore(int id, String name, Map<Integer, DTOItem> items, String location, int PPK, int numberOfItems, double price, double distance, double delivery, List<DTOItem> itemsFromOrder) {
        this.id = id;
        this.name = name;
        this.items=items;

        this.location = location;
        this.PPK = PPK;
        this.numberOfItems = numberOfItems;
        this.price = price;
        this.distance = distance;
        this.delivery = delivery;
        this.itemsFromOrder = itemsFromOrder;
    }
    // constructor show stores
    public DTOStore(int id, String name, Map<Integer, DTOItem> items, List<DTOOrder> orders, int PPK, double allDeliveries, int x, int y, List<DTODiscount> discounts){
        this.id=id;
        this.name=name;
        this.items=items;
        this.orders=orders;
        this.PPK=PPK;
        this.allDeliveries=allDeliveries;
        this.x=x;
        this.y=y;
        this.discounts = discounts;
    }

    public DTOStore(int id, String name, Map<Integer, DTOItem> items, String location, int PPK, int numberOfOrders, double priceItemSold, double allDeliveries, String ownerName, int x, int y){
        this.id=id;
        this.name=name;
        this.items=items;
        this.itemsList = new ArrayList<>(items.values());
        this.location = location;
        this.numberOfOrders = numberOfOrders;
        this.PPK=PPK;
        this.priceItemSold = priceItemSold;
        this.allDeliveries=allDeliveries;
        this.ownerName = ownerName;
        this.x=x;
        this.y=y;
    }

    public DTOStore(int id, String name, Map<Integer, DTOItem> items, List<DTOOrder> orders, int PPK, double allDeliveries, int x, int y){
        this.id=id;
        this.name=name;
        this.items=items;
        this.orders=orders;
        this.PPK=PPK;
        this.allDeliveries=allDeliveries;
        this.x=x;
        this.y=y;
    }


    // constructor get my stores
    public DTOStore(int id, String name, List<DTOOrder> orders, int numberOfOrders, int x, int y){
        this.id = id;
        this.name = name;
        this.orders = orders;
        this.numberOfOrders = numberOfOrders;
        this.x = x;
        this.y =y;
    }

    public void addItem(DTOItem item){
        itemsFromOrder.add(item);
    }

    public void addItemFromOffer(DTOItem item){
        itemsFromOffer.add(item);
    }

    public List<DTOItem> getItemsFromOrder() {
        return itemsFromOrder;
    }

    public void setItemsFromOrder(List<DTOItem> itemsFromOrder) {
        this.itemsFromOrder = itemsFromOrder;
    }

    public String getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }

    public double getDelivery() {
        return delivery;
    }

    public DTOStore(String name) {
        this.name = name;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<DTODiscount> getDiscounts() {
        return discounts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, DTOItem> getItems() {
        return items;
    }

    public List<DTOOrder> getOrders() {
        return orders;
    }

    public int getPPK() {
        return PPK;
    }

    public double getAllDeliveries() {
        return allDeliveries;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public List<DTOItem> getItemsFromOffer() {
        return itemsFromOffer;
    }

    public Map<Integer, Integer> getIdAndPriceItems() {
        return idAndPriceItems;
    }

    public void setIdAndPriceItems(Map<Integer, Integer> idAndPriceItems) {
        this.idAndPriceItems = idAndPriceItems;
    }

    public int getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(int priceItem) {
        this.priceItem = priceItem;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return  name;
    }
}
