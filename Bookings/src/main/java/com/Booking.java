package main.java.com;


public class Booking {
    private String type;
    private String customerName;
    private String details;
    private String status;

    public Booking(String id, String type, String customerName, String details, String status) {
        this.type = type;
        this.customerName = customerName;
        this.details = details;
        this.status = status;
    }

    public String getType() { return type; }
    public String getCustomerName() { return customerName; }
    public String getDetails() { return details; }
    public String getStatus() { return status; }
}
