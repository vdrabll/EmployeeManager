package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
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
    private final EmployeeService employeeService;

    @Transactional
    public SalaryHistory getSalaryHistoryById(Long id) {
        return salaryHistoryRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Проект по данному id: %s не найден", id)));
    }

    @Transactional
    public  SalaryHistory createSalaryHistory(SalaryHistory salary) {
        if (salaryHistoryRepository.findByEmployeeAndSalaryDateAndType(salary.getEmployee(), salary.getSalaryDate(), salary.getType()).isEmpty()) {
            return salaryHistoryRepository.save(salary);
        } else {
            throw new RecordExistException(String.valueOf(salary.getId()));
        }
    }

    @Transactional
    public void deleteSalaryHistoryById(Long id) {
        salaryHistoryRepository.delete(getSalaryHistoryById(id));
    }

    @Transactional
    public Page<SalaryHistory> getSalaryHistoryOfEmployee(Long employeeId, Pageable pageable) {
        Employee employeeById = employeeService.getEmployeeById(employeeId);
        return salaryHistoryRepository.findAllByEmployee(employeeById, pageable);
    }
}
