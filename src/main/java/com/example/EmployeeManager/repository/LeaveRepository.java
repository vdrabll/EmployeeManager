package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findByEmployee_Id(Long id, Pageable pageable);

    boolean existsByEmployee_IdAndEndDate(Long id, LocalDate endDate);

    Page<Leave> findAllByEndDateBetween(LocalDate start, LocalDate end, Pageable pageable);

}
