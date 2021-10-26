package api;

import Generated3.*;
import Enum.PurchaseMode;
import Enum.OperatorOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadClass {
    private SuperDuperMarketDescriptor SDM;


    public LoadClass(SuperDuperMarketDescriptor SDM){
        this.SDM = SDM;
    }

    public void loadMarket(SuperDuperMarketLogic market, String ownerName){
        market.setProducts(this.loadItems());
        market.setStores(this.loadStores(market.getProducts(), ownerName));
        market.setZoneName(SDM.getSDMZone().getName());
        //market.setCustomers(this.loadCustomers());
    }


    private Map<Integer, Product> loadItems(){
        Map<Integer, Product> newItems = new HashMap<Integer, Product>();
        List<SDMItem> items = SDM.getSDMItems().getSDMItem();

        for (SDMItem item: items) {
            newItems.put(item.getId(), new Product(item.getName(), item.getId(), getCategory(item.getPurchaseCategory())));
        }

        return newItems;
    }

    private static PurchaseMode getCategory(String category){
        if(category.equals("Quantity")){
            return PurchaseMode.Quantity;
        }

        return PurchaseMode.Weight;
    }

    private Map<Integer, Store> loadStores(Map<Integer, Product> allTheProducts, String ownerName){
        Map<Integer, Store> newProducts = new HashMap<Integer, Store>();
        List<SDMStore> stores = SDM.getSDMStores().getSDMStore();
        List<SDMSell> items;
        OperatorOffer operator;

        for (SDMStore store: stores) {
            items=store.getSDMPrices().getSDMSell();
            Map<Integer, Sell> products = new HashMap<Integer, Sell>();
            for (SDMSell item: items) {
                allTheProducts.get(item.getItemId()).increaseNumberSoldInTheMarket();
                products.put(item.getItemId(), new Sell(allTheProducts.get(item.getItemId()),item.getPrice()));
            }
            List<Discount> discounts = new ArrayList<>();
            if(store.getSDMDiscounts() !=null){
                for(SDMDiscount discount : store.getSDMDiscounts().getSDMDiscount()){
                    List<Offer> offers = new ArrayList<>();

                    for(SDMOffer offer: discount.getThenYouGet().getSDMOffer()){
                        offers.add(new Offer(offer.getItemId(),offer.getQuantity(), offer.getForAdditional()));
                    }

                    if(discount.getThenYouGet().getOperator().equals("IRRELEVANT")){
                        operator = OperatorOffer.IRRELEVANT;
                    }

                    else if(discount.getThenYouGet().getOperator().equals("ONE-OF")){
                        operator = OperatorOffer.ONE_OF;
                    }
                    else{
                        operator = OperatorOffer.ALL_OR_NOTHING;
                    }

                    discounts.add(new Discount(discount.getName(), new ItemToBuy(discount.getIfYouBuy().getItemId(), discount.getIfYouBuy().getQuantity(), SDM.getSDMItems().getSDMItem().get(discount.getIfYouBuy().getItemId() - 1).getName()), new ItemtoGet(operator, offers)));
                }
            }
           else{
               discounts = null;
            }
            newProducts.put(store.getId(), new Store(store.getId(), store.getName(),
                    store.getDeliveryPpk(), products, new Coordinates(store.getLocation().getX(), store.getLocation().getY()), discounts, ownerName));
        }

        return newProducts;

    }

    /*private Map<Integer, Customer> loadCustomers(){
        Map<Integer, Customer> newCustomers = new HashMap<>();
        List<SDMCustomer> customers = SDM.getSDMCustomers().getSDMCustomer();

        for(SDMCustomer customer: customers){
            newCustomers.put(customer.getId(), new Customer(customer.getName(), customer.getId(), new Coordinates(customer.getLocation().getX(),customer.getLocation().getY() )));
        }


        return newCustomers;
    }*/
}
