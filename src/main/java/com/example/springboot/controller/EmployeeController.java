package com.example.springboot.controller;

import com.example.springboot.model.Department;
import com.example.springboot.model.Employee;
import com.example.springboot.repository.DepartmentRepository;
import com.example.springboot.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    // Fetches and displays a list of employees. Allows sorting and filtering by department.
    @RequestMapping(value = "/employee/list") // homepage redirects here
    public String getAllEmployees(
            @RequestParam(value = "department", required = false, defaultValue = "0") Long departmentId,
            @RequestParam(value = "sort", required = false, defaultValue = "1") int sortMode,
            @RequestParam(value = "searchField", required = false) String searchField,
            Model model) {

        model.addAttribute("departmentId", departmentId);
        model.addAttribute("sortMode", sortMode);

        Sort.Direction sortOrder = Sort.Direction.DESC; // Default sort order
        String sortColumn = "employeeId"; // Default sort column
        // Determine sort order and column based on sortMode
        switch (sortMode) {
            case 1: // Latest Update
            case 2: // Oldest Update
                sortColumn = "employeeId"; // Assuming you have an updateDate field
                sortOrder = (sortMode == 1) ? Sort.Direction.DESC : Sort.Direction.ASC;
                break;
            case 3: // By name ASC
                sortColumn = "employeeName";
                sortOrder = Sort.Direction.ASC;
                break;
            case 4: // By name DESC
                sortColumn = "employeeName";
                sortOrder = Sort.Direction.DESC;
                break;
            case 5: // By age ASC
                sortColumn = "employeeAge";
                sortOrder = Sort.Direction.ASC;
                break;
            case 6: // By age DESC
                sortColumn = "employeeAge";
                sortOrder = Sort.Direction.DESC;
                break;
            case 7: // By salary ASC
                sortColumn = "employeeSalary";
                sortOrder = Sort.Direction.ASC;
                break;
            case 8: // By salary DESC
                sortColumn = "employeeSalary";
                sortOrder = Sort.Direction.DESC;
                break;
            default:
                // Default case if needed
                break;
        }

        List<Employee> employees;
        if (departmentId != 0) {
            Optional<Department> department = departmentRepository.findById(departmentId);
            if (department.isPresent()) {
                employees = employeeRepository.findByDepartment(department.get(), Sort.by(sortOrder, sortColumn));
            } else {
                employees = employeeRepository.findAll(Sort.by(sortOrder, sortColumn));
            }
        } else {
            employees = employeeRepository.findAll(Sort.by(sortOrder, sortColumn));
        }
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeList"; // Returns the view name to render
    }

//    @GetMapping("/employee/search")
//    public String searchEmployees(
//            @RequestParam(value = "name",required = false)String name,
//            Model model){
//        System.out.println("searching with "+ name);
//        List<Employee> employees;
//        if(name != null && !name.isEmpty()){
//            employees = employeeRepository.findByEmployeeNameContainingIgnoreCase(name);
//        } else {
//            employees = employeeRepository.findAll();
//        }
//        model.addAttribute("employees",employees);
//        return "employeeList";
//    }
    //Fetches and displays details of a specific employee by employeeId.
    @RequestMapping(value = "/employee/{employeeId}")
    public String getEmployeeById(@PathVariable(value = "employeeId") Long employeeId, Model model) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            model.addAttribute("employee", employeeOptional.get());
            return "employeeDetail";
        } else {
            return "redirect:/employee/list"; // Handle employee not found
        }
    }

    //Fetches a specific employee for updating and returns the update form.
    //If the employee is not found, return the employee list view
    @RequestMapping(value = "/employee/update/{employeeId}")
    public String updateEmployee(@PathVariable(value = "employeeId") Long employeeId, Model model) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            model.addAttribute("employee", employee);
            model.addAttribute("departments", departmentRepository.findAll()); // Add departments here
            return "employeeUpdate"; // Returns the view name for the update form
        } else {
            return "redirect:/employee/list"; // Handle employee not found
        }
    }

    //Saves the updated employee information if validated
    //Else, return the update form view.
    @RequestMapping(value = "/employee/save")
    public String saveUpdate(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employeeUpdate"; // Return to update form if validation fails
        } else {
            employeeRepository.save(employee);
            return "redirect:/employee/" + employee.getEmployeeId(); // Redirect to updated employee details
        }
    }

    //Initializes a new employee object and returns the add employee form.
    @RequestMapping(value = "/employee/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("employee", employee);
        return "employeeAdd"; // Returns the add employee form
    }

    //Inserts a new employee into the database.
    //Else return the add employee form view
    @RequestMapping(value = "/employee/insert")
    public String insertEmployee(Model model, @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            model.addAttribute("employee", employee);
            return "employeeAdd"; // Return to add form if validation fails
        } else {
            employeeRepository.save(employee);
            return "redirect:/employee/" + employee.getEmployeeId(); // Redirect to the newly created employee details
        }
    }

    //Find and deletes an employee by employeeId.
    //Return the employee list view.
    @RequestMapping(value = "/employee/delete/{employeeId}")
    public String deleteEmployee(@PathVariable(value = "employeeId") Long employeeId) {
        employeeRepository.findById(employeeId).ifPresent(employeeRepository::delete); // Delete employee if present
        return "redirect:/employee/list"; // Redirect to the employee list
    }
}