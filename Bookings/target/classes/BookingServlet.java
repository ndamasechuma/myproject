package com.example;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/bookings/*")
public class BookingServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/booking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "97532468cL@";
    private static final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Add Booking (POST /bookings/add)
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO bookings (id, type, customer_name, details, status) VALUES (?, ?, ?, ?, ?)")) {

            BufferedReader reader = request.getReader();
            Booking booking = gson.fromJson(reader, Booking.class);

            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, booking.getType());
            stmt.setString(3, booking.getCustomerName());
            stmt.setString(4, booking.getDetails());
            stmt.setString(5, booking.getStatus());
            stmt.executeUpdate();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Booking added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // View All Bookings (GET /bookings/view)
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bookings")) {

            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getString("id"),
                        rs.getString("type"),
                        rs.getString("customer_name"),
                        rs.getString("details"),
                        rs.getString("status")
                ));
            }
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(bookings));

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Update Booking (PUT /bookings/update?id=123)
        String id = request.getParameter("id");
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing booking ID.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE bookings SET status = 'confirmed' WHERE id = ?")) {

            stmt.setString(1, id);
            int updated = stmt.executeUpdate();

            if (updated > 0) {
                response.getWriter().write("Booking updated successfully.");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Delete Booking (DELETE /bookings/delete?id=123)
        String id = request.getParameter("id");
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing booking ID.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookings WHERE id = ?")) {

            stmt.setString(1, id);
            int deleted = stmt.executeUpdate();

            if (deleted > 0) {
                response.getWriter().write("Booking deleted successfully.");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
}

