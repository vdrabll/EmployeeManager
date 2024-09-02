package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(description = "Returns list for all departments", method = "GET")
    @ApiResponse
    @GetMapping()
    public List<Department> getAllDepartments() {
         return departmentService.getAll();
    }

    @Operation(description = "Returns department by giving identifier", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @GetMapping("/department/{id}")
    public Department getDepartmentById(@PathVariable("id") Long id) {
        return departmentService.getDepartmentById(id);
    }

    @Operation(description = "Create new department", method = "POST")
    @ApiResponse
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.save(department);
    }

    @Operation(description = "Update department by giving identifier", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @PutMapping("/department/update/{id}")
    public Department updateDepartment(@PathVariable("id") Long id, @RequestBody Department department) {
        return departmentService.updateDepartmentById(id, department);
    }

    @Operation(description = "Delete department by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @DeleteMapping("/department/delete/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.delete(departmentService.getDepartmentById(id));
    }

    @Operation(description = "Return all employees from department by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @GetMapping("/employees/{id}")
    public List<Employee> getAllEmployeesFromDepartment(@PathVariable Long id) {
        return departmentService.getAllEmployeesFromDepartment(id);
    }

    @Operation(description = "Add employee to given ", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true)
    })
    @ApiResponse
    @PostMapping("/employee/{id}")
    public void addEmployeeToDepartment(@PathVariable Long id, @RequestBody Employee newEmployee) {
        departmentService.addEmployeeToDepartment(id, newEmployee);
    }

    @Operation(description = "Delete employee from department bu given id ", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @ApiResponse
    @DeleteMapping("department/{id}/employee/{employeeId}")
    public void removeEmployeeFromDepartment(@PathVariable Long departmentId, @PathVariable Long employeeId) {
        departmentService.removeEmployeeFromDepartment(departmentId,employeeId);
    }
}