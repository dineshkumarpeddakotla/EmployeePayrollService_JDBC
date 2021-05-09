package com.databaseservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.databaseservice.EmployeePayrollService.IOService.DB_IO;

public class TestDataBaseService {

    /**
     * test case is created to check whether data is retrieved or not from database
     */
    @Test
    public void givenEmployeePayrollInDB_WhenDataRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(DB_IO);
        Assertions.assertEquals(3, employeePayrollData.size());
    }

    /**
     * test case is created to check whether data is update or not in database
     */
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Dinesh", 600000.00);
        boolean result = employeePayrollService.checkEmployeePayrollSyncWithDB("Dinesh");
        Assertions.assertTrue(result);
    }

    /**
     * test case is created to check whether data is retrieved from database in a particular date range
     */
    @Test
    public void givenDateRange_WhenRetrievedData_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        LocalDate startDate = LocalDate.of(2018, 1, 28);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollService.readEmployeePayrollDataForDateRange(DB_IO, startDate, endDate);
        Assertions.assertEquals(3, employeePayrollDataList.size());
    }

    /**
     * test case is created to check average salary by gender
     */
    @Test
    public void givenPayrollData_WhenAverageSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(DB_IO);
        Assertions.assertTrue(averageSalaryByGender.get("M").equals(500000.00) &&
                              averageSalaryByGender.get("F").equals(475000.00));
    }

    /**
     * test case is created to check sum salary by gender
     */
    @Test
    public void givenPayrollData_WhenSumSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> sumSalaryByGender = employeePayrollService.readSumSalaryByGender(DB_IO);
        Assertions.assertTrue(sumSalaryByGender.get("M").equals(1500000.00) &&
                              sumSalaryByGender.get("F").equals(950000.00));
    }

    /**
     * test case is created to check minimum salary by gender
     */
    @Test
    public void givenPayrollData_WhenMinimumSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> minimumSalaryByGender = employeePayrollService.readMinimumSalaryByGender(DB_IO);
        Assertions.assertTrue(minimumSalaryByGender.get("M").equals(400000.00) &&
                              minimumSalaryByGender.get("F").equals(450000.00));
    }

    /**
     * test case is created to check maximum salary by gender
     */
    @Test
    public void givenPayrollData_WhenMaximumSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> maximumSalaryByGender = employeePayrollService.readMaximumSalaryByGender(DB_IO);
        Assertions.assertTrue(maximumSalaryByGender.get("M").equals(600000.00) &&
                              maximumSalaryByGender.get("F").equals(500000.00));
    }

    /**
     * test case is created to check count name by gender
     */
    @Test
    public void givenPayrollData_WhenCountNameRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Integer> countNameByGender = employeePayrollService.readCountNameByGender(DB_IO);
        Assertions.assertTrue(countNameByGender.get("M").equals(3) &&
                              countNameByGender.get("F").equals(2));
    }

    /**
     * tested whether new employee added or not
     */
    @Test
    public void givenNewEmployee_whenAdded_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        employeePayrollService.addEmployeeToPayroll("Arun", "M", 400000.00, LocalDate.now());
        boolean result = employeePayrollService.checkEmployeePayrollSyncWithDB("Arun");
        Assertions.assertTrue(result);
    }
}
