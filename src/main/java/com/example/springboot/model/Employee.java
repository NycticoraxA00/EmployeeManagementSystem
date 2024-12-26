package com.example.springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId", nullable = false)
    private Long employeeId;
    @Length(min = 3, max = 30)
    @NotEmpty(message = "Name cannot be empty")
    private String employeeName;
    @Min(18)
    @Max(65)
    private int employeeAge;
    private String employeeAddress;
    private Long employeePhone;
    private String employeeGender;
    private BigDecimal employeeSalary;
    @ManyToOne
    private Department department;

    //Getter & Setter Method
    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public int getEmployeeAge() {
        return employeeAge;
    }
    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }
    public String getEmployeeAddress() {
        return employeeAddress;
    }
    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }
    public Long getEmployeePhone() {
        return employeePhone;
    }
    public void setEmployeePhone(Long employeePhone) {
        this.employeePhone = employeePhone;
    }
    public String getEmployeeGender() {
        return employeeGender;
    }
    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }
    public BigDecimal getEmployeeSalary() {
        return employeeSalary;
    }
    public void setEmployeeSalary(BigDecimal employeeSalary) {
        this.employeeSalary = employeeSalary;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
