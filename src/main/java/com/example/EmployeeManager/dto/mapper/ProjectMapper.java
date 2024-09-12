package com.example.EmployeeManager.dto.mapper;

import com.example.EmployeeManager.dto.ProjectDTO;
import com.example.EmployeeManager.entity.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {
    ProjectDTO toDto(Project project) {
        ProjectDTO dto = new ProjectDTO(project.getName(), project.getDescription());
        return dto;
    }

    Project fromDto(ProjectDTO dto) {
        Project project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        return project;
    }
}
