package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Project;
import com.example.EmployeeManager.entity.Task;
import com.example.EmployeeManager.enums.TaskPriority;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByNameAndEmployee(String name, Employee employee);
    Page<Task> findAllByPriority(TaskPriority priority, @ParameterObject Pageable pageable);
    Page<Task> findAllByDeadlineAfter(LocalDate deadline, @ParameterObject Pageable pageable);
    Page<Task> findAllByType(String type, @ParameterObject Pageable pageable);
    Page<Task> findAllByProject(Project project, @ParameterObject Pageable pageable);
    Page<Task> findAllByEmployee(Employee employee, @ParameterObject Pageable pageable);
}
