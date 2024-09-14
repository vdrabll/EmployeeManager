package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.SalaryHistory;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.SalaryHistoryRepository;
import com.example.EmployeeManager.service.interfaces.SalaryHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryHistoryServiceImpl implements SalaryHistoryService {
    private final SalaryHistoryRepository salaryHistoryRepository;

    @Transactional
    public  SalaryHistory createSalaryHistory(SalaryHistory salary) {
        if (salaryHistoryRepository.findByEmployeeAndSalaryDateAndType(salary.getEmployee(), salary.getSalaryDate(), salary.getType()).isEmpty()) {
            return salaryHistoryRepository.save(salary);
        } else {
            log.error("Record with {} already exists", salary.getId());
            throw new RecordExistException(String.valueOf(salary.getId()));
        }
    }

    @Transactional(readOnly = true)
    public SalaryHistory getSalaryHistoryById(Long id) {
        return salaryHistoryRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Проект по данному id: %s не найден", id)));
    }

    @Transactional(readOnly = true)
    public Page<SalaryHistory> getSalaryHistoryOfEmployee(Long employeeId, Pageable pageable) {
        return salaryHistoryRepository.findAllByEmployee_Id(employeeId, pageable);
    }
}
