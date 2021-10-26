package api;

import java.util.Map;

public class DTOItem {
    private  String productName;
    private  int serialNumber;
    private String purchaseCategory;
    private int price;
    private int numberSold=0;
    private int numberOfStores;
    private double averagePrice;
    private double quantitySold;
    private double quantityChoosen;
    private boolean fromDiscount = false;
    private double totalPrice;
    private int idStore;
    private Map<Integer, Integer> idStoreAndPrice;
    private String storeData;


    // constructor for Add Item
    public DTOItem(String productName, int serialNumber, String purchaseCategory, Map<Integer, Integer> idStoreAndPrice){
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.purchaseCategory = purchaseCategory;
        this.idStoreAndPrice =  idStoreAndPrice;
    }

    public DTOItem(String productName, int serialNumber, String purchaseCategory, boolean fromDiscount, double quantityChoosen, double totalPrice) {
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.purchaseCategory = purchaseCategory;
        this.fromDiscount = fromDiscount;
        this.quantityChoosen = quantityChoosen;
        this.totalPrice=totalPrice;
    }


    public DTOItem(String productName, int serialNumber) {
        this.productName = productName;
        this.serialNumber = serialNumber;
    }

    /*public DTOItem(String productName, int serialNumber, String purchaseCategory, int price,double quantityChoosen, double totalPrice,boolean fromDiscount ){
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.purchaseCategory = purchaseCategory;
        this.price = price;
        this.quantityChoosen = quantityChoosen;
        this.totalPrice = totalPrice;
        this.fromDiscount = fromDiscount;

    }*/

    /*public DTOItem(String productName, int serialNumber, double quantityChoosen) {
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.quantityChoosen = quantityChoosen;
    }*/

    public DTOItem(String productName, int serialNumber, String purchaseCategory, double quantitySold, int  price) {
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.purchaseCategory = purchaseCategory;
        this.quantitySold = Math.round(quantitySold * 100.0) / 100.0;
        this.price = price;

    }

            /// constructor for showItem
    public DTOItem( int serialNumber, String productName, String purchaseCategory, int numberOfStores, double averagePrice, double quantitySold) {
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.purchaseCategory = purchaseCategory;
        this.numberOfStores = numberOfStores;
        this.averagePrice = averagePrice;
        this.quantitySold=Math.round(quantitySold * 100.0) / 100.0;
    }

    public void setQuantitySold(double quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getQuantityChoosen() {
        return quantityChoosen;
    }

    public void setQuantityChoosen(double quantityChoosen) {
        this.quantityChoosen = quantityChoosen;
    }

    public java.lang.String getProductName() {
        return productName;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }

    public int getPrice() {
        return price;
    }

    public int getNumberSold() {
        return numberSold;
    }

    public int getNumberOfStores() {
        return numberOfStores;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public double getQuantitySold() {
        return quantitySold;
    }

    public boolean isFromDiscount() {
        return fromDiscount;
    }

    public void setFromDiscount(boolean fromDiscount) {
        this.fromDiscount = fromDiscount;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPurchaseCategory(String purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }
/*
    @Override
    public String toString() {
        return productName + " - " + price;
    }*/

    public String getStoreData() {
        return storeData;
    }

    public void setStoreData(String storeData) {
        this.storeData = storeData;
    }

    public Map<Integer, Integer> getIdStoreAndPrice() {
        return idStoreAndPrice;
    }

    @Override
    public String toString() {
        return productName ;
    }

}
