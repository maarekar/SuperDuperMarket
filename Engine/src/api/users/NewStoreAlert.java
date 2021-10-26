package api.users;

public class NewStoreAlert {
    private String storeOwnerName;
    private String storeName;
    private String location;
    private String numberOfItems;
    private String zoneName;

    public NewStoreAlert(String storeOwnerName, String storeName, String location, String numberOfItems, String zoneName) {
        this.storeOwnerName = storeOwnerName;
        this.storeName = storeName;
        this.location = location;
        this.numberOfItems = numberOfItems;
        this.zoneName = zoneName;
    }
}
