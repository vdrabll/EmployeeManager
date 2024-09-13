package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.enums.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();
        employeeRole = roleRepository.save(new Role(AuthRole.EMPLOYEE));
        developer = Employee.builder()
                .role(employeeRole)
                .fullName("Иванко Петр Петрович")
                .email("example@sber.ru")
                .build();
        tester = Employee.builder()
                .role(employeeRole)
                .fullName("Шобл Анатолий Петрович")
                .email("example@yandex.ru")
                .build();
        project = Project.builder()
                .name("Разработка Высоконагруженной системы")
                .description("Создание системы")
                .build();
        developerTask = Task.builder()
                .name("Разарботать схему базы данных")
                .description("- сделать схем базы данных согласно тз - перенести схему в sql")
                .priority(TaskPriority.HIGH)
                .employee(developer)
                .project(project)
                .deadline(LocalDate.of(2024, 6, 20))
                .type( "база данных")
                .status(TaskStatus.NOT_STARTED)
                .build();

        testerTask = Task.builder()
                .name("Разарботать интеграционные тесты")
                .description("- сделать тест согласно разработанной схемы")
                .priority(TaskPriority.HIGH)
                .employee(tester)
                .project(project)
                .deadline(LocalDate.of(2024, 7, 5))
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
        Optional<Task> taskOfDeveloper = taskRepository.findByNameAndEmployee("Разарботать схему базы данных", developer);
        Optional<Task> taskOfTester = taskRepository.findByNameAndEmployee("Разарботать интеграционные тесты",tester);
        assertThat(taskOfDeveloper).isNotNull();
        assertThat(taskOfTester).isNotNull();
        assertThat(taskOfDeveloper).isEqualTo(developerTask);
        assertThat(taskOfTester).isEqualTo(testerTask);
    }

    @Test
    void findTasksByDeadlineAfter() {
        Page<Task> allByDeadlineAfter = taskRepository.findAllByDeadlineAfter(LocalDate.of(2027, 8, 26), pageable);
        assertThat(allByDeadlineAfter).isNotNull();
        assertThat(allByDeadlineAfter).isEqualTo(List.of(developerTask, testerTask));
    }

    @Test
    void findTasksByType() {
        Page<Task> tasksOfDatabase = taskRepository.findAllByType("база данных", pageable);
        assertThat(tasksOfDatabase).isNotNull();
        assertThat(tasksOfDatabase).isEqualTo(List.of(developerTask, testerTask));
    }

    @Test
    void findTasksByPriority() {
        testerTask.setPriority(TaskPriority.MEDIUM);
        Page<Task> tasksOfDatabase = taskRepository.findAllByPriority(TaskPriority.HIGH, pageable);
        assertThat(tasksOfDatabase).isNotNull();
        assertThat(tasksOfDatabase).isEqualTo(List.of(developerTask));
    }

    @Test
    void findAllByProject() {
        Page<Task> tasksOfProject = taskRepository.findAllByProject(project, pageable);
        assertThat(tasksOfProject).isNotNull();
        assertThat(tasksOfProject).isEqualTo(List.of(developerTask, testerTask));
    }

    @Test
    void findAllByEmployee() {
        Page<Task> tasksOfTester = taskRepository.findAllByEmployee(tester, pageable);
        assertThat(tasksOfTester).isNotNull();
        assertThat(tasksOfTester).isEqualTo(List.of(testerTask));

        Page<Task> tasksOfDeveloper = taskRepository.findAllByEmployee(developer, pageable);
        assertThat(tasksOfDeveloper).isNotNull();
        assertThat(tasksOfDeveloper).isEqualTo(List.of(developerTask));
    }
}