package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    private Project hrProject;
    private Project developingProject;
    private Employee hr;
    private Employee developer;

    @BeforeEach
    void setUp() {
      developer = employeeRepository.save( Employee.builder()
              .fullName("Cтоматин Петр Петрович")
              .email("example@sber.ru")
              .build());
      hr = employeeRepository.save( Employee.builder()
              .fullName("Янкова Алла Вячаславовна")
              .email("alla@sber.ru")
              .build());
      hrProject = projectRepository.save(Project.builder()
              .name("Расширение отдела связи с общетсвенностью")
              .description("в связи с расширением компании требуется нанять 20 сотрудников в новый департамент")
              .employees(List.of(hr))
              .build());
      developingProject = projectRepository.save(Project.builder()
              .name("Разработка нового продукта")
              .description("разработка мобильного приложения по тендеру")
              .employees(List.of(developer))
              .build());
    }

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void getProjectById() {
        Project projectById = projectService.getProjectById(hrProject.getId());
        assertNotNull(projectById);
        assertEquals(hrProject.getName(), projectById.getName());
        assertEquals(hrProject.getDescription(), projectById.getDescription());
        assertNotEquals(projectById.getEmployees(), developingProject.getEmployees());
    }

    @Test
    void createProject() {
        Project newProject = projectService.createProject(Project.builder()
                .name("Разработать план продаж на 3 квартала вперед")
                .description("Требуется срочно обновить торговую стратегию отдела продаж.")
                .employees(List.of())
                .build());
        Project createdProject = projectService.getProjectById(newProject.getId());
        assertNotNull(createdProject);
        assertEquals(newProject.getName(), createdProject.getName());
        assertEquals(newProject.getDescription(), createdProject.getDescription());
    }

    @Test
    void deleteProjectById() {
        projectService.deleteProjectById(hrProject.getId());
        assertThrows(NoSuchElementException.class, () -> projectService.getProjectById(hrProject.getId()));
    }

    @Test
    void updateProject() {
        Project newData = Project.builder()
                .name("Разработать план продаж на 3 квартала вперед")
                .description("Требуется срочно обновить торговую стратегию отдела продаж.")
                .employees(List.of())
                .build();
        Project updatedProject = projectService.updateProject(hrProject.getId(), newData);
        assertEquals(newData.getName(), updatedProject.getName());
        assertEquals(newData.getDescription(), updatedProject.getDescription());
        assertNotEquals(updatedProject.getName(), hrProject.getName());
    }

    @Test
    void addEmployeeToProject() {
        Employee newEmployee = employeeRepository.save(Employee.builder()
                .fullName("Корнеева Светлана Львовна")
                .email("sveta@sber.ru")
                .build());
        Project updatedProject = projectService.addEmployeeToProject( hrProject.getId(), newEmployee.getId());
        projectService.getProjectById(hrProject.getId()).getEmployees().forEach(employee -> System.out.println(employee.getFullName()));
        assertEquals(updatedProject.getEmployees().size(), 2);
    }

    @Test
    void removeEmployeeFromProject() {
        Employee newEmployee = employeeService.addEmployee(Employee.builder()
                .fullName("Корнеева Светлана Львовна")
                .email("sveta@sber.ru")
                .build());
        projectService.addEmployeeToProject( hrProject.getId(), newEmployee.getId());
        projectService.getProjectById(hrProject.getId()).getEmployees().forEach(employee -> System.out.println(employee.getFullName()));

        projectService.removeEmployeeFromProject(hrProject.getId(), newEmployee.getId());
        projectService.getProjectById(hrProject.getId()).getEmployees().forEach(employee -> System.out.println(employee.getFullName()));
        assertEquals(1,projectService.getProjectById(hrProject.getId()).getEmployees().size());
    }
}