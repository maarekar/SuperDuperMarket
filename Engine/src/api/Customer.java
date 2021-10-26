package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
    private String name;
    private int id;
    private Coordinates location;
    private Map<Integer, Order> orders = new HashMap<>();
    List<Double> priceOrder = new ArrayList<>();
    List<Double> priceDelivery = new ArrayList<>();
    private double averagePriceOfOrders;
    private double averageDeliveryCost;


    public Customer(String name, int id, Coordinates location) {
        this.name = name;
        this.id = id;
        this.location = location;
    }

    public double getAveragePriceOfOrders() {
        return averagePriceOfOrders;
    }

    public void setAveragePriceOfOrders(double averagePriceOfOrders) {
        this.averagePriceOfOrders = averagePriceOfOrders;
    }

    public double getAverageDeliveryCost() {
        return averageDeliveryCost;
    }

    public void setAverageDeliveryCost(double averageDeliveryCost) {
        this.averageDeliveryCost = averageDeliveryCost;
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public void addOrder(Order order){
        priceOrder.add(order.getPriceProducts());
        priceDelivery.add(order.getPriceDelivery());
        this.averagePriceOfOrders =0;
        this.averageDeliveryCost =0;
        for (Double price : priceOrder){
            averagePriceOfOrders += price;
        }
        averagePriceOfOrders /= priceOrder.size();

        for (Double price : priceDelivery){
            averageDeliveryCost += price;
        }
        averageDeliveryCost /= priceDelivery.size();

        orders.put(order.getId(), order);
    }
}
