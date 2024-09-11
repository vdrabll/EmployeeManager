package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.SalaryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SalaryHistoryService {

    SalaryHistory getSalaryHistoryById(Long id);

    SalaryHistory createSalaryHistory(SalaryHistory salary);

    Page<SalaryHistory> getSalaryHistoryOfEmployee(Long employeeId, Pageable pageable);
}
