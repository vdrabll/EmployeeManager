package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.DepartmentDTO;
import com.example.EmployeeManager.dto.EmployeeDTO;
import com.example.EmployeeManager.representation.DepartmentRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentRepresentation departmentRepresentation;

    @Operation(description = "Get all existing departments")
    @GetMapping("/all")
    public Page<DepartmentDTO> getAllDepartments(@ParameterObject Pageable pageable) {
         return departmentRepresentation.getAllDepartments(pageable) ;
    }

    @Operation(description = "Delete department by giving id")
    @GetMapping("/delete/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable("id") Long id) {
        return departmentRepresentation.getDepartmentById(id);
    }

    @Operation(description = "Create department")
    @PostMapping("/create")
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO department) {
        return departmentRepresentation.createDepartment(department);
    }

    @Operation(description = "Update department by giving id")
    @PutMapping("/update/{id}")
    public DepartmentDTO updateDepartment(@PathVariable("id") Long id, @RequestBody DepartmentDTO department) {
        return departmentRepresentation.updateDepartment(id, department);
    }

    @Operation(description = "Delete department by giving id")
    @DeleteMapping("/delete/{id}")
    public void deleteDepartment(@PathVariable("id") Long id) {
        departmentRepresentation.deleteDepartment(id);
    }

    @Operation(description = "Get all employees from department by giving id", method = "GET")
    @GetMapping("/departments/{id}")
    public Page<EmployeeDTO> getAllEmployeesFromDepartment(@PathVariable("id") Long id, @ParameterObject Pageable pageable ) {
        return departmentRepresentation.getAllEmployeesFromDepartment(id, pageable);
    }

    @Operation(description = "Add employee by given id to department", method = "POST")
    @PostMapping("{id}/employees/{employeeId}")
    public void addEmployeeToDepartment(@PathVariable("id") Long id, @PathVariable("employeeId") Long employeeId) {
        departmentRepresentation.addEmployeeToDepartment(id, employeeId);
    }

    @Operation(description = "Delete employee by given id to department", method = "DELETE")
    @DeleteMapping("/{id}/employees/{employeeId}")
    public void removeEmployeeFromDepartment(@PathVariable("id") Long id, @PathVariable("employeeId") Long employeeId) {
        departmentRepresentation.removeEmployeeFromDepartment(id,employeeId);
    }
}