package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.createDTO.DepartmentCreateDTO;
import com.example.EmployeeManager.dto.returnDTO.DepartmentReturnDTO;
import com.example.EmployeeManager.dto.returnDTO.EmployeeReturnDTO;
import com.example.EmployeeManager.representation.DepartmentRepresentation;
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
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentRepresentation departmentRepresentation;

    @Operation(description = "Get all existing departments")
    @GetMapping
    public Page<DepartmentReturnDTO> getAllDepartments(@ParameterObject Pageable pageable) {
         return departmentRepresentation.getAllDepartments(pageable) ;
    }

    @Operation(description = "Delete department by giving id")
    @GetMapping("/{id}")
    public DepartmentReturnDTO getDepartmentById(@PathVariable("id") Long id) {
        return departmentRepresentation.getDepartmentById(id);
    }

    @Operation(description = "Create department")
    @PostMapping
    public DepartmentReturnDTO createDepartment(@RequestBody DepartmentCreateDTO department) {
        return departmentRepresentation.createDepartment(department);
    }

    @Operation(description = "Update department by giving id")
    @PutMapping("/{id}")
    public DepartmentReturnDTO updateDepartment(@PathVariable("id") Long id, @RequestBody DepartmentCreateDTO department) {
        return departmentRepresentation.updateDepartment(id, department);
    }

    @Operation(description = "Delete department by giving id")
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") Long id) {
        departmentRepresentation.deleteDepartment(id);
    }

    @Operation(description = "Get all employees from department by giving id", method = "GET")
    @GetMapping("/{id}/employees")
    public Page<EmployeeReturnDTO> getAllEmployeesFromDepartment(@PathVariable("id") Long id,
                                                                 @ParameterObject Pageable pageable ) {
        return departmentRepresentation.getAllEmployeesFromDepartment(id, pageable);
    }

    @Operation(description = "Add employee by given id to department", method = "POST")
    @PostMapping("/{departmentId}/employees/{employeeId}")
    public void addEmployeeToDepartment(@PathVariable("departmentId") Long departmentId,
                                        @PathVariable("employeeId") Long employeeId) {
        departmentRepresentation.addEmployeeToDepartment(departmentId, employeeId);
    }

    @Operation(description = "Delete employee by given id to department", method = "DELETE")
    @DeleteMapping("/{departmentId}/employees/{employeeId}")
    public void removeEmployeeFromDepartment(@PathVariable("departmentId") Long departmentId,
                                             @PathVariable("employeeId") Long employeeId) {
        departmentRepresentation.removeEmployeeFromDepartment(departmentId,employeeId);
    }
}