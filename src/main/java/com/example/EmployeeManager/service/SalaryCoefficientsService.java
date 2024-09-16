package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.SalaryCoefficients;
import com.example.EmployeeManager.repository.SalaryCoefficientsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryCoefficientsService {
    private final SalaryCoefficientsRepository repository;

    @Transactional
    public Page<SalaryCoefficients> getAllCoefficients(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
