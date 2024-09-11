package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.TaskRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import com.example.EmployeeManager.service.interfaces.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    @Transactional
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Задача по данномому id: %s не найдена", id)));
    }

    @Transactional
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Transactional
    public Task saveTask(Task task) {
        if (taskRepository.findByNameAndEmployee(task.getName(), task.getEmployee()).isEmpty()) {
            return taskRepository.save(task);
        } else {
            throw new RecordExistException(task.getName());
        }
    }

    @Transactional
    public Task updateTask(Long id, Task task) {
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
