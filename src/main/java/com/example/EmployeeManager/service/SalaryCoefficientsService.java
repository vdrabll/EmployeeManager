package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.SalaryCoefficients;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.SalaryCoefficientsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryCoefficientsService {

    private final SalaryCoefficientsRepository repository;

    @Transactional
    public SalaryCoefficients createNewCoefficien(SalaryCoefficients salaryCoefficients) {
        if (repository.existsByYear(salaryCoefficients.getYear())) {
            return repository.save(salaryCoefficients);
        } else {
            log.error("Record with {} already exists", salaryCoefficients.getYear());
            throw new RecordExistException(salaryCoefficients.getYear().toString());
        }
    }

    @Transactional(readOnly = true)
    public SalaryCoefficients getCoefficientOfYear(LocalDate date) {
        return repository.getAllByYear(LocalDate.now()).orElseThrow(() ->
                new NotFoundException("Запись за данный год не найдена"));
    }

    @Transactional
    public Page<SalaryCoefficients> getAllCoefficients(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
