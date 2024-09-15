package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.create.ProjectCreateDTO;
import com.example.EmployeeManager.dto.ProjectReturnDTO;
import com.example.EmployeeManager.representation.ProjectRepresentation;
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
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectRepresentation projectRepresentation;

    @Operation(description = "Return project by giving id", method = "GET")
    @GetMapping("/{id}")
    public ProjectReturnDTO getProjectById(@PathVariable Long id) {
        return projectRepresentation.getProjectById(id);
    }

    @Operation(description = "Create new project", method = "POST")
    @PostMapping
    public ProjectReturnDTO createProject(@RequestBody ProjectCreateDTO project) {
        return projectRepresentation.createProject(project);
    }

    @Operation(description = "Delete project by giving id", method = "DELETE")
    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable Long id) {
        projectRepresentation.deleteProjectById(id);
    }

    @Operation(description = "Update project by giving id", method = "PATCH")
    @PatchMapping("/{id}")
    public ProjectReturnDTO updateProject(@PathVariable Long id,
                                          @RequestBody ProjectCreateDTO project) {
        return projectRepresentation.updateProject(id, project);
    }

    @Operation(description = "Add employee by giving id to project by giving id", method = "POST")
    @PostMapping("/{projectId}/employees/{employeeId}")
    public ProjectReturnDTO addEmployeeToProject(@PathVariable Long projectId,
                                                 @PathVariable Long employeeId) {
        return projectRepresentation.addEmployeeToProject(projectId, employeeId);
    }

    @Operation(description = "Remove employee by giving id to project by giving id", method = "DELETE")
    @DeleteMapping("/{projectId}/employee/{employeeId}")
    public ProjectReturnDTO removeEmployeeFromProject(@PathVariable Long projectId,
                                                      @PathVariable Long employeeId) {
        return projectRepresentation.removeEmployeeFromProject(projectId, employeeId);
    }

    @Operation(description = "Return list of all projects", method = "GET")
    @GetMapping
    public Page<ProjectReturnDTO> getAll(@ParameterObject Pageable pageable) {
        return  projectRepresentation.getAll(pageable);
    }
}