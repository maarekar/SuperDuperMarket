package api;

public class DTOOffer {
    private int itemID;
    private double quantity;
    private int forAdditional;
    private String nameProduct;
    private int storeID;
    private String sentenceToPrint;

    public DTOOffer(String nameProduct, int itemID, double quantity, int forAdditional, int storeID) {
        this.nameProduct = nameProduct;
        this.itemID = itemID;
        this.quantity = quantity;
        this.forAdditional = forAdditional;
        this.storeID = storeID;
        this.sentenceToPrint = quantity + " of " + nameProduct  + " for an additional of " + forAdditional ;
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

    public int getStoreID() {
        return storeID;
    }

    @Override
    public String toString() {
        return quantity + " of " + nameProduct  + " for an additional of " + forAdditional ;
    }
}
