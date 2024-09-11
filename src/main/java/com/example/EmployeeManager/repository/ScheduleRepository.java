package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findAllByEmployee_Id(Long employee, Pageable pageable);
    Optional<Schedule> findByEmployee_IdAndDate(Long employee, LocalDate date);
}
