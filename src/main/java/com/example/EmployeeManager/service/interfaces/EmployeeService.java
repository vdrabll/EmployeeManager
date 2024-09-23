package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService {

    Employee getEmployeeById(Long id);

    Page<Employee> getAllEmployee(Pageable pageable);

    Employee addEmployee(Employee newEmployee);

    Employee updateEmployee(Long id, Employee employee);

    Employee dismissEmployee(Long id);

    Page<Employee> getAllWorkingEmployees(Pageable pageable);

    Page<Employee> getAllDismissedEmployees(Pageable pageable);

    Page<Employee> getAllEmployeesByDepartment(Long departmentId, Pageable pageable);

    Employee addChief(Employee newEmployee);

}
