package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findByEmployee(Employee employee, Pageable pageable);

    Page<Leave> findByEmployeeAndEndDate(Employee employee, LocalDate endDate, Pageable pageable) ;

    Page<Leave> findAllByEndDateBetween(LocalDate start, LocalDate end, Pageable pageable);

}
