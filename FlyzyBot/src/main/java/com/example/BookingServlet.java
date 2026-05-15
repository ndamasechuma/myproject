package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class BookingServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            // Load JDBC driver and open DB connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/flyzydb", "root", "97532468cL@"
            );
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // POST: /booking/addBooking
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        if ("/addBooking".equals(path)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                String type = req.getParameter("type");
                String customerName = req.getParameter("customerName");
                String details = req.getParameter("details");
                String status = req.getParameter("status");

                PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO bookings (id, type, customerName, details, status) VALUES (?, ?, ?, ?, ?)"
                );
                stmt.setInt(1, id);
                stmt.setString(2, type);
                stmt.setString(3, customerName);
                stmt.setString(4, details);
                stmt.setString(5, status);

                int rows = stmt.executeUpdate();

                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.println(rows + " booking(s) added successfully!");

            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }

    // GET: /booking/viewBookings
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        if ("/viewBookings".equals(path)) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM bookings");

                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();

                while (rs.next()) {
                    out.println("ID: " + rs.getInt("id") +
                                ", Type: " + rs.getString("type") +
                                ", Name: " + rs.getString("customerName") +
                                ", Details: " + rs.getString("details") +
                                ", Status: " + rs.getString("status"));
                }

            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }

    // PUT: /booking/updateBooking?id=1
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        if ("/updateBooking".equals(path)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));

                PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE bookings SET status = 'Confirmed' WHERE id = ?"
                );
                stmt.setInt(1, id);

                int rows = stmt.executeUpdate();

                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.println(rows + " booking(s) updated.");

            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }

    // DELETE: /booking/deleteBooking?id=1
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        if ("/deleteBooking".equals(path)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));

                PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM bookings WHERE id = ?"
                );
                stmt.setInt(1, id);

                int rows = stmt.executeUpdate();

                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.println(rows + " booking(s) deleted.");

            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null) conn.close();
        } catch (Exception ignored) {}
    }
}


