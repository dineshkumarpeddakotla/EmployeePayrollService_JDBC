package com.databaseservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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
}
