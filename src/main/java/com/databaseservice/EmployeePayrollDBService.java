package com.databaseservice;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EmployeePayrollDBService  {
    //declared private variables
    private PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;

    // private default constructor
    private EmployeePayrollDBService() {

    }

    /**
     * used singleton design pattern to use  single instance at a time  by using getInstance() method
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
        return this.getEmployeePayrollDataUDINGDB(sql);
    }

    /**
     * created getEmployeePayrollDataForDateRange method to retrieve data from data base in a particular date range
     * @param startDate startDate in LocalDate format
     * @param endDate endDate in LocalDate format
     * @return this.getEmployeePayrollDataUDINGDB(sql)
     */
    public List<EmployeePayrollData> getEmployeePayrollDataForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM employee_payroll WHERE START BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate),Date.valueOf(endDate));
        return this.getEmployeePayrollDataUDINGDB(sql);
    }

    /**
     * created getEmployeePayrollDataUDINGDB method to retrieve data from database
     * by taking sql query input from
     * @param sql getEmployeePayrollDataForDateRange
     * @return employeePayrollDataList;
     */
    private List<EmployeePayrollData> getEmployeePayrollDataUDINGDB(String sql) {
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
     * created getAverageSalaryByGender method to get average salary group by gender data from database
     * by using mysql query in the method and mapped gender and averageSalary
     * @return genderToAverageSalaryMap
     */
    public Map<String, Double> getAverageSalaryByGender() {
        String sql = "SELECT gender, AVG(salary) as average_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double averageSalary = resultSet.getDouble("average_salary");
                genderToAverageSalaryMap.put(gender, averageSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToAverageSalaryMap;
    }

    /**
     * created getAverageSalaryByGender method to get sum salary group by gender data from database
     * by using mysql query in the method and mapped gender and sumSalary
     * @return genderToSumSalaryMap
     */
    public Map<String, Double> getSumSalaryByGender() {
        String sql = "SELECT gender, SUM(salary) as sum_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToSumSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double sumSalary = resultSet.getDouble("sum_salary");
                genderToSumSalaryMap.put(gender, sumSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToSumSalaryMap;
    }

    /**
     * created getMinimumSalaryByGender method to get minimum salary group by gender data from database
     * by using mysql query in the method and mapped gender and minimumSalary
     * @return genderToMinimumSalaryMap
     */
    public Map<String, Double> getMinimumSalaryByGender() {
        String sql = "SELECT gender, Min(salary) as minimum_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToMinimumSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double minimumSalary = resultSet.getDouble("minimum_salary");
                genderToMinimumSalaryMap.put(gender, minimumSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToMinimumSalaryMap;
    }

    /**
     * created getMaximumSalaryByGender method to get maximum salary group by gender data from database
     * by using mysql query in the method and mapped gender and maximumSalary
     * @return genderToMaximumSalaryMap
     */
    public Map<String, Double> getMaximumSalaryByGender() {
        String sql = "SELECT gender, Max(salary) as maximum_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToMaximumSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double maximumSalary = resultSet.getDouble("maximum_salary");
                genderToMaximumSalaryMap.put(gender, maximumSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToMaximumSalaryMap;
    }

    /**
     * created getCountNameByGender method to get count name group by gender data from database
     * by using mysql query in the method and mapped gender and countName
     * @return genderToCountNameMap
     */
    public Map<String, Integer> getCountNameByGender() {
        String sql = "SELECT gender, COUNT(name) as count_name FROM employee_payroll GROUP BY GENDER;";
        Map<String, Integer> genderToCountNameMap = new HashMap<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Integer countName = resultSet.getInt("count_name");
                genderToCountNameMap.put(gender, countName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToCountNameMap;
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

    /**
     * added new employee and payroll details to table in data base to database
     * @param employeeName employee name
     * @param gender gender
     * @param basic_pay salary
     * @param startDate start date
     * @return employeePayrollData
     */
    public EmployeePayrollData addEmployeeToPayroll(String employeeName, String gender, double basic_pay, LocalDate startDate) {
        int employee_id = -1;
        Connection connection = null;
        EmployeePayrollData employeePayrollData = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try(Statement statement = Objects.requireNonNull(connection).createStatement()) {

            String sql = String.format("INSERT INTO employee_payroll (name, gender, salary, start) " +
                    "VALUES('%s', '%s', '%s', '%s');", employeeName, gender, basic_pay, Date.valueOf(startDate));
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                    employee_id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Objects.requireNonNull(connection).rollback();
                return employeePayrollData;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

        try (Statement statement = Objects.requireNonNull(connection).createStatement()){
            double deductions = basic_pay*0.2;
            double taxble_pay = basic_pay - deductions;
            double incom_tax =  taxble_pay*0.1;
            double net_pay = basic_pay - incom_tax;
            String sql = String.format("INSERT INTO payroll_details " +
                    "(employee_id, basic_pay, deductions, taxble_pay, incom_tax, net_pay)" +
                    "VALUES (%s, %s, %s, %s, %s, %s ); ", employee_id, basic_pay, deductions, taxble_pay, incom_tax, net_pay);
            int rowAffected1 = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected1 == 1) {
                    employeePayrollData = new EmployeePayrollData(employee_id, employeeName, basic_pay, startDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Objects.requireNonNull(connection).commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Objects.requireNonNull(connection).rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        try {
            Objects.requireNonNull(connection).commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return employeePayrollData;
    }
}
