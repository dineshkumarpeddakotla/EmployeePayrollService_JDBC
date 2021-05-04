package com.databaseservice;

import java.time.LocalDate;

public class EmployeePayrollData {
    public LocalDate startDate;
    private int id;
    private String name;
    private Double salary;

    public EmployeePayrollData(int id, String name, Double salary) {
        setId(id);
        setName(name);
        setSalary(salary);
    }

    public EmployeePayrollData(int id, String name, Double salary,LocalDate startDate) {
        setId(id);
        setName(name);
        setSalary(salary);
        this.startDate = startDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeePayRollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == 0) return true;
//        if (this == null || getClass() != o.getClass()) return false;
//        EmployeePayrollData that = (EmployeePayrollData) o;
//        return id == that.id &&
//                     Double.compare(that.getSalary(), salary) == 0 &&
//                     name.equals(that.getName());
//    }
}
