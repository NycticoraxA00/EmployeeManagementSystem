package com.example.springboot.repository;

import com.example.springboot.model.Department;
import com.example.springboot.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Find a Department's employees
    List<Employee> findByDepartment(Department department, Sort sort);
//    @Query("SELECT e FROM Employee e WHERE LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :name, '%'))")
//    List<Employee> findByEmployeeNameContainingIgnoreCase(@Param("name")String name);

}