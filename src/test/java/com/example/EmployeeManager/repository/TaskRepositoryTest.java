package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.enums.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest()
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee developer;
    private Employee tester;
    private Project project;
    private Task developerTask;
    private Task testerTask;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();
        developer = employeeRepository.save(Employee.builder()
                .role(AuthRole.EMPLOYEE)
                .fullName("Иванко Петр Петрович")
                .email("example@sber.ru")
                .build());
        tester = employeeRepository.save(Employee.builder()
                .role(AuthRole.EMPLOYEE)
                .fullName("Шобл Анатолий Петрович")
                .email("example@yandex.ru")
                .build());
        project = projectRepository.save(Project.builder()
                .name("Разработка Высоконагруженной системы")
                .description("Создание системы")
                .build());
        developerTask = taskRepository.save(Task.builder()
                .name("Разарботать схему базы данных")
                .description("- сделать схем базы данных согласно тз - перенести схему в sql")
                .priority(TaskPriority.HIGH)
                .employee(developer)
                .project(project)
                .deadline(LocalDate.of(2024, 6, 20))
                .type( "база данных")
                .status(TaskStatus.NOT_STARTED)
                .build());

        testerTask = taskRepository.save(Task.builder()
                .name("Разарботать интеграционные тесты")
                .description("- сделать тест согласно разработанной схемы")
                .priority(TaskPriority.HIGH)
                .employee(tester)
                .project(project)
                .deadline(LocalDate.of(2024, 7, 5))
                .type( "база данных")
                .status(TaskStatus.NOT_STARTED)
                .build());

    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    void findByNameAndEmployeeTest() {
        boolean taskOfDeveloper = taskRepository.existsByNameAndEmployee_id("Разарботать схему базы данных", developer.getId());
        boolean taskOfTester = taskRepository.existsByNameAndEmployee_id("Разарботать интеграционные тесты",tester.getId());
        assertThat(taskOfDeveloper).isEqualTo(true);
        assertThat(taskOfTester).isEqualTo(true);
    }

    @Test
    void findTasksByDeadlineAfter() {
        Page<Task> allByDeadlineAfter = taskRepository.findAllByDeadlineAfter(LocalDate.of(2027, 8, 26), pageable);
        assertThat(allByDeadlineAfter).isEqualTo(Page.empty());
    }

    @Test
    void findTasksByType() {
        Page<Task> tasksOfDatabase = taskRepository.findAllByType("база данных", pageable);
        assertThat(tasksOfDatabase).isNotNull();
        assertThat(tasksOfDatabase.stream().allMatch(task -> Objects.equals(task.getType(), "база данных")));
    }

    @Test
    void findTasksByPriority() {
        testerTask.setPriority(TaskPriority.MEDIUM);
        Page<Task> tasksOfDatabase = taskRepository.findAllByPriority(TaskPriority.HIGH, pageable);
        assertThat(tasksOfDatabase).isNotNull();
        assertTrue(tasksOfDatabase.toList().size() == 1);
    }

    @Test
    void findAllByProject() {
        Page<Task> tasksOfProject = taskRepository.findAllByProject(project, pageable);
        assertThat(tasksOfProject).isNotNull();
        assertThat(tasksOfProject.stream().toList().size() == 2);
        assertThat(tasksOfProject.stream().toList().stream().allMatch(task -> task.getProject() == project));

    }

    @Test
    void findAllByEmployee() {
        Page<Task> tasksOfTester = taskRepository.findAllByEmployee_Id(tester.getId(), pageable);
        assertThat(tasksOfTester).isNotNull();
        assertThat(tasksOfTester.toList()).isEqualTo(List.of(testerTask));

        Page<Task> tasksOfDeveloper = taskRepository.findAllByEmployee_Id(developer.getId(), pageable);
        assertThat(tasksOfDeveloper).isNotNull();
        assertThat(tasksOfDeveloper.toList()).isEqualTo(List.of(developerTask));
    }
}