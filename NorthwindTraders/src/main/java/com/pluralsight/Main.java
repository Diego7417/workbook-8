package com.pluralsight;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Optional: Uncomment if the driver still isn't loading
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to Northwind DB
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root"; // use your actual MySQL username
        String password = "yearup"; // use your actual password

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        // Query all product names
        String query = "SELECT ProductName FROM Products";
        ResultSet results = statement.executeQuery(query);

        // Print each product name
        while (results.next()) {
            String productName = results.getString("ProductName");
            System.out.println(productName);
        }

        // Cleanup
        results.close();
        statement.close();
        connection.close();
    }
}
