package api.users;

public class NewFeedbackAlert {
    private String customerName;
    private int rating;
    private String message;
    private String date;
    private String storeName;

    public NewFeedbackAlert(String customerName, int rating, String message, String date, String storeName) {
        this.customerName = customerName;
        this.rating = rating;
        this.message = message;
        this.date = date;
        this.storeName =storeName;
    }
}
