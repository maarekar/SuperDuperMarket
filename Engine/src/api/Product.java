package api;
import Enum.PurchaseMode;

public  class Product{

    private final String productName;
    private final int serialNumber;
    private final PurchaseMode string;
    private int numberSoldInTheMarket = 0;
    private double quantitySold;


    public Product(String productName, int serialNumber, PurchaseMode string) {
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.string = string;
    }

    public String getProductName() {
        return productName;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public PurchaseMode getPurchaseMode() {
        return string;
    }

    public int getNumberSoldInTheMarket() {
        return numberSoldInTheMarket;
    }

    public void setNumberSoldInTheMarket(int numberSoldInTheMarket) {
        this.numberSoldInTheMarket = numberSoldInTheMarket;
    }

    public double getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(double quantitySolde) {
        this.quantitySold = quantitySolde;
    }

    public void increaseNumberSoldInTheMarket(){
        this.numberSoldInTheMarket ++;
    }

    public void decreaseNumberSoldInTheMarket(){
        this.numberSoldInTheMarket --;
    }

    public void addQuantitySold(double quantity){
        this.quantitySold += quantity;
    }


    @Override
    public String toString() {
        return "- Name: " + productName + '\n' +
                "- Serial number: " + serialNumber;
    }
}
