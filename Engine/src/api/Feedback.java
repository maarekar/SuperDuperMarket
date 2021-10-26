package api;

public class Feedback {
    private String ownerName;
    private String customerName;
    private String date;
    private int grade;
    private String message;
    private int storeID;

    public Feedback(int storeID, String customerName, String date, int grade, String message, String ownerName) {
        this.storeID = storeID;
        this.customerName = customerName;
        this.date = date;
        this.grade = grade;
        this.message = message;
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDate() {
        return date;
    }

    public int getGrade() {
        return grade;
    }

    public String getMessage() {
        return message;
    }
}
