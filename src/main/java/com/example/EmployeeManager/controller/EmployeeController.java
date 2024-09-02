package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(description = "Returns employee by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @Operation(description = "Returns list for all Employees", method = "GET")
    @GetMapping("/all")
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @Operation(description = "Create new employee", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PostMapping
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        return employeeService.addEmployee(newEmployee);
    }
    @Operation(description = "Update employee by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PutMapping("/update/employee/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @Operation(description = "Delete employee by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @DeleteMapping("dismiss/employee/{id}")
    public Employee dismissEmployee(@PathVariable Long id) {
        return employeeService.dismissEmployee(id);
    }

    @Operation(description = "Get all working employees", method = "GET")
    @GetMapping("/working")
    public List<Employee> getAllWorkingEmployees() {
        return employeeService.getAllWorkingEmployees();
    }

    @Operation(description = "Get all dismissed employees", method = "GET")
    @GetMapping("/dismissed")
    public List<Employee> getAllDismissedEmployees() {
        return employeeService.getAllDismissedEmployees();
    }
}
