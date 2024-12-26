package com.example.springboot.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;
    @Length(min = 3, max = 30)
    private String departmentName;
    private String departmentAddress;
    private int departmentEstablishmentYear;
    private Long departmentPhoneNumber;
    private String departmentEmail;
    @Transient
    private int departmentEmployeeCount;
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    //Getter & Setter Method
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentAddress() {
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }

    public int getDepartmentEstablishmentYear() {
        return departmentEstablishmentYear;
    }

    public void setDepartmentEstablishmentYear(int departmentEstablishmentYear) {
        this.departmentEstablishmentYear = departmentEstablishmentYear;
    }

    public Long getDepartmentPhoneNumber() {
        return departmentPhoneNumber;
    }

    public void setDepartmentPhoneNumber(Long departmentPhoneNumber) {
        this.departmentPhoneNumber = departmentPhoneNumber;
    }

    public String getDepartmentEmail() {
        return departmentEmail;
    }

    public void setDepartmentEmail(String departmentEmail) {
        this.departmentEmail = departmentEmail;
    }

    public int getDepartmentEmployeeCount() {
        this.departmentEmployeeCount = employees.size();
        return departmentEmployeeCount;
    }
//    public void setDepartmentEmployeeCount(int departmentEmployeeCount) {
//        this.departmentEmployeeCount = departmentEmployeeCount;
//    }

    public List<Employee> getDepartmentEmployees() {
        return employees;
    }

    public void setDepartmentEmployees(List<Employee> departmentEmployees) {
        this.employees = departmentEmployees;
    }

    @Override
    public String toString() {
        return this.getDepartmentName();
    }
}
