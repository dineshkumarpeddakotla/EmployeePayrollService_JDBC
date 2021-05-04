package com.databaseservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.databaseservice.EmployeePayrollService.IOService.DB_IO;

public class TestDataBaseService {

    @Test
    public void givenEmployeePayrollInDB_WhenDataRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDate(DB_IO);
        Assertions.assertEquals(3, employeePayrollData.size());
    }
}
