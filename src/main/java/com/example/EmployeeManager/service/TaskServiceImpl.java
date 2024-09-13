package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.TaskStatus;
import com.example.EmployeeManager.exceptions.InvalidTaskStatusExeption;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.TaskRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import com.example.EmployeeManager.service.interfaces.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.DAYS;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    @Transactional
    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Задача по данномому id: %s не найдена", id)));
        task.setEstimate(Duration.ofDays(DAYS.toChronoUnit().between(task.getDeadline(), LocalDate.now())));
        return task;
    }

    @Transactional
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Transactional
    public Task saveTask(Task task) {
        if (taskRepository.findByNameAndEmployee(task.getName(), task.getEmployee()).isPresent()) {
            throw new RecordExistException(task.getName());
        }

        if (task.getStatus() == TaskStatus.EXPIRED) {
            throw new InvalidTaskStatusExeption("Нельзя создать уже созданную задачу");
        }
        task.setEstimate(Duration.ofDays(DAYS.toChronoUnit().between(task.getDeadline(), LocalDate.now())));
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task task) {
        if (task.getStatus() == TaskStatus.DONE) {
            throw new InvalidTaskStatusExeption("Нельзя изменять статус уже выполненных задач");
        }
        Task taskById = getTaskById(id);
        taskById.setName(task.getName());
        taskById.setDescription(task.getDescription());
        taskById.setDeadline(task.getDeadline());
        taskById.setType(task.getType());
        taskById.setStatus(task.getStatus());
        return taskById;
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.delete(getTaskById(id));
    }
    
    @Transactional
    public Page<Task> getAllTasksOfEmployee(Long employeeId, Pageable pageable) {
        Employee employeeById = employeeService.getEmployeeById(employeeId);
        return taskRepository.findAllByEmployee(employeeById, pageable);
    }
    
    @Transactional
    public Task assignTaskToEmployee(Long id, Task task, Pageable pageable) {
        Employee employeeById = employeeService.getEmployeeById(id);
        Task taskById = taskRepository.findById(task.getId()).get();
        employeeById.getTasks().add(taskById);
        taskById.setEmployee(employeeById);
        return task;
    }
}
