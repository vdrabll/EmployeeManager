package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAllByIsWorkingNowEquals(Boolean isWorkingNow, Pageable pageable);

    Optional<Employee> findByEmail(String email);

    Page<Employee> findAllByDepartment_Id(Long departmentId, Pageable pageable);

}
