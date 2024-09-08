package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.TaskRepository;
import com.example.EmployeeManager.service.interfaces.TaskService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;

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
        Task byNameAndEmployee = taskRepository.findByNameAndEmployee(task.getName(), task.getEmployee()).orElseThrow(()
                        -> new NoSuchElementException("Запись уже найдена"));

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task task) {
        Task taskById = getTaskById(id);
        taskById.setName(task.getName());
        taskById.setDescription(task.getDescription());
        return taskById;
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.delete(getTaskById(id));
    }
    @Transactional
    public Page<Task> getAllTasksOfEmployee(Long employeeId, Pageable pageable) {
        Employee employeeById = employeeRepository.getReferenceById(employeeId);
        return (Page<Task>) employeeById.getTasks();
    }
    public Page<Task> assignTaskToEmployee(Long id, Task task, Pageable pageable) {
        saveTask(task);
        Employee employeeById = employeeRepository.getReferenceById(id);
        employeeById.getTasks().add(task);
        return (Page<Task>) employeeById.getTasks();
    }
}
