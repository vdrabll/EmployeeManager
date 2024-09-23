package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.SalaryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface SalaryHistoryService {

    SalaryHistory getSalaryHistoryById(Long id);

    SalaryHistory createSalaryHistory(SalaryHistory salary);

    Page<SalaryHistory> getSalaryHistoryOfEmployee(Long employeeId, Pageable pageable);

    void createAdvancePayment();

    void createSalaryPayment();

    void createBonusPayment();

    BigDecimal calculateAdvance(Employee employee, BigDecimal advancePercentage);

    BigDecimal calculateSalary(Employee employee, BigDecimal advancePercentage);

    BigDecimal calculateBonus(Employee employee, BigDecimal bonusPercentage);
}
