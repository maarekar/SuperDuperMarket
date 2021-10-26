package api;

import java.util.List;
import java.util.Map;

public class DTODiscount {
    private String name;
    private String  ifYouBuy;
    private String operator;
    private List<DTOOffer> offers;
    private List<String> offersToPrint;
    private int numberOfTimes;
    private int idIfYouBBuy;
    private double quantityIfYouBuy;
    private int storeId;

// constructor for add Discount
    public DTODiscount(String name, int idIfYouBBuy, double quantityIfYouBuy, String operator, List<DTOOffer> offers, int storeId) {
        this.name = name;
        this.idIfYouBBuy =idIfYouBBuy;
        this.quantityIfYouBuy = quantityIfYouBuy;
        this.operator = operator;
        this.offers = offers;
        this.storeId = storeId;
    }


    public DTODiscount(String name, String operator, List<DTOOffer> offers, int numberOfTimes) {
        this.name = name;
        //this.ifYouBuy = ifYouBuy;
        this.operator = operator;
        this.offers = offers;
        this.numberOfTimes = numberOfTimes;
    }

    public DTODiscount(String name, String  ifYouBuy, String operator, List<String> offersToPrint) {
        this.name = name;
        this.ifYouBuy = ifYouBuy;
        this.operator = operator;
        this.offersToPrint = offersToPrint;
    }

    public List<String> getOffersToPrint() {
        return offersToPrint;
    }

    public void discountUsed(){
        this.numberOfTimes--;
    }

    public int getNumberOfTimes() {
        return numberOfTimes;
    }

    public String getName() {
        return name;
    }

    public String getIfYouBuy() {
        return ifYouBuy;
    }

    public String getOperator() {
        return operator;
    }

    public List<DTOOffer> getOffers() {
        return offers;
    }

    public int getIdIfYouBBuy() {
        return idIfYouBBuy;
    }

    public double getQuantityIfYouBuy() {
        return quantityIfYouBuy;
    }

    public int getStoreId() {
        return storeId;
    }
}
