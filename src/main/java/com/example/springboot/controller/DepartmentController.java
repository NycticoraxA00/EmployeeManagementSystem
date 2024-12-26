package com.example.springboot.controller;

import com.example.springboot.model.Department;
import com.example.springboot.repository.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    // Displays a list of all departments.
    @RequestMapping(value = "/department/list")
    public String getAllDepartments(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "departmentList"; // Returns the view name for the department list
    }

    // Displays details of a specific department by ID.
    @RequestMapping(value = "/department/{departmentId}")
    public String getDepartmentById(@PathVariable(value = "departmentId") Long departmentId, Model model) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isPresent()) {
            model.addAttribute("department", departmentOptional.get());
            return "departmentDetail"; // Returns the view name for department details
        } else {
            // Handle department not found
            return "redirect:/department/list";
        }
    }
    // Fetches a department for updating and returns the update form.
    @RequestMapping(value = "/department/update/{departmentId}")
    public String updateCompany(@PathVariable(value = "departmentId") Long departmentId, Model model) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            model.addAttribute("department",department);
            return "departmentUpdate"; // Returns the view name for the update form
        } else {
            // Redirect to the department list if not found
            return "redirect:/department/list";
        }
    }

    // Saves updated department information if validated
    // Else, return the update form view
    @RequestMapping(value = "/department/save")
    public String saveUpdate(@Valid Department department, BindingResult result) {
        if (result.hasErrors()) {
            return "departmentUpdate"; // Return to update form if validation fails
        } else {
            departmentRepository.save(department);
            return "redirect:/department/" + department.getDepartmentId(); // Redirect to updated department details
        }
    }

    //Initializes a new department object and returns the add department form
    @RequestMapping(value = "/department/add")
    public String addDepartment(Model model) {
        Department department = new Department(); // Create a new Department object
        model.addAttribute("department", department);
        return "departmentAdd"; // Returns the view name for the add form
    }

    // Inserts a new department into the database.
    @RequestMapping(value = "/department/insert")
    public String insertDepartment(@Valid Department department, BindingResult result) {
        if (result.hasErrors()) {
            return "departmentAdd"; // Return to add form if validation fails
        } else {
            departmentRepository.save(department);
            return "redirect:/department/" + department.getDepartmentId(); // Redirect to the newly created department details
        }
    }

    // Deletes a department by departmentId.
    @RequestMapping(value = "/department/delete/{departmentId}")
    public String deleteDepartment(@PathVariable(value = "departmentId") Long departmentId) {
        departmentRepository.findById(departmentId).ifPresent(departmentRepository::delete); // Delete department if present
        return "redirect:/department/list"; // Redirect to the department list
    }
}
