package api;

import api.users.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DTOTransaction {
    private String type;
    private String date;
    private double amount;
    private double amountBefore;
    private double amountAfter;
    //private SimpleDateFormat format = new SimpleDateFormat("dd/mm-HH:mm");

    public DTOTransaction(Transaction transaction) throws ParseException {
        this.type = transaction.getType().name();
        this.date = transaction.getDate().toString();
        this.amount = transaction.getAmount();
        this.amountBefore = transaction.getAmountBefore();
        this.amountAfter = transaction.getAmountAfter();
    }
}
