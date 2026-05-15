package com.example;


import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        BookingDAO dao = new BookingDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Flyzy Booking Management ====");
            System.out.println("1. Add Booking");
            System.out.println("2. View All Bookings");
            System.out.println("3. Confirm Booking Status");
            System.out.println("4. Delete Booking");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Booking ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Type (Flight/Hotel/Cab): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Details: ");
                    String details = scanner.nextLine();
                    System.out.print("Enter Status (Confirmed/Pending): ");
                    String status = scanner.nextLine();
                    Booking booking = new Booking(id, type, name, details, status);
                    dao.addBooking(booking);
                    break;

                case 2:
                    dao.viewBookings();
                    break;

                case 3:
                    System.out.print("Enter Booking ID to Confirm: ");
                    int confirmId = scanner.nextInt();
                    dao.confirmBooking(confirmId);
                    break;

                case 4:
                    System.out.print("Enter Booking ID to Delete: ");
                    int deleteId = scanner.nextInt();
                    dao.deleteBooking(deleteId);
                    break;

                case 5:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}



