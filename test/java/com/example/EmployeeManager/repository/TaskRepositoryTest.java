package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.*;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.enums.TaskStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Set.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest()
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProjectRepository projectRepository;
    private Role employeeRole;
    private Employee developer;
    private Employee tester;
    private Project project;
    private Task developerTask;
    private Task testerTask;


    @BeforeEach
    void setUp() {
        employeeRole = roleRepository.save(new Role(AuthRole.EMPLOYEE));
        developer = Employee.builder()
                .role(employeeRole)
                .fullName( "Иванов Петр Петрович")
                .email("example@sber.ru")
                .build();
        tester = Employee.builder()
                .role(employeeRole)
                .fullName( "Иванов Иван Петрович")
                .email("example@yandex.ru")
                .build();
        project =  Project.builder()
                .name("Разработка Высоконагруженной системы")
                .description("Создание системы")
                .build();
        developerTask = Task.builder()
                .name("Разарботать схему базы данных")
                .description("- сделать схем базы данных согласно тз - перенести схему в sql")
                .priority(TaskPriority.HIGH)
                .employee(developer)
                .project(project)
                .deadline(new Date(2024, 6, 20))
                .estimate((short) 10)
                .type( "база данных")
                .status(TaskStatus.NOT_STARTED)
                .build();

        testerTask = Task.builder()
                .name("Разарботать интеграционные тесты")
                .description("- сделать тест согласно разработанной схемы")
                .priority(TaskPriority.HIGH)
                .employee(tester)
                .project(project)
                .deadline(new Date(2024, 7, 5))
                .estimate((short) 10)
                .type( "база данных")
                .status(TaskStatus.NOT_STARTED)
                .build();
        projectRepository.save(project);
        taskRepository.save(developerTask);
        taskRepository.save(testerTask);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    void findByNameAndEmployeeTest() {
        Task taskOfDeveloper = taskRepository.findByNameAndEmployee("Разарботать схему базы данных", developer);
        Task taskOfTester = taskRepository.findByNameAndEmployee("Разарботать интеграционные тесты",tester);
        assertThat(taskOfDeveloper).isNotNull();
        assertThat(taskOfTester).isNotNull();
        assertThat(taskOfDeveloper).isEqualTo(developerTask);
        assertThat(taskOfTester).isEqualTo(testerTask);
    }

    @Test
    void findTasksByDeadlineAfter() {
        List<Task> allByDeadlineAfter = taskRepository.findAllByDeadlineAfter(new Date(2024, 8, 26));
        assertThat(allByDeadlineAfter).isEmpty();
    }

    @Test
    void findTasksByType() {
        List<Task> tasksOfDatabase = taskRepository.findAllByType("база данных");
        assertThat(tasksOfDatabase).isNotNull();
        assertThat(tasksOfDatabase).isEqualTo(List.of(developerTask, testerTask));
    }

    @Test
    void findTasksByPriority() {
        testerTask.setPriority(TaskPriority.MEDIUM);
        List<Task> tasksOfDatabase = taskRepository.findAllByPriority(TaskPriority.HIGH);
        assertThat(tasksOfDatabase).isNotNull();
        assertThat(tasksOfDatabase).isEqualTo(List.of(developerTask));
    }

    @Test
    void findAllByProject() {
        List<Task> tasksOfProject = taskRepository.findAllByProject(project);
        assertThat(tasksOfProject).isNotNull();
        assertThat(tasksOfProject).isEqualTo(List.of(developerTask, testerTask));
    }

    @Test
    void findAllByEmployee() {
        List<Task> tasksOfTester = taskRepository.findAllByEmployee(tester);
        assertThat(tasksOfTester).isNotNull();
        assertThat(tasksOfTester).isEqualTo(List.of(testerTask));

        List<Task> tasksOfDeveloper = taskRepository.findAllByEmployee(developer);
        assertThat(tasksOfDeveloper).isNotNull();
        assertThat(tasksOfDeveloper).isEqualTo(List.of(developerTask));
    }
}