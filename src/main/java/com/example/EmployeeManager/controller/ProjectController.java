package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.ProjectDTO;
import com.example.EmployeeManager.representation.ProjectRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectRepresentation projectRepresentation;

    @Operation(description = "Return project by giving id", method = "GET")
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id) {
        return projectRepresentation.getProjectById(id);
    }

    @Operation(description = "Create project by giving id", method = "POST")
    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO project) {
        return projectRepresentation.createProject(project);
    }

    @Operation(description = "Delete project by giving id", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public void deleteProjectById(@PathVariable Long id) {
        projectRepresentation.deleteProjectById(id);
    }

    @Operation(description = "Update project by giving id", method = "PUT")
    @PutMapping("/update/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO project) {
        return projectRepresentation.updateProject(id, project);
    }

    @Operation(description = "Add employee by giving id to project by giving id", method = "POST")
    @PostMapping("/{id}/employees/{employeeId}")
    public ProjectDTO addEmployeeToProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectRepresentation.addEmployeeToProject(id, employeeId);
    }

    @Operation(description = "Remove employee by giving id to project by giving id", method = "DELETE")
    @DeleteMapping("/{id}/employee/{employeeId}")
    public ProjectDTO removeEmployeeFromProject(@PathVariable Long id, @PathVariable Long employeeId) {
        return projectRepresentation.removeEmployeeFromProject(id, employeeId);
    }
}