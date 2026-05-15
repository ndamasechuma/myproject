package com.example;

public class Booking {
	    private int id;
	    private String type;
	    private String customerName;
	    private String details;
	    private String status;

	    // Constructor
	    public Booking(int id, String type, String customerName, String details, String status) {
	        this.id = id;
	        this.type = type;
	        this.customerName = customerName;
	        this.details = details;
	        this.status = status;
	    }

	    // Getters and Setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }

	    public String getType() { return type; }
	    public void setType(String type) { this.type = type; }

	    public String getCustomerName() { return customerName; }
	    public void setCustomerName(String customerName) { this.customerName = customerName; }

	    public String getDetails() { return details; }
	    public void setDetails(String details) { this.details = details; }

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }
	}


