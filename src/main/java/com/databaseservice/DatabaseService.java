package com.databaseservice;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class DatabaseService {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll_service?useSSL=false";
        String username = "root";
        String password = "Dinnu@247";
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw  new IllegalStateException("Cannot find database driver in the Classpath", e);
        }
        listDriver();
        try {
            System.out.println("Connecting to database: " +jdbcURL);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection is Successful! " +connection);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private static void  listDriver() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("  " +driverClass.getClass().getName());
        }
    }
}
