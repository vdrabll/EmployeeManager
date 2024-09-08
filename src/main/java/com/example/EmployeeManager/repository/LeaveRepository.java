package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findByEmployee(Employee employee, @ParameterObject Pageable pageable);

    Optional<List> findByEmployeeAndEndDate(Employee employee, LocalDate endDate);

    Page<Leave> findAllByEndDateBetween(LocalDate start, LocalDate end, @ParameterObject Pageable pageable);

}
