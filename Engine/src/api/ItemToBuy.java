package api;

public class ItemToBuy {
    private int id;
    private double quantity;
    private String itemName;

    public ItemToBuy(int id, double quantity, String itemName) {
        this.id = id;
        this.quantity = quantity;
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return  quantity + " of " + itemName ;
    }
}
