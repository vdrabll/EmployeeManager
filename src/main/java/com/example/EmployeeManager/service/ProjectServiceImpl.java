package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.ProjectRepository;
import com.example.EmployeeManager.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Проект по данному id: %s не найден", id)));
    }

    @Transactional
    public  Project createProject(Project project) {
        Project byNameAndDescription = projectRepository.findByName(project.getName()).orElseThrow(()
                        -> new NoSuchElementException(String.format("Проект с названием %s уже существует", project.getName())));
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProjectById(Long id) {
        projectRepository.delete(getProjectById(id));
    }

    @Transactional
    public Project updateProject(Long id, Project project) {
        Project projectById = getProjectById(id);
        projectById.setName(project.getName());
        projectById.setDescription(project.getDescription());
        return projectById;
    }

    @Transactional // TODO: исправить
    public Project addEmployeeToProject(Long id, Long empId) {
        Project project = getProjectById(id);
        Employee employee = employeeRepository.getReferenceById(empId);
        if (!project.getEmployees().contains(employee)) {
            project.getEmployees().add(employee);
        } else {
            throw new RuntimeException(String.format("Сотрудник по данному id: %s уже найден в списке участников проекта", id));
        }
        return project;
    }

    @Transactional
    public Project removeEmployeeFromProject(Long id, Long empId) {
        Project project = getProjectById(id);
        Employee employee = employeeRepository.getReferenceById(empId);
        if (project.getEmployees().contains(employee)) {
            project.getEmployees().remove(employee);
        } else {
           throw new RuntimeException(String.format("Сотрудник по данному id: %s не найден в списке участников проекта", id));
        }
        return project;
    }
}
