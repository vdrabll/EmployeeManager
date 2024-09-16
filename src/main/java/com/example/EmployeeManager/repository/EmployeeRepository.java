package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByIsWorkingNowFalse(Pageable pageable);
    Page<Employee> findAllByIsWorkingNowTrue(Pageable pageable);
    boolean existsByEmail(String email);
    Page<Employee> findAllByDepartment_Id(Long departmentId, Pageable pageable);
}
