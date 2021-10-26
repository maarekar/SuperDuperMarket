package api;

public class DTOFeedback {
    private String customerName;
    private int rating;
    private String message;
    private String date;

    public DTOFeedback(String customerName, int rating, String message, String date) {
        this.customerName = customerName;
        this.rating = rating;
        this.message = message;
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRating() {
        return rating;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

}
