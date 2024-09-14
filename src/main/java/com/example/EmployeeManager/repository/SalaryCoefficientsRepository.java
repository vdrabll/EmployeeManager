package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.SalaryCoefficients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SalaryCoefficientsRepository extends JpaRepository<SalaryCoefficients, Long> {
    boolean existsByYear(LocalDate date);

    Optional<SalaryCoefficients> getAllByYear(LocalDate year);

}
