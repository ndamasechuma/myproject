package com.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/flyzydb";
    private final String dbUser = "root";
    private final String dbPassword = "97532468cL@";

    // Add a new booking
    public void addBooking(Booking booking) {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "INSERT INTO bookings (id, type, customerName, details, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, booking.getId());
            stmt.setString(2, booking.getType());
            stmt.setString(3, booking.getCustomerName());
            stmt.setString(4, booking.getDetails());
            stmt.setString(5, booking.getStatus());
            stmt.executeUpdate();
            System.out.println("Booking added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View all bookings
    public void viewBookings() {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "SELECT * FROM bookings";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("---- Bookings ----");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id")
                    + ", Type: " + rs.getString("type")
                    + ", Name: " + rs.getString("customerName")
                    + ", Details: " + rs.getString("details")
                    + ", Status: " + rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update booking status to Confirmed
    public void confirmBooking(int id) {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "UPDATE bookings SET status = 'Confirmed' WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Booking status updated to Confirmed.");
            } else {
                System.out.println("Booking not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete a booking by ID
    public void deleteBooking(int id) {
        try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            String sql = "DELETE FROM bookings WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Booking deleted successfully.");
            } else {
                System.out.println("Booking not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



