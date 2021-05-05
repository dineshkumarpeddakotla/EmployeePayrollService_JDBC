package com.databaseservice;

import java.time.LocalDate;

public class EmployeePayrollData {
    public LocalDate startDate;
    private int id;
    private String name;
    private Double salary;

    /**
     * created a parameterized constructor
     * @param id id
     * @param name name
     * @param salary salary
     */
    public EmployeePayrollData(int id, String name, Double salary) {
        setId(id);
        setName(name);
        setSalary(salary);
    }

    /**
     * overloading  parameterized constructor
     * @param id id
     * @param name name
     * @param salary salary
     * @param startDate startDate
     */
    public EmployeePayrollData(int id, String name, Double salary,LocalDate startDate) {
        setId(id);
        setName(name);
        setSalary(salary);
        this.startDate = startDate;
    }

    //added getters and setters for id, name, salary
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

    //Override toSting method
    @Override
    public String toString() {
        return "EmployeePayRollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}'+'\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id &&
                     Double.compare(that.getSalary(), salary) == 0 &&
                     name.equals(that.getName());
    }
}
