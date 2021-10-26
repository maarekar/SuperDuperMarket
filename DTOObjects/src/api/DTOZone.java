package api;

public class DTOZone {
    private final String zoneName;
    private final String ownerName;
    private final int numberOfItems;
    private final int numberOfStores;
    private final int numberOfOrders;
    private final double averagePriceOrders;

    public DTOZone(ISuperDuperMarket zone){
        zoneName = zone.getZoneName();
        ownerName = zone.getOwnerName();
        numberOfItems = zone.getNumberOfItems();
        numberOfStores = zone.getNumberOfStores();
        numberOfOrders = zone.getNumberOfOrders();
        averagePriceOrders = zone.getAveragePriceOrders();
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getNumberOfStores() {
        return numberOfStores;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public double getAveragePriceOrders() {
        return averagePriceOrders;
    }
}
