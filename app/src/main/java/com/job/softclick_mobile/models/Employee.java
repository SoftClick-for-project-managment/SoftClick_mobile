package com.job.softclick_mobile.models;

import java.io.Serializable;

public class Employee implements Serializable {

    private int id, employeeImage;
    private String employeeFirstName, employeeLastName, employeeFunction, employeeEmail, employeePhone;

    public Employee(int employeeImage, String employeeFirstName, String employeeLastName, String employeeFunction, String employeeEmail, String employeePhone) {
        this.employeeImage = employeeImage;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeFunction = employeeFunction;
        this.employeeEmail = employeeEmail;
        this.employeePhone = employeePhone;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeImage() {
        return employeeImage;
    }

    public void setEmployeeImage(int employeeImage) {
        this.employeeImage = employeeImage;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeFunction() {
        return employeeFunction;
    }

    public void setEmployeeFunction(String employeeFunction) {
        this.employeeFunction = employeeFunction;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

}
