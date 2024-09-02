package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.server.core.LastInvocationAware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private EmployeeRepository employeeRepository;

    @Transactional
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Задача по данномому id: %s не найдена", id)));
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
    @org.springframework.transaction.annotation.Transactional
    public List<Task> getAllTasksOfEmployee(Long employeeId) {
        Employee employeeById = employeeRepository.getReferenceById(employeeId);
        return employeeById.getTasks();
    }
    public List<Task> assignTaskToEmployee(Long id, Task task) {
        saveTask(task);
        Employee employeeById = employeeRepository.getReferenceById(id);
        employeeById.getTasks().add(task);
        return employeeById.getTasks();
    }
}
