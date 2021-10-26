package api.users;

import api.DTOFeedback;
import api.DTOTransaction;
import api.chat.SingleChatEntry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class User {
    protected static int serialNumber =1;
    private String name;
    private String accountType;
    private Balance balance;
    private List<NewOrderAlert> ordersAlert = new ArrayList<>();
    private List<NewStoreAlert> storesAlert = new ArrayList<>();
    private List<NewFeedbackAlert> feedbacksAlert = new ArrayList<>();

    public User(String name, String accountType) {
        this.name=name;
        this.accountType = accountType;
        this.balance = new Balance();
    }

    public Balance getBalance(){
        return this.balance;
    }

    public String getName() {
        return name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<DTOTransaction> getAllTransactions() throws ParseException {
        List<DTOTransaction> transactions = new ArrayList<>();

        for (Transaction transaction: balance.getTransactions()){

                transactions.add(new DTOTransaction(transaction));


        }

        return transactions;
    }

    public int getOrderVersion() {
        return this.ordersAlert.size();
    }

    public synchronized List<NewOrderAlert> getOrdersAlertEntries(int fromIndex){
        if (fromIndex < 0 || fromIndex > ordersAlert.size()) {
            fromIndex = 0;
        }
        return ordersAlert.subList(fromIndex, ordersAlert.size());
    }

    public int getNewStoreVersion() {
        return this.storesAlert.size();
    }

    public synchronized List<NewStoreAlert> getNewStoresAlertEntries(int fromIndex){
        if (fromIndex < 0 || fromIndex > storesAlert.size()) {
            fromIndex = 0;
        }
        return storesAlert.subList(fromIndex, storesAlert.size());
    }

    public int getFeedbackVersion() {
        return this.feedbacksAlert.size();
    }

    public synchronized List<NewFeedbackAlert> getFeedbacksAlerts(int fromIndex){
        if (fromIndex < 0 || fromIndex > feedbacksAlert.size()) {
            fromIndex = 0;
        }
        return feedbacksAlert.subList(fromIndex, feedbacksAlert.size());
    }


    public void addFeedbackAlert(NewFeedbackAlert alert){
        this.feedbacksAlert.add(alert);
    }


    public void addStoreAlert(NewStoreAlert alert){
        this.storesAlert.add(alert);
    }

    public void addOrderAlert(NewOrderAlert alert){
        this.ordersAlert.add(alert);
    }
}
