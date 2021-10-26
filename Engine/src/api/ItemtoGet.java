package api;

import java.util.List;
import Enum.OperatorOffer;


public class ItemtoGet {
    private OperatorOffer operator;
    private List<Offer> offers;

    public ItemtoGet(OperatorOffer operator, List<Offer> offers) {
        this.operator = operator;
        this.offers = offers;
    }

    public OperatorOffer getOperator() {
        return operator;
    }

    public void setOperator(OperatorOffer operator) {
        this.operator = operator;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    /*@Override
    public String toString() {
        String offer = null;

        if(operator)

        return offer;
    }*/
}
