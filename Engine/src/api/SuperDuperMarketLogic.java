package api;

import Exceptions.*;
import Generated3.SuperDuperMarketDescriptor;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import Enum.OperatorOffer;
import Enum.TransactionType;




import Enum.PurchaseMode;
import api.users.*;


public class SuperDuperMarketLogic implements ISuperDuperMarket {
    private Map<Integer, Store> stores;
    private Map<Integer, Product> products;
    private Map<Integer, Order> orders = new HashMap<Integer, Order>();
    private Map<Integer, Customer> customers;
    private DecimalFormat df = new DecimalFormat("#.##");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm-HH:mm");

    private String zoneName;
    private String ownerName;
    private List<Feedback> feedbacks = new ArrayList<>();
    private List<String> allTheOwners = new ArrayList<>();


    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Map<Integer, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Map<Integer, Customer> customers) {
        this.customers = customers;
    }

    /*public void setMarketController(SuperDuperMarketController marketController) {
        this.marketController = marketController;
    }


    public SuperDuperMarketLogic(SuperDuperMarketController marketController) {
        this.marketController = marketController;
    }*/

    public SuperDuperMarketLogic(){
    }

    public Map<Integer, Store> getStores() {

        return stores;
    }

    public void setStores(Map<Integer, Store> stores) {
        Store.serialNumber = stores.size() + 1 ;
        this.stores = stores;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }

    @Override
    public void loadFile(InputStream path, String ownerName) throws JAXBException, FileNotFoundException, MessageException {
        //currentRunningTask = new LoadFileTask(path, this);
       // marketController.bindTaskToUIComponents(currentRunningTask, onFinish);
        //taskController.bindTaskToUIComponents(currentRunningTask, onFinish);
        SuperDuperMarketDescriptor SDM = ReadXml.loadFile(path);
        CheckFile x = new CheckFile(SDM);
        x.checkTheFile();
        //SuperDuperMarket b = new SuperDuperMarket();
        LoadClass a = new LoadClass(SDM);
        a.loadMarket(this, ownerName);

    }

    @Override
    public Map<Integer, DTOStore> showStores() {

        return getDataStores();
    }

    @Override
    public Map<Integer, DTOItem> showItems() {
        return getDataITems();
    }


    private Map<Integer, Integer> getCheapestItem(Map<Integer, Double> itemOrder) {
        int idStore = 0;
        double min = 0;
        Map<Integer, Integer> itemWithStore = new HashMap<Integer, Integer>();
        for (Integer id : itemOrder.keySet()) {
            for (Store store : this.stores.values()) {
                if (store.getProducts().containsKey(id)) {
                    if (min == 0 || store.getProducts().getOrDefault(id, null).getPrice() < min) {
                        min = store.getProducts().getOrDefault(id, null).getPrice();
                        idStore = store.getID();
                    }
                }
            }
            min = 0;
            itemWithStore.put(id, idStore);
        }

        return itemWithStore;
    }




    @Override
    public Map<Integer, DTOOrder> showOrders() {
        Map<Integer, DTOOrder> orders = new HashMap<Integer, DTOOrder>();
        int counter = 1;

        for (Order order : this.orders.values()) {
            /*if (order.getSubOrders() == null) {
                Map<Integer, String> storesInOrder = new HashMap<Integer, String>();
                for (Store storeInOrder : order.getStores().values()) {
                    storesInOrder.put(storeInOrder.getID(), storeInOrder.getStoreName());
                }
                orders.put(order.getId(), new DTOOrder(String.valueOf(order.getDate()), order.getNumberOfItems(), order.getPriceProducts(), order.getPriceDelivery(), order.getTotalPrice(), order.getId(), storesInOrder, order.getProducts()));
            } else {
                List<DTOOrder> subOrders = new ArrayList<DTOOrder>();
                Map<Integer, String> storesInSubOrder = new HashMap<Integer, String>();
                for (Order subOrder : order.getSubOrders()) {
                    Map<Integer, String> storesInOrder = new HashMap<Integer, String>();
                    for (Store storeInOrder : subOrder.getStores().values()) {
                        storesInOrder.put(storeInOrder.getID(), storeInOrder.getStoreName());
                        storesInSubOrder.put(storeInOrder.getID(), storeInOrder.getStoreName());
                    }
                    subOrders.add(new DTOOrder(String.valueOf(subOrder.getDate()), subOrder.getNumberOfItems(), subOrder.getPriceProducts(), subOrder.getPriceDelivery(), subOrder.getTotalPrice(), subOrder.getId(), storesInOrder, subOrder.getProducts()));
                    counter++;
                }
                orders.put(order.getId(), new DTOOrder(String.valueOf(order.getDate()), subOrders, order.getNumberOfItems(), order.getPriceProducts(), order.getPriceDelivery(), order.getTotalPrice(), order.getId(), storesInSubOrder, order.getProducts(), order.getStoresFinishOrder()));
            }*/
            String s =String.valueOf(order.getDate());
            orders.put(order.getId(), new DTOOrder(order.getId(), order.getStoresFinishOrder(), s));
        }

        return orders;
    }


    @Override
    public Map<Integer, DTOStore> getStoreForDynamicOrder(Map<Integer, DTOItem> items, int x, int y) {
        Map<Integer, DTOStore> stores = new HashMap<>();
        Map<Integer, Double> itemOrder =  new HashMap<>();
        String location;
        double distance, delivery, price;
        int priceItem;

        for (DTOItem item: items.values()){
            itemOrder.put(item.getSerialNumber(), item.getQuantityChoosen());
        }

        Map<Integer, Integer> itemWithStore = getCheapestItem(itemOrder);


        for (Integer idItem: itemWithStore.keySet()){
            if(stores.containsKey(itemWithStore.get(idItem))){
                price = this.stores.get(itemWithStore.get(idItem)).getProducts().get(idItem).getPrice() * items.get(idItem).getQuantityChoosen();
                price=Math.round(price * 100.0) / 100.0;
                stores.get(itemWithStore.get(idItem)).setPrice(stores.get(itemWithStore.get(idItem)).getPrice() + price);
                stores.get(itemWithStore.get(idItem)).setNumberOfItems(stores.get(itemWithStore.get(idItem)).getNumberOfItems() + 1);
                stores.get(itemWithStore.get(idItem)).addItem(items.get(idItem));
                priceItem= this.stores.get(itemWithStore.get(idItem)).getProducts().get(idItem).getPrice();


                for (DTOItem item : stores.get(itemWithStore.get(idItem)).getItemsFromOrder() ){
                    if(item.getSerialNumber() == idItem){
                        item.setPrice(priceItem);
                        item.setPurchaseCategory(this.stores.get(itemWithStore.get(idItem)).getProducts().get(idItem).getProduct().getPurchaseMode().name());
                        item.setTotalPrice(Double.parseDouble(df.format(priceItem * items.get(idItem).getQuantityChoosen())));
                    }
                }
            }

            else{
                Map<Integer, DTOItem> itemsOfStore = new HashMap<Integer, DTOItem>();
                for (Sell sell : this.stores.get(itemWithStore.get(idItem)).getProducts().values()) {
                    itemsOfStore.put(sell.getProduct().getSerialNumber(), new DTOItem(sell.getProduct().getProductName(), sell.getProduct().getSerialNumber(), sell.getProduct().getPurchaseMode().toString(), sell.getQuantitySold(), sell.getPrice()));
                }
                List<DTOItem> itemsFromThisStore = new ArrayList<>();
                itemsFromThisStore.add(items.get(idItem));
                location = "[" + this.stores.get(itemWithStore.get(idItem)).getLocation().getX() +", " + this.stores.get(itemWithStore.get(idItem)).getLocation().getY() + "]";
                distance = Math.sqrt(Math.pow(Math.abs(this.stores.get(itemWithStore.get(idItem)).getLocation().getX() - x), 2) + Math.pow(Math.abs(this.stores.get(itemWithStore.get(idItem)).getLocation().getY() - y), 2));
                delivery = distance * this.stores.get(itemWithStore.get(idItem)).getPPK();

                price = this.stores.get(itemWithStore.get(idItem)).getProducts().get(idItem).getPrice() * items.get(idItem).getQuantityChoosen();
                price=Math.round(price * 100.0) / 100.0;


                stores.put(itemWithStore.get(idItem), new DTOStore(itemWithStore.get(idItem), this.stores.get(itemWithStore.get(idItem)).getStoreName(),itemsOfStore, location, this.stores.get(itemWithStore.get(idItem)).getPPK(), 1, Double.parseDouble(df.format(price)), Double.parseDouble(df.format(distance)), Double.parseDouble(df.format(delivery)), itemsFromThisStore));

                priceItem= this.stores.get(itemWithStore.get(idItem)).getProducts().get(idItem).getPrice();



                for (DTOItem item : stores.get(itemWithStore.get(idItem)).getItemsFromOrder() ){
                    if(item.getSerialNumber() == idItem){
                        item.setPrice(priceItem);
                        item.setPurchaseCategory(this.stores.get(itemWithStore.get(idItem)).getProducts().get(idItem).getProduct().getPurchaseMode().name());
                        item.setTotalPrice(Double.parseDouble(df.format(priceItem * items.get(idItem).getQuantityChoosen())));
                    }
                }

            }
        }

        return stores;
    }

    @Override
    public DTOStore getStoreForOrder(Map<Integer, DTOItem> items, DTOStore store, int x, int y) {
        double distance, delivery, price;
        int priceItem;
        String location;




        location = "[" + this.stores.get(store.getId()).getLocation().getX() +", " + this.stores.get(store.getId()).getLocation().getY() + "]";
        distance = Math.sqrt(Math.pow(Math.abs(this.stores.get(store.getId()).getLocation().getX() - x), 2) + Math.pow(Math.abs(this.stores.get(store.getId()).getLocation().getY() - y), 2));
        delivery = distance * this.stores.get(store.getId()).getPPK();

        store.setDistance(distance);
        store.setDelivery(delivery);
        store.setLocation(location);

        for (DTOItem itemOfOrder : items.values()){
            //price = this.stores.get(store.getId()).getProducts().get(itemOfOrder.getSerialNumber()).getPrice() * items.get(itemOfOrder.getSerialNumber()).getQuantityChoosen();
            priceItem= this.stores.get(store.getId()).getProducts().get(itemOfOrder.getSerialNumber()).getPrice();
            itemOfOrder.setPrice(priceItem);
            itemOfOrder.setPurchaseCategory(this.stores.get((store.getId())).getProducts().get(itemOfOrder.getSerialNumber()).getProduct().getPurchaseMode().name());
            itemOfOrder.setTotalPrice(Double.parseDouble(df.format(priceItem * items.get(itemOfOrder.getSerialNumber()).getQuantityChoosen())));
            store.addItem(itemOfOrder);
        }




        return store;
    }


    @Override
    public Map<String, DTODiscount> getDiscounts(Map<Integer, DTOItem> items, List<DTOStore> storesOfDynamicOrderList) {
        Map<String, DTODiscount> discounts = new HashMap<>();
        Store tempStore;
        int counter=0;
        String operator;
        double quantity;

        for(DTOStore store: storesOfDynamicOrderList){
            tempStore = this.stores.get(store.getId());
            if(tempStore.getDiscounts() != null){
                for(Discount discount: tempStore.getDiscounts()){
                    for (Integer idItem: items.keySet()){
                        for (DTOItem dtoitem : store.getItemsFromOrder()){
                            if(dtoitem.getSerialNumber() == idItem){
                                if(discount.getIfYouBuy().getId() == idItem){
                                    if(items.get(idItem).getQuantityChoosen() >= discount.getIfYouBuy().getQuantity()){
                                        quantity = items.get(idItem).getQuantityChoosen();
                                        while(quantity >= discount.getIfYouBuy().getQuantity()){
                                            quantity -= discount.getIfYouBuy().getQuantity();
                                            counter++;
                                        }
                                        if(discount.getThenYouGet().getOperator() == OperatorOffer.IRRELEVANT){
                                            operator= "GET";
                                        }

                                        else if(discount.getThenYouGet().getOperator() == OperatorOffer.ONE_OF){
                                            operator= "GET one of them:";
                                        }
                                        else{
                                            operator= " GET all of them:";
                                        }
                                        List<DTOOffer> offers = new ArrayList<>();
                                        for (Offer offer: discount.getThenYouGet().getOffers()){
                                            offers.add(new DTOOffer(this.products.get(offer.getItemID()).getProductName(), offer.getItemID(), offer.getQuantity(), offer.getForAdditional(), tempStore.getID()));
                                        }

                                        discounts.put(discount.getName(), new DTODiscount(discount.getName(), operator, offers, counter));

                                        counter = 0;
                                    }
                                }
                            }
                        }
            /*            if(store.getItemsFromOrder().containsKey(idItem)){
                            if(discount.getIfYouBuy().getId() == idItem){
                                if(items.get(idItem).getQuantityChoosen() >= discount.getIfYouBuy().getQuantity()){
                                    quantity = items.get(idItem).getQuantityChoosen();
                                    while(quantity >= discount.getIfYouBuy().getQuantity()){
                                        quantity -= discount.getIfYouBuy().getQuantity();
                                        counter++;
                                    }
                                    if(discount.getThenYouGet().getOperator() == OperatorOffer.IRRELEVANT){
                                        operator= "GET";
                                    }

                                    else if(discount.getThenYouGet().getOperator() == OperatorOffer.ONE_OF){
                                        operator= "GET one of them:";
                                    }
                                    else{
                                        operator= " GET all of them:";
                                    }
                                    List<DTOOffer> offers = new ArrayList<>();
                                    for (Offer offer: discount.getThenYouGet().getOffers()){
                                        offers.add(new DTOOffer(this.products.get(offer.getItemID()).getProductName(), offer.getItemID(), offer.getQuantity(), offer.getForAdditional(), tempStore.getID()));
                                    }

                                    discounts.put(discount.getName(), new DTODiscount(discount.getName(), operator, offers, counter));

                                    counter = 0;
                                }
                            }
                        }*/

                    }
                }
            }

        }



        return discounts;
    }

    @Override
    public Map<String, DTODiscount> getDiscounts(Map<Integer, DTOItem> items, DTOStore store) {
        Map<String, DTODiscount> discounts = new HashMap<>();
        Store tempStore;
        int counter=0;
        String operator;
        double quantity;

        tempStore = this.stores.get(store.getId());
        if(tempStore.getDiscounts() != null){
            for(Discount discount: tempStore.getDiscounts()){
                for (Integer idItem: items.keySet()){
                    //if(store.getItemsFromOrder().containsKey(idItem)){
                        if(discount.getIfYouBuy().getId() == idItem){
                            if(items.get(idItem).getQuantityChoosen() >= discount.getIfYouBuy().getQuantity()){
                                quantity = items.get(idItem).getQuantityChoosen();
                                while(quantity >= discount.getIfYouBuy().getQuantity()){
                                    quantity -= discount.getIfYouBuy().getQuantity();
                                    counter++;
                                }
                                if(discount.getThenYouGet().getOperator() == OperatorOffer.IRRELEVANT){
                                    operator= "GET";
                                }

                                else if(discount.getThenYouGet().getOperator() == OperatorOffer.ONE_OF){
                                    operator= "GET one of them:";
                                }
                                else{
                                    operator= " GET all of them:";
                                }
                                List<DTOOffer> offers = new ArrayList<>();
                                for (Offer offer: discount.getThenYouGet().getOffers()){
                                    offers.add(new DTOOffer(this.products.get(offer.getItemID()).getProductName(), offer.getItemID(), offer.getQuantity(), offer.getForAdditional(), tempStore.getID()));
                                }

                                discounts.put(discount.getName(), new DTODiscount(discount.getName(), operator, offers, counter));

                                counter = 0;
                            }
                        }
                    //}

                }
            }
        }

        return discounts;
    }

    @Override
    public List<DTOStore> getStoresForSummary(List<DTOStore> storesOfDynamicOrderList, List<DTOOffer> choosenOffers) {
        List<DTOStore> storesForSummary = new ArrayList<>();


        for (DTOStore store: storesOfDynamicOrderList){
            for (DTOOffer offer: choosenOffers){
                if(offer.getStoreID() == store.getId()){
                    DTOItem itemFromOffer = new DTOItem(store.getItems().get(offer.getItemID()).getProductName(), offer.getItemID(), store.getItems().get(offer.getItemID()).getPurchaseCategory(), true, offer.getQuantity(), offer.getForAdditional());

                    store.addItemFromOffer(itemFromOffer);
                }
            }
            storesForSummary.add(store);
        }

        return storesForSummary;
    }

    @Override
    public List<DTOStore> getStoresForSummary(DTOStore storesOfDynamicOrderList, List<DTOOffer> choosenOffers) {
        List<DTOStore> storesForSummary = new ArrayList<>();



            for (DTOOffer offer: choosenOffers){
                if(offer.getStoreID() == storesOfDynamicOrderList.getId()){
                    DTOItem itemFromOffer = new DTOItem(storesOfDynamicOrderList.getItems().get(offer.getItemID()).getProductName(), offer.getItemID(), storesOfDynamicOrderList.getItems().get(offer.getItemID()).getPurchaseCategory(), true, offer.getQuantity(), offer.getForAdditional());

                    storesOfDynamicOrderList.addItemFromOffer(itemFromOffer);
                }
            }
            storesForSummary.add(storesOfDynamicOrderList);


        return storesForSummary;
    }

    @Override
    public void makeOrder(List<DTOStore> storesFinishOrder, String username, String date, UserManager manager,int x, int y) {
        List<Order> subOrders = new ArrayList<>();
        Map<Integer, Store> storesOfOrder = new HashMap<>();
        Map<Integer, Double> productsOfOrder = new HashMap<>();
        //Map<Integer, Integer> checkNumberOfItems = new HashMap<>();
        double totalItemsPrice=0, totalDeliveryCost=0, totalPrice=0;
        int numberOfItems=0;
        int serialNumber = 1;
        List<DTOItem> dtoItems = new ArrayList<>();

        for (DTOStore storeOfFinishOrder: storesFinishOrder){
            double totalItemsPriceForSubOrder=0, totalDeliveryCostForSubOrder=0, totalPriceForSubOrder=0;
            Map<Integer, Double> productsOfSubOrder = new HashMap<>();
            List<DTOItem> dtoItemsForSubOrder = new ArrayList<>();
            double priceForThisStore=0;

            this.stores.get(storeOfFinishOrder.getId()).addNumberProductsSold(storeOfFinishOrder.getItemsFromOrder().size());
            this.stores.get(storeOfFinishOrder.getId()).addPriceOfAllDeliveries(storeOfFinishOrder.getDelivery());



            for (DTOItem itemInOrder: storeOfFinishOrder.getItemsFromOrder()){
                String storeData = storeOfFinishOrder.getName() + " - " + storeOfFinishOrder.getId();
                itemInOrder.setStoreData(storeData);
                dtoItems.add(itemInOrder);
                dtoItemsForSubOrder.add(itemInOrder);
                this.products.get(itemInOrder.getSerialNumber()).increaseNumberSoldInTheMarket();
                this.products.get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                productsOfOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                productsOfSubOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                totalItemsPrice += itemInOrder.getTotalPrice();
                priceForThisStore += itemInOrder.getTotalPrice();
                totalItemsPriceForSubOrder += itemInOrder.getTotalPrice();
                //totalDeliveryCostForSubOrder += itemInOrder.getTotalPrice();
                this.stores.get(storeOfFinishOrder.getId()).getProducts().get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                //checkNumberOfItems.put(itemInOrder.getIdStore(), itemInOrder.getIdStore() );
            }
            for (DTOItem itemInOrder: storeOfFinishOrder.getItemsFromOffer()){
                String storeData = storeOfFinishOrder.getName() + " - " + storeOfFinishOrder.getId();
                itemInOrder.setStoreData(storeData);
                dtoItems.add(itemInOrder);
                dtoItemsForSubOrder.add(itemInOrder);
                this.products.get(itemInOrder.getSerialNumber()).increaseNumberSoldInTheMarket();
                this.products.get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                productsOfOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                productsOfSubOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                totalItemsPrice += itemInOrder.getTotalPrice();
                priceForThisStore += itemInOrder.getTotalPrice();
                totalItemsPriceForSubOrder += itemInOrder.getTotalPrice();
                //totalDeliveryCostForSubOrder += itemInOrder.getTotalPrice();
                this.stores.get(storeOfFinishOrder.getId()).getProducts().get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                //checkNumberOfItems.put(itemInOrder.getIdStore(), itemInOrder.getIdStore() );

            }
            numberOfItems+=storeOfFinishOrder.getItemsFromOrder().size();
            numberOfItems+=storeOfFinishOrder.getItemsFromOffer().size();
            //numberOfItems = checkNumberOfItems.size();
            totalDeliveryCost += storeOfFinishOrder.getDelivery();
            totalDeliveryCostForSubOrder += storeOfFinishOrder.getDelivery();

            totalPriceForSubOrder = totalDeliveryCostForSubOrder + totalItemsPriceForSubOrder;



            storesOfOrder.put(storeOfFinishOrder.getId(), this.stores.get(storeOfFinishOrder.getId()));
            if(storesFinishOrder.size() > 1){
                Map<Integer, Store> storesOfSubOrder = new HashMap<>();
                storesOfOrder.put(storeOfFinishOrder.getId(),this.stores.get(storeOfFinishOrder.getId()));
                Order orderFromSubOrder = new Order(date, storesOfSubOrder, productsOfSubOrder, serialNumber, storeOfFinishOrder.getItemsFromOrder().size() + storeOfFinishOrder.getItemsFromOffer().size(), totalItemsPriceForSubOrder, totalDeliveryCostForSubOrder, totalPriceForSubOrder);
                orderFromSubOrder.setX(x);
                orderFromSubOrder.setY(y);
                orderFromSubOrder.setAllTheItemsOfTheOrder(dtoItemsForSubOrder);
                subOrders.add(orderFromSubOrder);
                this.stores.get(storeOfFinishOrder.getId()).addOrder(orderFromSubOrder);
                serialNumber++;

                manager.getUser(this.stores.get(storeOfFinishOrder.getId()).getOwnerName())
                        .addOrderAlert(new NewOrderAlert(orderFromSubOrder.getId(), username, orderFromSubOrder.getNumberOfItems(), orderFromSubOrder.getPriceProducts(), orderFromSubOrder.getPriceDelivery(), storeOfFinishOrder.getName()));
            }

            priceForThisStore += storeOfFinishOrder.getDelivery();

            priceForThisStore = Math.round(priceForThisStore * 100.0) / 100.0;

            newTransaction(manager, this.stores.get(storeOfFinishOrder.getId()).getOwnerName(), new Transaction(priceForThisStore, TransactionType.RECEIVING_PAYMENT, date));


        }

        totalPrice += totalItemsPrice + totalDeliveryCost;

        totalPrice = Math.round(totalPrice * 100.0) / 100.0;
        totalDeliveryCost = Math.round(totalDeliveryCost * 100.0) / 100.0;



        if(storesFinishOrder.size() > 1){
            Order order = new Order(date, storesOfOrder, productsOfOrder, subOrders, numberOfItems,totalItemsPrice, totalDeliveryCost, totalPrice, storesFinishOrder);
            order.setCustomerName(username);
            order.setAllTheItemsOfTheOrder(dtoItems);
            order.setX(x);
            order.setY(y);
            this.orders.put(order.getId(), order);
        }

        else{
            Order order = new Order(date, storesOfOrder, productsOfOrder, numberOfItems,totalItemsPrice, totalDeliveryCost, totalPrice, storesFinishOrder);
            this.stores.get(storesFinishOrder.get(0).getId()).addOrder(order);

            order.setCustomerName(username);
            order.setAllTheItemsOfTheOrder(dtoItems);
            order.setX(x);
            order.setY(y);
            this.orders.put(order.getId(), order);

            manager.getUser(this.stores.get(storesFinishOrder.get(0).getId()).getOwnerName())
                    .addOrderAlert(new NewOrderAlert(order.getId(), username, order.getNumberOfItems(), order.getPriceProducts(), order.getPriceDelivery(), storesFinishOrder.get(0).getName()));
        }


        newTransaction(manager, username, new Transaction(totalPrice, TransactionType.PAYMENT_TRANSFER, date));
    }

    private void newTransaction(UserManager manager, String user, Transaction transaction){
        manager.getUser(user).getBalance().addTransaction(transaction);
    }

/*    @Override
    public void makeOrder(List<DTOStore> storesFinishOrder, DTOCustomer customer, LocalDate date) {
        List<Order> subOrders = new ArrayList<>();
        Map<Integer, Store> storesOfOrder = new HashMap<>();
        Map<Integer, Double> productsOfOrder = new HashMap<>();
        //Map<Integer, Integer> checkNumberOfItems = new HashMap<>();
        double totalItemsPrice=0, totalDeliveryCost=0, totalPrice=0;
        int numberOfItems=0;
        int serialNumber = 1;


        for (DTOStore storeOfFinishOrder: storesFinishOrder){
            Map<Integer, Double> productsOfSubOrder = new HashMap<>();

            this.stores.get(storeOfFinishOrder.getId()).addNumberProductsSold(storeOfFinishOrder.getItemsFromOrder().size());
            this.stores.get(storeOfFinishOrder.getId()).addPriceOfAllDeliveries(storeOfFinishOrder.getDelivery());

            for (DTOItem itemInOrder: storeOfFinishOrder.getItemsFromOrder()){
                this.products.get(itemInOrder.getSerialNumber()).increaseNumberSoldInTheMarket();
                this.products.get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                productsOfOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                productsOfSubOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                totalItemsPrice += itemInOrder.getTotalPrice();
                this.stores.get(storeOfFinishOrder.getId()).getProducts().get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                //checkNumberOfItems.put(itemInOrder.getIdStore(), itemInOrder.getIdStore() );
            }
            for (DTOItem itemInOrder: storeOfFinishOrder.getItemsFromOffer()){
                this.products.get(itemInOrder.getSerialNumber()).increaseNumberSoldInTheMarket();
                this.products.get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                productsOfOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                productsOfSubOrder.put(itemInOrder.getSerialNumber(), itemInOrder.getQuantityChoosen());
                totalItemsPrice += itemInOrder.getTotalPrice();
                this.stores.get(storeOfFinishOrder.getId()).getProducts().get(itemInOrder.getSerialNumber()).addQuantitySold(itemInOrder.getQuantityChoosen());
                //checkNumberOfItems.put(itemInOrder.getIdStore(), itemInOrder.getIdStore() );

            }
            numberOfItems+=storeOfFinishOrder.getItemsFromOrder().size();
            numberOfItems+=storeOfFinishOrder.getItemsFromOffer().size();
            //numberOfItems = checkNumberOfItems.size();
            totalDeliveryCost += storeOfFinishOrder.getDelivery();
            totalPrice += totalItemsPrice + totalDeliveryCost;


            storesOfOrder.put(storeOfFinishOrder.getId(), this.stores.get(storeOfFinishOrder.getId()));
            if(storesFinishOrder.size() > 1){
                Map<Integer, Store> storesOfSubOrder = new HashMap<>();
                storesOfOrder.put(storeOfFinishOrder.getId(),this.stores.get(storeOfFinishOrder.getId()));
                Order orderFromSubOrder = new Order(date, storesOfSubOrder, productsOfSubOrder, serialNumber, storeOfFinishOrder.getItemsFromOrder().size() + storeOfFinishOrder.getItemsFromOffer().size(), totalItemsPrice, totalDeliveryCost, totalPrice);
                subOrders.add(orderFromSubOrder);
                this.stores.get(storeOfFinishOrder.getId()).addOrder(orderFromSubOrder);
                serialNumber++;
            }
        }

        if(storesFinishOrder.size() > 1){
            Order order = new Order(date, storesOfOrder, productsOfOrder, subOrders, numberOfItems,totalItemsPrice, totalDeliveryCost, totalPrice, storesFinishOrder);
            this.customers.get(customer.getId()).addOrder(order);
            this.orders.put(order.getId(), order);
        }

        else{
            Order order = new Order(date, storesOfOrder, productsOfOrder, numberOfItems,totalItemsPrice, totalDeliveryCost, totalPrice, storesFinishOrder);
            this.stores.get(storesFinishOrder.get(0).getId()).addOrder(order);

            this.customers.get(customer.getId()).addOrder(order);
            this.orders.put(order.getId(), order);
        }


        //newTransaction()

    }*/



    private Map<Integer, DTOStore> getDataStores() {
        Map<Integer, DTOStore> stores = new HashMap<Integer, DTOStore>();
        String offerString, location;
        int numberOfOrders;
       // double priceItemSold = 0;

        for (Store store : this.stores.values()) {
            double priceItemSold = 0;
            Map<Integer, DTOItem> items = new HashMap<Integer, DTOItem>();
            for (Sell sell : store.getProducts().values()) {
                items.put(sell.getProduct().getSerialNumber(), new DTOItem(sell.getProduct().getProductName(), sell.getProduct().getSerialNumber(), sell.getProduct().getPurchaseMode().toString(), sell.getQuantitySold(), sell.getPrice()));
            }

            numberOfOrders = store.getOrders().size();
            location = "["+ store.getLocation().getX() +", " + store.getLocation().getY() + "]";

            for (Order order: store.getOrders()){
                priceItemSold += order.getPriceProducts();
            }

            /*Set<DTOOrder> orders = new HashSet<DTOOrder>();
            if (store.getOrders().size() != 0) {
                getDataOrders(store, orders);
            } else {
                orders = null;
            }*/
           /* List<DTODiscount> discounts =  new ArrayList<>();
            if(store.getDiscounts() != null){
                for(Discount discount: store.getDiscounts()){
                    List<String> offers =new ArrayList<>();
                    for (Offer offer: discount.getThenYouGet().getOffers()){
                        offerString= offer.getQuantity() + " of " + products.get(offer.getItemID()).getProductName()  + " for an additional of " + offer.getForAdditional();
                        offers.add(offerString);
                    }
                    discounts.add(new DTODiscount(discount.getName(), discount.getIfYouBuy().toString(), discount.getThenYouGet().getOperator().name(), offers));

                }
            }
            else{
                discounts=null;
            }*/

           stores.put(store.getID(), new DTOStore(store.getID(), store.getStoreName(), items, location, store.getPPK(), numberOfOrders, Double.parseDouble(df.format(priceItemSold)), Double.parseDouble(df.format(store.getPriceOfAllDeliveries())), this.ownerName, store.getLocation().getX(), store.getLocation().getY()));

        }

        return stores;
    }

    private Map<Integer, DTOItem> getDataITems() {
        Map<Integer, DTOItem> items = new HashMap<Integer, DTOItem>();
        int numberOfStores;
        double averagePrice;

        for (Product product : this.getProducts().values()) {
            numberOfStores = numberOfStores(product.getSerialNumber());
            averagePrice = averagePrice(product.getSerialNumber()) / numberOfStores;
            averagePrice = Math.round(averagePrice * 100.0) / 100.0;
            items.put(product.getSerialNumber(), new DTOItem(product.getSerialNumber(), product.getProductName(), product.getPurchaseMode().toString(), numberOfStores, Double.parseDouble(df.format(averagePrice)), product.getQuantitySold()));
        }

        return items;
    }

    private void getDataOrders(Store store, List<DTOOrder> orders) {
        for (Order order : store.getOrders()) {
            Map<Integer, String> storesInOrder = new HashMap<Integer, String>();
            List<DTOItem> items =order.getAllTheItemsOfTheOrder();
            String location = "[" + order.getX() + ", " + order.getY() + "]";

            for (Store storeInOrder : order.getStores().values()) {
                storesInOrder.put(storeInOrder.getID(), storeInOrder.getStoreName());
            }


            orders.add(new DTOOrder(String.valueOf(order.getDate()), order.getNumberOfItems(), Double.parseDouble(df.format(order.getPriceProducts())), Double.parseDouble(df.format(order.getPriceDelivery())), Double.parseDouble(df.format(order.getTotalPrice())),
                    order.getId(), storesInOrder, order.getProducts(), location, items));
        }

    }

    private int numberOfStores(int id) {
        int count = (int) this.stores.values().stream()
                .filter(item -> item.getProducts().containsKey(id))
                .count();

        return count;
    }

    private double averagePrice(int id) {
        double count = this.stores.values().stream()
                .filter(item -> item.getProducts().containsKey(id))
                .mapToDouble(item -> item.getProducts().getOrDefault(id, null).getPrice())
                .sum();


        return count;
    }

    @Override
    public void addItem(DTOItem newItem) {
        PurchaseMode mode;
        if(newItem.getPurchaseCategory().equals("Quantity")){
            mode = PurchaseMode.Quantity;
        }
        else{
            mode = PurchaseMode.Weight;
        }


        Product newProduct = new Product(newItem.getProductName(), newItem.getSerialNumber(), mode);
        newProduct.increaseNumberSoldInTheMarket();
        this.products.put(newProduct.getSerialNumber(), newProduct);

        for (Integer idStore: newItem.getIdStoreAndPrice().keySet()){
            this.stores.get(idStore).addNewProduct(newProduct, newItem.getIdStoreAndPrice().get(idStore));
        }

    }

    @Override
    public void addDiscount(DTODiscount newDiscount) {
        ItemToBuy ifYouBuy = new ItemToBuy(newDiscount.getIdIfYouBBuy(), newDiscount.getQuantityIfYouBuy(), this.products.get(newDiscount.getIdIfYouBBuy()).getProductName());

        OperatorOffer operator;
        if(newDiscount.getOperator().equals("IRRELEVANT")){
            operator = OperatorOffer.IRRELEVANT;
        } else if(newDiscount.getOperator().equals("ONE_OF")){
            operator = OperatorOffer.ONE_OF;
        } else{
            operator = OperatorOffer.ALL_OR_NOTHING;
        }

        List<Offer> offers = new ArrayList<>();

        for (DTOOffer offer : newDiscount.getOffers()){
            offers.add(new Offer(offer.getItemID(), offer.getQuantity(), offer.getForAdditional()));
        }

        ItemtoGet thenYouGet = new ItemtoGet(operator, offers);

        this.stores.get(newDiscount.getStoreId()).addDiscount(new Discount(newDiscount.getName(), ifYouBuy, thenYouGet));
    }

    @Override
    public boolean checkIDStore(int id) {
        int count = (int) this.stores.values().stream()
                .filter(store -> store.getID() == id)
                .count();
        if(count > 0){
            return false;
        }

        else{
            return true;
        }
    }

    @Override
    public boolean checkNameStore(String name) {
        int count = (int) this.stores.values().stream()
                .filter(store -> store.getStoreName().equals(name))
                .count();
        if(count > 0){
            return false;
        }

        else{
            return true;
        }
    }

    @Override
    public boolean checkLocation(int x, int y) {
        int count = (int) this.stores.values().stream()
                .filter(store -> store.getLocation().getX() == x && store.getLocation().getY() == y)
                .count();
        int count2 = (int) this.customers.values().stream()
                .filter(customer -> customer.getLocation().getX() == x && customer.getLocation().getY() == y)
                .count();
        if(count > 0 || count2 > 0){
            return false;
        }

        else{
            return true;
        }
    }

    @Override
    public boolean checkIDItem(int id) {
        int count = (int) this.products.values().stream()
                .filter(product -> product.getSerialNumber() == id)
                .count();
        if(count > 0){
            return false;
        }

        else{
            return true;
        }
    }

    @Override
    public boolean checkNameItem(String name) {
        int count = (int) this.products.values().stream()
                .filter(product -> product.getProductName().equals(name))
                .count();
        if(count > 0){
            return false;
        }

        else{
            return true;
        }
    }

    @Override
    public boolean checkNameDiscount(String name) {
        for (Store store: this.stores.values()){
            if(this.stores.get(store.getID()).getDiscounts() != null){
                for (Discount discount: this.stores.get(store.getID()).getDiscounts()){
                    if(discount.getName().equals(name)){
                        return false;
                    }
                }
            }
        }


        return true;
    }

    @Override
    public String getZoneName() {
        return this.zoneName;
    }

    @Override
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        this.allTheOwners.add(ownerName);
    }

    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    @Override
    public int getNumberOfItems() {
        return this.products.size();
    }

    @Override
    public int getNumberOfStores() {
        return this.stores.size();
    }

    @Override
    public int getNumberOfOrders() {
        return this.orders.size();
    }

    @Override
    public double getAveragePriceOrders() {
        double average =0, totalPrice = 0;
        int counter = 0;

        for (Order order: this.orders.values()){
            counter++;
            totalPrice += order.getPriceProducts();
        }
        if(counter!=0){
            average = Double.parseDouble(df.format(totalPrice / counter));
        }
        return average;
    }

    @Override
    public List<DTOOrder> getMyOrders(String userName) {
        List<DTOOrder> myOrders = new ArrayList<>();
        String location;
        //int counter = 1;

        for(Order order: this.orders.values()){
            if(order.getCustomerName().toLowerCase().equals(userName.toLowerCase())){
                List<DTOItem> myItems = order.getAllTheItemsOfTheOrder();

                location = "[" + order.getX() + ", " + order.getY() +"]";
                myOrders.add(new DTOOrder(order.getId(), String.valueOf(order.getDate()), location, order.getStores().size(), order.getNumberOfItems(),
                        Math.round(order.getPriceProducts() * 100.0) / 100.0, Math.round(order.getPriceDelivery() * 100.0) / 100.0, Math.round(order.getTotalPrice() * 100.0) / 100.0, myItems));

                //counter++;
            }
        }

        return myOrders;
    }

    @Override
    public List<DTOStore> getMyStores(String userName) {
        List<DTOStore> stores = new ArrayList<>();


        for (Store store: this.stores.values()){
            int size =0;
            if(store.getOwnerName().toLowerCase().equals(userName.toLowerCase())){
                List<DTOOrder> orders = new ArrayList<>();


                if (store.getOrders().size() != 0) {
                    size = store.getOrders().size();
                    getDataOrders(store, orders);
                } else {
                    orders = null;
                }
                stores.add(new DTOStore( store.getID(), store.getStoreName(), orders, size, store.getLocation().getX(), store.getLocation().getY()));
            }
        }

        return stores;
    }

    @Override
    public List<DTOFeedback> getMyFeedbacks(String userName) {
        List<DTOFeedback> myFeedbacks = new ArrayList<>();
        for (Feedback feedback : this.feedbacks){
            if(feedback.getOwnerName().toLowerCase().equals(userName.toLowerCase())){
                myFeedbacks.add(new DTOFeedback(feedback.getCustomerName(), feedback.getGrade(), feedback.getMessage(), feedback.getDate()));
            }
        }

        return myFeedbacks;
    }

    @Override
    public void addFeedback(int storeID, String customerName, String date, int grade, String message, UserManager manager) {
        String name = this.stores.get(storeID).getOwnerName();

        if(message.equals("")){
            message="There are no comment.";
        }

        this.feedbacks.add(new Feedback(storeID, customerName, date, grade, message, name));

        manager.getUser(name)
                .addFeedbackAlert(new NewFeedbackAlert(customerName, grade, message, date, this.stores.get(storeID).getStoreName()));

    }

    @Override
    public void addNewStore(String newOwner,String storeName, Map<Integer, DTOItem> items, int ppk, int x, int y, UserManager manager) {
        Coordinates location = new Coordinates(x, y);
        Map<Integer, Sell> products = new HashMap<>();
        String locationString = "[" + x + ", " + y + "]";
        String numberOfItems = items.size() + "/" + this.products.size();

        for (Integer idItem : items.keySet()){
            // why 2 times ????????????
            //this.products.get(idItem).increaseNumberSoldInTheMarket();
            products.put(idItem, new Sell(this.products.get(idItem), items.get(idItem).getPrice()));
            this.products.get(idItem).increaseNumberSoldInTheMarket();
        }

        Store newStore = new Store(storeName, ppk, products, location);
        newStore.setOwnerName(newOwner);

        this.stores.put(newStore.getID(), newStore);

        for (String nameOwner : this.allTheOwners){
            if(!nameOwner.toLowerCase().equals(newOwner.toLowerCase())){
                manager.getUser(nameOwner).addStoreAlert(new NewStoreAlert(newOwner, storeName,locationString, numberOfItems, this.zoneName));
            }
        }

        boolean checkPresence = false;
        for (String nameOwner : this.allTheOwners){
            if(nameOwner.toLowerCase().equals(newOwner.toLowerCase())){
                checkPresence = true;
                break;
            }
        }

        if(!checkPresence){
            this.allTheOwners.add(newOwner);
        }


    }


}
