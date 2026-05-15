package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BookingApp {

    public static void main(String[] args) {

        String jdbcURL = "jdbc:mysql://localhost:3306/flyzydb";
        String dbUser = "root";
        String dbPassword = "97532468cL@";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Prepare SQL Insert Query
            String sql = "INSERT INTO bookings (id, type, customerName, details, status) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            // Sample Bookings
            Booking b1 = new Booking(1, "Flight", "Chuma Ndamase", "Flight No: AI202", "Confirmed");
            Booking b2 = new Booking(2, "Hotel", "Liyabona Mzoyi", "Hotel: Taj Palace", "Pending");
            Booking b3 = new Booking(3, "Cab", "Yongama Bidie", "Cab No: MH12AB1234", "Confirmed");

            // Insert Booking 1
            statement.setInt(1, b1.getId());
            statement.setString(2, b1.getType());
            statement.setString(3, b1.getCustomerName());
            statement.setString(4, b1.getDetails());
            statement.setString(5, b1.getStatus());
            statement.executeUpdate();

            // Booking 2
            statement.setInt(1, b2.getId());
            statement.setString(2, b2.getType());
            statement.setString(3, b2.getCustomerName());
            statement.setString(4, b2.getDetails());
            statement.setString(5, b2.getStatus());
            statement.executeUpdate();

            // Booking 3
            statement.setInt(1, b3.getId());
            statement.setString(2, b3.getType());
            statement.setString(3, b3.getCustomerName());
            statement.setString(4, b3.getDetails());
            statement.setString(5, b3.getStatus());
            statement.executeUpdate();

            // Close Connection
            connection.close();

            System.out.println("Bookings inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



