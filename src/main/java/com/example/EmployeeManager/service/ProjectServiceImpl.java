package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.ProjectRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import com.example.EmployeeManager.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeService employeeService;

    @Transactional
    public  Project createProject(Project project) {
        if (!projectRepository.existsByName(project.getName())) {
            return projectRepository.save(project);
        } else {
            log.error("Record with {} already exists", project.getId());
            throw new RecordExistException(project.getName());
        }
    }

    @Transactional(readOnly = true)
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Проект по данному id: %s не найден", id)));
    }

    @Override
    public Page<Project> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Transactional
    public Project updateProject(Long id ,Project project) {
        Project projectById = getProjectById(id);
        projectById.setName(project.getName());
        projectById.setDescription(project.getDescription());
        return projectById;
    }

    @Transactional
    public Project addEmployeeToProject(Long id, Long empId) {
        Project project = getProjectById(id);
        Employee employee = employeeService.getEmployeeById(empId);
        if (project.getEmployees().stream().noneMatch(emp -> emp.getId().equals(empId))) {

            project.getEmployees().add(employee);
            projectRepository.save(project);
        } else {
            throw new RuntimeException(String.format("Сотрудник по данному id: %s уже найден в списке участников проекта", id));
        }
        return project;
    }

    @Transactional
    public Project removeEmployeeFromProject(Long id, Long empId) {
        Project project = getProjectById(id);
        Employee employee = employeeService.getEmployeeById(empId);
        if (project.getEmployees().stream().anyMatch(emp -> emp.getId().equals(empId))) {
            project.getEmployees().remove(employee);
            projectRepository.save(project);
        } else {
            throw new RuntimeException(String.format("Сотрудник по данному id: %s не найден в списке участников проекта", id));
        }
        return project;
    }

    @Transactional
    public void deleteProjectById(Long id) {
        if (projectRepository.findById(id).isPresent()) {
            projectRepository.delete(getProjectById(id));
        } else {
            throw new NotFoundException("Нельзя удалить запись, которой нет в базе данных.");
        }
    }
}
