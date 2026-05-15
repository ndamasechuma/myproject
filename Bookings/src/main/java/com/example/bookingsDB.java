package main.java.com.example;


import java.sql.*;
import java.util.Scanner;
import java.util.UUID;

public class bookingsDB {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/booking_system";
    private static final String USER = "root"; 
    private static final String PASSWORD = "97532468cL@"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nBooking Management System");
            System.out.println("1. Add Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. Update Booking");
            System.out.println("4. Delete Booking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBooking(scanner);
                    break;
                case 2:
                    viewBookings();
                    break;
                case 3:
                    updateBooking(scanner);
                    break;
                case 4:
                    deleteBooking(scanner);
                    break;
                case 5:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    
    private static void addBooking(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO bookings (id, type, customer_name, details, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, UUID.randomUUID().toString());

                System.out.print("Enter booking type (flight/hotel/cab): ");
                statement.setString(2, scanner.nextLine());

                System.out.print("Enter customer name: ");
                statement.setString(3, scanner.nextLine());

                System.out.print("Enter booking details: ");
                statement.setString(4, scanner.nextLine());

                System.out.print("Enter booking status (confirmed/pending): ");
                statement.setString(5, scanner.nextLine());

                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Booking added successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private static void viewBookings() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM bookings";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                System.out.println("\nAll Bookings:");
                while (resultSet.next()) {
                    System.out.printf("ID: %s | Type: %s | Name: %s | Details: %s | Status: %s\n",
                            resultSet.getString("id"),
                            resultSet.getString("type"),
                            resultSet.getString("customer_name"),
                            resultSet.getString("details"),
                            resultSet.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private static void updateBooking(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            System.out.print("Enter booking ID to confirm: ");
            String bookingId = scanner.nextLine();

            String sql = "UPDATE bookings SET status = 'confirmed' WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, bookingId);

                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Booking confirmed successfully.");
                } else {
                    System.out.println("No booking found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    private static void deleteBooking(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            System.out.print("Enter booking ID to delete: ");
            String bookingId = scanner.nextLine();

            String sql = "DELETE FROM bookings WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, bookingId);

                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Booking deleted successfully.");
                } else {
                    System.out.println("No booking found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

