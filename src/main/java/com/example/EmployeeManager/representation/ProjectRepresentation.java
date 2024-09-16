package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.createDTO.ProjectCreateDTO;
import com.example.EmployeeManager.dto.returnDTO.ProjectReturnDTO;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProjectRepresentation {
    private final ProjectService projectService;

    public ProjectReturnDTO getProjectById(Long id) {
        return toDTO(projectService.getProjectById(id));
    }

    public ProjectReturnDTO createProject(ProjectCreateDTO project) {
        return toDTO(projectService.createProject(fromDTO(project)));
    }

    public void deleteProjectById(Long id) {
        projectService.deleteProjectById(id);
    }

    public ProjectReturnDTO updateProject(Long id, ProjectCreateDTO project) {
        Project data = fromDTO(project);
        data.setId(id);
        return toDTO(projectService.updateProject(id, data));
    }

    public Page<ProjectReturnDTO> getAll(Pageable pageable) {
        return projectService.getAll(pageable).map(this::toDTO);
    }

    public ProjectReturnDTO addEmployeeToProject(Long id, Long employeeId) {
        return toDTO(projectService.addEmployeeToProject(id, employeeId));
    }

    public ProjectReturnDTO removeEmployeeFromProject(Long id, Long employeeId) {
        return toDTO(projectService.removeEmployeeFromProject(id, employeeId));
    }

    ProjectReturnDTO toDTO(Project project) {
        return new ProjectReturnDTO(project.getId(), project.getName(), project.getDescription());
    }

    Project fromDTO(ProjectCreateDTO dto) {
        return Project.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }
}
