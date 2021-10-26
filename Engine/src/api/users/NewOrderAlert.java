package api.users;

public class NewOrderAlert {
    private int orderId;
    private String customerName;
    private int numberOfItems;
    private double priceOfItems;
    private double deliveryCost;
    private String storeName;

    public NewOrderAlert(int orderId, String customerName, int numberOfItems, double priceOfItems, double deliveryCost, String storeName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.numberOfItems = numberOfItems;
        this.priceOfItems = priceOfItems;
        this.deliveryCost = deliveryCost;
        this.storeName = storeName;
    }
}
