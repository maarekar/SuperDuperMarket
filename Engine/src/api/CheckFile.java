package api;

import Exceptions.*;
import Generated3.*;

import java.util.List;

public class CheckFile {
    private SuperDuperMarketDescriptor SDM;

    public CheckFile(SuperDuperMarketDescriptor SDM) {
        this.SDM = SDM;
    }

    public void checkTheFile() throws MessageException {
        this.checkIDItems();
        this.checkIDStores();
        this.checkIfItemIsInStore();
        this.checkIfSameItemInStore();
        this.checkItemIsExit();
        this.checkStoreAndCustomerLocation();
        //this.checkIDCustomers();
        this.checkItemInDiscount();
    }

    private void checkIDStores() throws MessageException {
        List<SDMStore> stores = this.SDM.getSDMStores().getSDMStore();

        for (int i = 0; i < stores.size() - 1; i++) {

            for (int j = i + 1; j < stores.size() - 1; j++) {
                if (stores.get(i).getId() == stores.get(j).getId()) {
                    throw new MessageException("There are 2 stores with the same ID in this file!");
                }
            }
        }
    }

    private void checkIDItems() throws MessageException {
        List<SDMItem> items = this.SDM.getSDMItems().getSDMItem();

        for (int i = 0; i < items.size() - 1; i++) {

            for (int j = i + 1; j < items.size() - 1; j++) {
                if (items.get(i).getId() == items.get(j).getId()) {
                    throw new MessageException("There are 2 items with the same ID in this file!");
                }
            }
        }
    }

    private void checkItemIsExit() throws MessageException {
        List<SDMStore> stores = this.SDM.getSDMStores().getSDMStore();
        boolean check;

        for (SDMStore store : stores) {
            for (SDMSell itemInStore : store.getSDMPrices().getSDMSell()) {
                check = false;
                for (SDMItem item : this.SDM.getSDMItems().getSDMItem()) {
                    if (itemInStore.getItemId() == item.getId()) {
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    throw new MessageException("The item with the ID: " + itemInStore.getItemId() + " in the store " + store.getName() + " does not exist in this file!");
                }
            }
        }
    }

    private void checkIfItemIsInStore() throws MessageException {
        List<SDMItem> items = this.SDM.getSDMItems().getSDMItem();
        List<SDMStore> stores = this.SDM.getSDMStores().getSDMStore();
        boolean check = false;

        for (SDMItem item : items) {
            check = false;
            for (SDMStore store : stores) {
                for (SDMSell itemInStore : store.getSDMPrices().getSDMSell()) {
                    if (itemInStore.getItemId() == item.getId()) {
                        check = true;
                        break;
                    }
                }
                if (check) {
                    break;
                }
            }
            if (!check) {
                throw new MessageException("The item " + item.getName() + " with the ID: " + item.getId() + " is not sold in a store!");
            }
        }
    }

    private void checkIfSameItemInStore() throws MessageException {
        List<SDMStore> stores = this.SDM.getSDMStores().getSDMStore();
        List<SDMSell> items;
        SDMSell currentItem;

        for (SDMStore store : stores) {
            items = store.getSDMPrices().getSDMSell();
            for (int i = 0; i < items.size(); i++) {
                currentItem = items.get(i);
                for (int j = i + 1; j < items.size() - 1; j++) {
                    if (currentItem.getItemId() == items.get(j).getItemId()) {
                        throw new MessageException("The item with the ID: " + currentItem.getItemId() + " is present more than one time in the store: " + store.getName() + " !");
                    }
                }
            }
        }
    }

    private void checkStoreAndCustomerLocation() throws MessageException {
        List<SDMStore> stores = this.SDM.getSDMStores().getSDMStore();
        //List<SDMCustomer> customers = this.SDM.getSDMCustomers().getSDMCustomer();

        for (SDMStore store : stores) {
            if (store.getLocation().getX() < 1 || store.getLocation().getX() > 50 ||
                    store.getLocation().getY() < 1 || store.getLocation().getY() > 50) {
                throw new MessageException("The location of the store " + store.getName() + " with the ID: " + store.getId() + " is incorrect");
            }
            for (SDMStore store2 : stores) {
                if (store.getId() != store2.getId() && store.getLocation().getX() == store2.getLocation().getX() && store.getLocation().getY() == store2.getLocation().getY()) {
                    throw new MessageException("There are 2 stores with the same location in this file!");
                }
            }
            /*for (SDMCustomer customer : customers) {
                if (customer.getLocation().getX() < 1 || customer.getLocation().getX() > 50 ||
                        customer.getLocation().getY() < 1 || customer.getLocation().getY() > 50) {
                    throw new MessageException("The location of the customer " + customer.getName() + " with the ID: " + customer.getId() + " is incorrect");
                }
                if (store.getLocation().getX() == customer.getLocation().getX() && store.getLocation().getY() == customer.getLocation().getY()) {
                    throw new MessageException("There are a store and a customer with the same location in this file!");
                }
                for (SDMCustomer customer2 : customers) {
                    if (customer.getId() != customer2.getId() && customer.getLocation().getX() == customer2.getLocation().getX() && customer.getLocation().getY() == customer2.getLocation().getY()) {
                        throw new MessageException("There are 2 customers with the same location in this file!");
                    }
                }
            }*/
        }
    }

    /*private void checkIDCustomers() throws MessageException {
        List<SDMCustomer> customers = this.SDM.getSDMCustomers().getSDMCustomer();

        for (int i = 0; i < customers.size() - 1; i++) {

            for (int j = i + 1; j < customers.size() - 1; j++) {
                if (customers.get(i).getId() == customers.get(j).getId()) {
                    throw new MessageException("There are 2 customers with the same ID in this file !");
                }
            }
        }
    }*/

    private void checkItemInDiscount() throws MessageException {
        List<SDMStore> stores = this.SDM.getSDMStores().getSDMStore();
        List<SDMDiscount> discounts;
        boolean check = false;


        for (SDMStore store : stores) {
            if(store.getSDMDiscounts() != null){
                discounts = store.getSDMDiscounts().getSDMDiscount();
                for (SDMDiscount discount : discounts) {
                    for (SDMSell itemInStore : store.getSDMPrices().getSDMSell()) {
                        if (itemInStore.getItemId() == discount.getIfYouBuy().getItemId()) {
                            check = true;
                            break;
                        }
                    }
                    if (!check) {
                        throw new MessageException("The item with the ID: " + discount.getIfYouBuy().getItemId() + " in the discount " + discount.getName() + " is not sold in the store: " + store.getName() + " !");
                    }
                    check=false;
                    for (SDMOffer offer: discount.getThenYouGet().getSDMOffer()){
                        for (SDMSell itemInStore : store.getSDMPrices().getSDMSell()) {
                            if (itemInStore.getItemId() == offer.getItemId()) {
                                check = true;
                                break;
                            }
                        }
                        if (!check) {
                            throw new MessageException("The item with the ID: " + offer.getItemId() + " in the discount " + discount.getName() + " is not sold in the store: " + store.getName() + " !");
                        }

                    }
                }
                check=false;
            }

        }
    }
}
