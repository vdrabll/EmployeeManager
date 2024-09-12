package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.ProjectDTO;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProjectRepresentation {
    private final ProjectService projectService;

    public ProjectDTO getProjectById(Long id) {
        return toDTO(projectService.getProjectById(id));
    }

    public ProjectDTO createProject(ProjectDTO project) {
        return toDTO(projectService.createProject(fromDTO(project)));
    }

    public void deleteProjectById(Long id) {
        projectService.deleteProjectById(id);
    }

    public ProjectDTO updateProject(Long id, ProjectDTO project) {
        Project data = fromDTO(project);
        data.setId(id);
        return toDTO(projectService.updateProject(id,data));
    }

    public ProjectDTO addEmployeeToProject(Long id, Long employeeId) {
        return toDTO(projectService.addEmployeeToProject(id, employeeId));
    }

    public ProjectDTO removeEmployeeFromProject(Long id, Long employeeId) {
        return toDTO(projectService.removeEmployeeFromProject(id, employeeId));
    }

    ProjectDTO toDTO(Project project) {
        ProjectDTO dto = new ProjectDTO(project.getName(), project.getDescription());
        return dto;
    }

    Project fromDTO(ProjectDTO dto) {
        Project project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        return project;
    }
}
