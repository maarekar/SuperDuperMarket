package api.users;

import Enum.TransactionType;

import java.util.Date;


public class Transaction {
    private double amount;
    private TransactionType type;
    private String date;
    private double amountBefore;
    private double amountAfter;

    public Transaction(double amount, TransactionType type, String date) {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(double amountBefore) {
        this.amountBefore = amountBefore;
    }

    public double getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(double amountAfter) {
        this.amountAfter = amountAfter;
    }
}
