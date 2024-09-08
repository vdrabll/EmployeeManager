package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.SalaryHistory;

public interface SalaryHistoryService {

    SalaryHistory getSalaryHistoryById(Long id);

    SalaryHistory createSalaryHistory(SalaryHistory salary);
}
