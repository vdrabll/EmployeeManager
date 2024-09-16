package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.TaskStatus;
import com.example.EmployeeManager.exceptions.InvalidTaskStatusExeption;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.TaskRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import com.example.EmployeeManager.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Задача по данномому id: %s не найдена", id)));
    }

    @Transactional
    public Task saveTask(Task task) {
        if (taskRepository.existsByNameAndEmployee_id(task.getName(), task.getEmployee().getId())) {
            log.error("Record with {} already exists", task.getName());
            throw new RecordExistException(task.getName());
        }
        if (task.getStatus() == TaskStatus.EXPIRED) {
            log.error("Нельзя создать уже созданную задачу");
            throw new InvalidTaskStatusExeption("Нельзя создать уже созданную задачу");
        }
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Page<Task> getAllTasksOfEmployee(Long id, Pageable pageable) {
        return taskRepository.findAllByEmployee_Id(id, pageable);
    }

    @Transactional
    public Task assignTaskToEmployee(Long id, Task task, Pageable pageable) {
        Employee employeeById = employeeService.getEmployeeById(id);
        Task taskById = taskRepository.findById(task.getId()).orElseThrow(() -> new NotFoundException("Задача не найдена."));
        employeeById.getTasks().add(taskById);
        taskById.setEmployee(employeeById);
        return taskById;
    }

    @Transactional
    public Task updateTask(Task task, Long id) {
        Task taskById = getTaskById(id);
        if (taskById.getStatus() == TaskStatus.DONE) {
            throw new InvalidTaskStatusExeption("Нельзя изменять статус уже выполненных задач");
        }
        taskById.setName(task.getName());
        taskById.setDescription(task.getDescription());
        taskById.setDeadline(task.getDeadline());
        taskById.setType(task.getType());
        taskById.setStatus(task.getStatus());
        return taskById;
    }

    @Transactional
    public void deleteTask(Long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.delete(getTaskById(id));
        } else {
            throw new NotFoundException("Нельзя удалить запись, которой нет в базе данных.");
        }
    }
}
