package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SalaryHistoryServiceImpl implements SalaryHistoryService {
    private final SalaryHistoryRepository salaryHistoryRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public SalaryHistory getSalaryHistoryById(Long id) {
        return salaryHistoryRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Проект по данному id: %s не найден", id)));
    }

    @Transactional
    public  SalaryHistory createSalaryHistory(SalaryHistory salary) {
        SalaryHistory salaryHistory = salaryHistoryRepository.findByEmployeeAndSalaryDateAndType(
                salary.getEmployee(),
                salary.getSalaryDate(),
                salary.getType()).orElseThrow(() -> new NoSuchElementException("Выплата данного типа для сотрудника уже существует"));
        return salaryHistoryRepository.save(salary);
    }

    @Transactional
    public void deleteSalaryHistoryById(Long id) {
        salaryHistoryRepository.delete(getSalaryHistoryById(id));
    }

    @Transactional
    public Page<SalaryHistory> getSalaryHistoryOfEmployee(Long employeeId, Pageable pageable) {
        Employee employeeById = employeeRepository.getReferenceById(employeeId);
        return salaryHistoryRepository.findAllByEmployee(employeeById, pageable);
    }
}
