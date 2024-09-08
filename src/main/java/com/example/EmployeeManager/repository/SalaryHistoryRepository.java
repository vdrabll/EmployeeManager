package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.enums.SalaryType;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, Long> {
    Page<SalaryHistory> findAllByEmployee(Employee employee, @ParameterObject Pageable pageable);
    Optional<SalaryHistory> findByEmployeeAndSalaryDateAndType(Employee employee, LocalDate date, SalaryType type);
}