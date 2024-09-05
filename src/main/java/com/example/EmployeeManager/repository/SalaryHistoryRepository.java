package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.enums.SalaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, Long> {
    List<SalaryHistory> findAllByEmployee(Employee employee);
    Optional<SalaryHistory> findByEmployeeAndSalaryDateAndType(Employee employee, LocalDate date, SalaryType type);
}