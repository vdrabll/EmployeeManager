package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee(Employee employee);

    Optional<List> findByEmployeeAndEndDate(Employee employee, Date endDate);

    List<Leave> findAllByEndDateBetween(Date start, Date end);

    // public List<Leave> findAllByYear(int year); TODO: исправить этот метод и написать к нему тест
}
