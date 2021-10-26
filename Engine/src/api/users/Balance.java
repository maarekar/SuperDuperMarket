package api.users;

import java.util.ArrayList;
import java.util.List;
import Enum.TransactionType;


public class Balance {
    private double balance;
    private List<Transaction> transactions;

    public Balance() {
        this.transactions = new ArrayList<>();
        this.balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction newTransaction){
        newTransaction.setAmountBefore(this.balance);
        if(newTransaction.getType() == TransactionType.PAYMENT_TRANSFER){
            balance -= newTransaction.getAmount();
        } else{
            balance += newTransaction.getAmount();
        }
        balance = Math.round(balance * 100.0) / 100.0;

        newTransaction.setAmountAfter(this.balance);
        transactions.add(newTransaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
