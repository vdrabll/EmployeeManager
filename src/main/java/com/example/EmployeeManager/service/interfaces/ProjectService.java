package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    Project getProjectById(Long id);

    Project createProject(Project project);

    void deleteProjectById(Long id);

    Project updateProject(Long id, Project project);

    Project addEmployeeToProject(Long id, Long empId);

    Project removeEmployeeFromProject(Long id, Long empId);

    Page<Project> getAll(Pageable pageable);

}
