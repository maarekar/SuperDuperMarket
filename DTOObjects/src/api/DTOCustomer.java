package api;

public class DTOCustomer {
    private int id;
    private String name;
    private String location;
    private int numberOfOrders;
    private double averagePriceOfOrders;
    private double averageDeliveryCost;
    private int x;
    private int y;

    public DTOCustomer(int id, String name, String location, int numberOfOrders, double averagePriceOfOrders, double averageDeliveryCost, int x, int y) {
        this.id = id;
        this.name = name;
        this.location=location;
        this.numberOfOrders = numberOfOrders;
        this.averagePriceOfOrders = averagePriceOfOrders;
        this.averageDeliveryCost = averageDeliveryCost;
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation(){
        return location;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public double getAveragePriceOfOrders() {
        return averagePriceOfOrders;
    }

    public double getAverageDeliveryCost() {
        return averageDeliveryCost;
    }

    @Override
    public String toString() {
        return name + " - " + id;
    }
}
