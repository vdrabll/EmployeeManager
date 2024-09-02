package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(description = "Returns project by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @GetMapping("/project/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @Operation(description = "Create project by giving id", method = "POST")
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @Operation(description = "Returns project by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @DeleteMapping("delete/project/{id}")
    public void deleteProjectById(@PathVariable Long id) {
        projectService.deleteProjectById(id);
    }

    @Operation(description = "Update project by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of project", required = true)
    })
    @PutMapping("/update/project/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }
    @Operation(description = "Add employee to project", method = "POST", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @PostMapping("project/{id}/employees/{employeeId}")
    public Project addEmployeeToProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectService.addEmployeeToProject(id, employeeId);
    }

    @Operation(description = "Delete employee from project", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of department", required = true),
            @Parameter(name = "employeeId", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @DeleteMapping("project/{id}/employee/{employeeId}")
    public Project removeEmployeeFromProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectService.removeEmployeeFromProject(id, employeeId);
    }
}