package com.databaseservice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService  {
    //declared private variables
    private PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;

    //created private default constructor
    private EmployeePayrollDBService() {

    }

    /**
     * created singleton design pattern to single instance by using getInstance() method
     * @return employeePayrollDBService
     */
    public static EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

    /**
     * created getConnection() method to make connection with mysql database
     * @return connection
     * @throws SQLException sql exception
     */
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String username = "root";
        String password = "Dinnu@247";
        Connection connection;
        System.out.println("Connecting to database " +jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connection is Successful! " +connection);
        return connection;
    }

    /**
     * created a readData() method to read data from database table
     * added try and catch block to throw sql exception
     * @return employeePayrollDataList
     */
    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_payroll; ";
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    /**
     * created a updateEmployeeData method to update data in database table
     * added try and catch block to throw sql exception
     * @param name name
     * @param salary salary
     * @return this.updateEmployeeDataUsingStatement(name, salary)
     */
    public int updateEmployeeData(String name, double salary) {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }

    /**
     * created updateEmployeeDataUsingStatement method to update data in database by using sql query
     * added try and catch block to throw sql exception
     * @param name name
     * @param salary salary
     * @return statement.executeUpdate(sql)
     */
    private int updateEmployeeDataUsingStatement(String name, double salary) {
        String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    /**
     * created getEmployeePayrollData method to get data from database
     * added try and catch block to throw sql exception
     * @param name name
     * @return employeePayrollDataList
     */
    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayrollDataList = null;
        if (this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
         e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    /**
     * created private getEmployeePayrollData method to get all data from database table
     * added try and catch block to throw sql exception
     * @param resultSet resultSet
     * @return employeePayrollDataList
     */
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollDataList =new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, salary,startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    /**
     * prepareStatementForEmployeeData method for single query to get the data from database
     * added try and catch block to throw sql exception
     */
    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll Where name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
