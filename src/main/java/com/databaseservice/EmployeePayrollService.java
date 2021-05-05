package com.databaseservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayrollService {

    /**
     * created ioService enum class for CONSOLE_IO, FILE_IO,DB_IO
     */
    public enum IOService {
        CONSOLE_IO, FILE_IO, DB_IO
    }
    //declared private variables
    private List<EmployeePayrollData> employeePayrollList;
    private final EmployeePayrollDBService employeePayrollDBService;

    //created default constructor for singleton class
    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    /**
     * created parameterized constructor
     * @param employeePayrollList employeePayrollList
     */
    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
        this();
        this.employeePayrollList = employeePayrollList;
    }

    /**
     * created readEmployeePayrollData from console
     * @param consoleInputReader consoleInputReader
     */
    private void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Please enter employee name");
        String name = consoleInputReader.nextLine();
        System.out.println("Please enter employee ID");
        int id = consoleInputReader.nextInt();
        System.out.println("Please enter employee salary");
        double salary = consoleInputReader.nextDouble();

        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }

    /**
     * created readEmployeePayrollData method to read data from ioService of database Io
     * @param ioService ioService
     * @return this.employeePayrollList
     */
    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            this.employeePayrollList = employeePayrollDBService.readData();
        return this.employeePayrollList;
    }

    /**
     * created updateEmployeeSalary method to update employee salary to database
     * @param name name
     * @param salary salary
     */
    public void updateEmployeeSalary(String name, double salary) {
        int result = employeePayrollDBService.updateEmployeeData(name, salary);
        if ( result == 0)
            return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null) employeePayrollData.setSalary(salary);
    }

    /**
     * created private getEmployeePayrollData method to get data from database by using stream api to filter data
     * @param name name
     * @return employeePayrollData
     */
    private EmployeePayrollData getEmployeePayrollData(String name) {
        EmployeePayrollData employeePayrollData;
        employeePayrollData = this.employeePayrollList.stream()
                                                      .filter(employeePayrollDataItem -> employeePayrollDataItem
                                                                                         .getName().equals(name))
                                                      .findFirst()
                                                      .orElse(null);
        return employeePayrollData;

    }

    /**
     * created readEmployeePayrollDataForDateRange method to read data from database in particular date range
     * @param ioService ioService
     * @param startDate start date in LocalDate format
     * @param endDate end date in LocalDate format
     * @return employeePayrollDBService.getEmployeePayrollDataForDateRange(startDate,endDate)
     */
    public List<EmployeePayrollData> readEmployeePayrollDataForDateRange(IOService ioService,
                                                                         LocalDate startDate, LocalDate endDate) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getEmployeePayrollDataForDateRange(startDate,endDate);
        return null;
    }

    /**
     * created readAverageSalaryByGender method to read average salary by gender data from database
     * @param ioService ioService
     * @return employeePayrollDBService.getAverageSalaryByGender()
     */
    public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getAverageSalaryByGender();
        return null;
    }

    /**
     * created readSumSalaryByGender method to read sum salary by gender data from database
     * @param ioService ioService
     * @return employeePayrollDBService.getSumSalaryByGender()
     */
    public Map<String, Double> readSumSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getSumSalaryByGender();
        return null;
    }

    /**
     * created readMinimumSalaryByGender method to read minimum salary by gender data from database
     * @param ioService ioService
     * @return employeePayrollDBService.getMinimumSalaryByGender()
     */
    public Map<String, Double> readMinimumSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMinimumSalaryByGender();
        return null;
    }

    /**
     * created readMaximumSalaryByGender method to read maximum salary by gender data from database
     * @param ioService ioService
     * @return employeePayrollDBService.getMaximumSalaryByGender()
     */
    public Map<String, Double> readMaximumSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMaximumSalaryByGender();
        return null;
    }

    /**
     *created readCountNameByGender method to read count name by gender data from database
     * @param ioService ioService
     * @return employeePayrollDBService.getCountNameByGender()
     */
    public Map<String, Integer> readCountNameByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getCountNameByGender();
        return null;
    }
    /**
     * created checkEmployeePayrollSyncWithDB method to check whether updated data is synced with database or not
     * @param name name
     * @return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name))
     */
    public boolean checkEmployeePayrollSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

    /**
     * created writeEmployeePayrollData method to write data by taking input from console
     * @param ioService ioService
     */
    public void writeEmployeePayrollData(EmployeePayrollService.IOService ioService) {
        if(ioService.equals(IOService.CONSOLE_IO)) {
            System.out.println("\n Writing Employee Payroll Roster to Console\n" + employeePayrollList);
        }
        else if(ioService.equals(IOService.FILE_IO)){
            new EmployeePayrollFileIOService().writeData(employeePayrollList);
        }
    }

    /**
     * main method
     * @param args arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Employee Payroll service program!");
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
    }
}
