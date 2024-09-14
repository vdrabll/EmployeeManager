package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.EmployeeCreateDTO;
import com.example.EmployeeManager.dto.EmployeeReturnDTO;
import com.example.EmployeeManager.representation.EmployeeRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepresentation employeeRepresentation;

    @Operation(description = "Get employee by given id", method = "GET")
    @GetMapping("/{id}")
    public EmployeeReturnDTO getEmployeeById(@PathVariable Long id) {
        return employeeRepresentation.getEmployeeById(id);
    }


    @Operation(description = "Get employee by given id", method = "GET")
    @GetMapping
    public Page<EmployeeReturnDTO> getAllEmployee(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllEmployee(pageable);
    }

    @Operation(description = "Create new employee", method = "POST")
    @PostMapping("/employee")
    public EmployeeReturnDTO createNewEmployee(@RequestBody EmployeeCreateDTO newEmployee) {
        return employeeRepresentation.addChief(newEmployee);
    }

    @Operation(description = "Create new chief", method = "POST")
    @PostMapping("/chief")
    public EmployeeReturnDTO createNewChief(@RequestBody EmployeeCreateDTO newEmployee) {
        return employeeRepresentation.addEmployee(newEmployee);
    }


    @Operation(description = "Update employee by given id", method = "PATCH")
    @PatchMapping("/{id}")
    public EmployeeReturnDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeCreateDTO employee) {
        return employeeRepresentation.updateEmployee(id, employee);
    }

    @Operation(description = "Dismiss employee by given id", method = "Delete")
    @DeleteMapping("/{id}")
    public void dismissEmployee(@PathVariable Long id) {
        employeeRepresentation.dismissEmployee(id);
    }

    @Operation(description = "Return all working employees", method = "GET")
    @GetMapping("/working")
    public Page<EmployeeReturnDTO> getAllWorkingEmployees(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllWorkingEmployees(pageable);
    }

    @Operation(description = "Return all dismissed working employees", method = "GET")
    @GetMapping("/dismissed")
    public Page<EmployeeReturnDTO> getAllDismissedEmployees(@ParameterObject Pageable pageable) {
        return employeeRepresentation.getAllDismissedEmployees(pageable);
    }
}
