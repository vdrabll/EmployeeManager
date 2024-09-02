package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByNameAndEmployee(String name, Employee employee);
    List<Task> findAllByDeadlineAfter(Date deadline);
    List<Task> findAllByType(String type);
    List<Task> findAllByPriority(TaskPriority priority);
    List<Task> findAllByProject(Project project);
    List<Task> findAllByEmployee(Employee employee);
}
