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

    boolean existsByNameAndEmployee_id(String name, Long employee);
    Page<Task> findAllByPriority(TaskPriority priority, Pageable pageable);
    Page<Task> findAllByDeadlineAfter(LocalDate deadline, Pageable pageable);
    Page<Task> findAllByType(String type, Pageable pageable);
    Page<Task> findAllByProject(Project project, Pageable pageable);
    Page<Task> findAllByEmployee_Id(Long employeeId, Pageable pageable);
}
