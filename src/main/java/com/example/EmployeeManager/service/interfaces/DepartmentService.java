package com.example.EmployeeManager.service.interfaces;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DepartmentService {

    Department getDepartmentById(Long id);

    Page<Department> getAll(Pageable pageable);

    Department save(Department department);

    Department updateDepartmentById(Long id, Department department);

    void delete(Department department);

    Page<Employee> getAllEmployeesFromDepartment(Long id, Pageable pageable);

    void addEmployeeToDepartment(Long id, Long newEmployeeId);

    void removeEmployeeFromDepartment(Long id, Long employeeId);
}
