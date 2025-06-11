package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/northwind";
            String username = "root";
            String password = "yearup";

            connection = DriverManager.getConnection(url, username, password);

            boolean running = true;
            while (running) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        displayProducts(connection);
                        break;
                    case 2:
                        displayCustomers(connection);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting the program.");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    private static void displayProducts(Connection connection) throws SQLException {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        System.out.printf("%-10s %-40s %12s %10s%n", "Id", "Product Name", "Unit Price", "Stock");
        System.out.println("--------------------------------------------------------------------------");

        while (results.next()) {
            int id = results.getInt("ProductID");
            String name = results.getString("ProductName");
            double price = results.getDouble("UnitPrice");
            int stock = results.getInt("UnitsInStock");

            System.out.printf("%-10d %-40s %11.2f %10d", id, name, price, stock);
            if (stock < 10) {
                System.out.print(" <-- Low Stock");
            }
            System.out.println();
        }

        results.close();
        statement.close();
    }

    private static void displayCustomers(Connection connection) throws SQLException {
        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        System.out.printf("%-25s %-40s %-20s %-15s %-17s%n",
                "Contact Name", "Company Name", "City", "Country", "Phone");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        while (results.next()) {
            String contact = results.getString("ContactName");
            String company = results.getString("CompanyName");
            String city = results.getString("City");
            String country = results.getString("Country");
            String phone = results.getString("Phone");

            System.out.printf("%-25s %-40s %-20s %-15s %-15s%n",
                    contact, company, city, country, phone);
        }

        results.close();
        statement.close();
    }
}
