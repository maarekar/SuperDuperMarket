package api;

public class Sell{
    private Product product;
    private int price;
    private int numberSoldInTheStore=0;
    private double quantitySold;

    public Sell(Product product, int price) {
        this.product = product;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberSold() {
        return numberSoldInTheStore;
    }

    public void setNumberSoldInTheStore(int numberSoldInTheStore) {
        this.numberSoldInTheStore = numberSoldInTheStore;
    }

    public int getNumberSoldInTheStore() {
        return numberSoldInTheStore;
    }

    public double getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(double quantitySold) {
        this.quantitySold = quantitySold;
    }

    public void addQuantitySold(double quantity){
        this.quantitySold += quantity;
    }
}
