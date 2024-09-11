package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.TaskPriority;
import com.example.EmployeeManager.enums.TaskStatus;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.ProjectRepository;
import com.example.EmployeeManager.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class TaskServiceTest {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PositionService positionService;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee developer;
    private Employee tester;
    private Project project;
    private Task developerTask;
    private Task testerTask;
    private Task testerPersonalTask;

    @BeforeEach
    void setUp() {
        developer = employeeRepository.save(Employee.builder()
                .fullName("Иванко Петр Петрович")
                .email("iKnowHtml@sber.ru")
                .build());

        tester = employeeRepository.save(Employee.builder()
                .fullName("Иванко Петр Петрович")
                .email("IWishIHadAFrandLikeMe@sber.ru")
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
                .estimate((short) 10)
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
                .estimate((short) 10)
                .type( "база данных")
                .status(TaskStatus.NOT_STARTED)
                .build());
        testerPersonalTask = taskRepository.save(Task.builder()
                .name("Прочитать книгу по Unit testing")
                .description("надо подготовится к аттестации")
                .priority(TaskPriority.HIGH)
                .employee(tester)
                .deadline(LocalDate.of(2024, 8, 5))
                .estimate((short) 10)
                .type( "подготовка")
                .status(TaskStatus.NOT_STARTED)
                .build());
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    void getTaskById() {
        Task taskById = taskService.getTaskById(developerTask.getId());
        assertNotNull(taskById);
        assertEquals(developerTask.getId(), taskById.getId());
        assertEquals(developerTask.getName(), taskById.getName());
        assertEquals(developerTask.getDescription(), taskById.getDescription());
        assertEquals(developerTask.getPriority(), taskById.getPriority());
        assertEquals(developerTask.getStatus(), taskById.getStatus());
        assertEquals(developerTask.getDeadline(), taskById.getDeadline());
    }

    @Test
    void saveTask() {
        Task newDeveloperTask = taskService.saveTask(Task.builder()
                .name("Прочитать книгу по java 20")
                .description("освежить знания")
                .priority(TaskPriority.HIGH)
                .employee(developer)
                .deadline(LocalDate.of(2024, 8, 5))
                .estimate((short) 10)
                .type( "личные дела")
                .status(TaskStatus.NOT_STARTED)
                .build());
        Task savedTask = taskService.getTaskById(newDeveloperTask.getId());
        assertNotNull(savedTask);
        assertEquals(newDeveloperTask.getId(), savedTask.getId());
        assertEquals(newDeveloperTask.getName(), savedTask.getName());
        assertEquals(newDeveloperTask.getDescription(), savedTask.getDescription());
        assertEquals(newDeveloperTask.getEmployee().getId(), savedTask.getEmployee().getId());

    }

    @Test
    void updateTask() {
        Task newData = Task.builder()
                .name("Прочитать книгу по Братья Карамазовы")
                .description(" ")
                .priority(TaskPriority.HIGH)
                .employee(tester)
                .deadline(LocalDate.of(2024, 9, 5))
                .estimate((short) 10)
                .type( "собеседование")
                .status(TaskStatus.BACKLOG)
                .build();
        taskService.updateTask(testerPersonalTask.getId(), newData);
        Task updatedTask = taskService.getTaskById(testerPersonalTask.getId());
        assertEquals(newData.getName(), updatedTask.getName());
        assertEquals(newData.getDescription(), updatedTask.getDescription());
        assertEquals(newData.getPriority(), updatedTask.getPriority());
        assertEquals(newData.getDeadline(), updatedTask.getDeadline());
        assertEquals(newData.getType(), updatedTask.getType());
        assertEquals(newData.getStatus(), updatedTask.getStatus());
    }

    @Test
    void deleteTask() {
        taskService.deleteTask(testerPersonalTask.getId());
        assertThrows(NoSuchElementException.class, () -> taskService.getTaskById(testerPersonalTask.getId()));
        assertEquals(taskService.getAllTasksOfEmployee(tester.getId(), Pageable.unpaged()).getSize(), 1);
    }

    @Test
    void getAllTasksOfEmployee() {
        List<Task> testerTasks = taskService.getAllTasksOfEmployee(tester.getId(), Pageable.unpaged()).toList();
        assertEquals(testerTasks.size(), 2);
        assertTrue(testerTasks.stream().allMatch(task -> Objects.equals(task.getEmployee().getId(), tester.getId())));
        assertEquals(taskService.getAllTasksOfEmployee(developer.getId(), Pageable.unpaged()).getSize(), 1);
        assertEquals(testerTasks.size(), 2);
        assertTrue(testerTasks.stream().allMatch(task -> Objects.equals(task.getEmployee().getId(), tester.getId())));

    }

    @Test
    void assignTaskToEmployee() {
        Task newTask =  taskRepository.save(Task.builder()
                .name("Реализовать репозитории")
                .description("реализовать репозитории согласно тз ")
                .priority(TaskPriority.HIGH)
                .employee(developer)
                .deadline(LocalDate.of(2024, 9, 10))
                .estimate((short) 10)
                .type( "проект")
                .status(TaskStatus.NOT_STARTED)
                .build());
        taskService.assignTaskToEmployee(developer.getId(), newTask, Pageable.unpaged());
        assertEquals(2, taskService.getAllTasksOfEmployee(developer.getId(), Pageable.unpaged()).getSize());
    }
}