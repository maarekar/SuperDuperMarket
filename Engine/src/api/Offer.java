package api;

public class Offer {
    private int itemID;
    private double quantity;
    private int forAdditional;

    public Offer(int itemID, double quantity, int forAdditional) {
        this.itemID = itemID;
        this.quantity = quantity;
        this.forAdditional = forAdditional;
    }


    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getForAdditional() {
        return forAdditional;
    }

    public void setForAdditional(int forAdditional) {
        this.forAdditional = forAdditional;
    }

    /*@Override
    public String toString() {
        return quantity + " of " + nameProduct  + " for an additional of " + forAdditional ;
    }*/
}
