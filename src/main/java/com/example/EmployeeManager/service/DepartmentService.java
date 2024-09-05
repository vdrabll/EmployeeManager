package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.repository.DepartmentRepository;
import com.example.EmployeeManager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Департамент по данному id: %s не найден", id)));
    }

    @Transactional
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department save(Department department) {
        Department departmentByName = departmentRepository.findByName(department.getName()).orElseThrow(()
                -> new NoSuchElementException("Департамент с таким именем уже найден")); // прочитать про runtime exeption
        return departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartmentById(Long id, Department department) {
        Department departmentById = getDepartmentById(id);
        departmentById.setName(department.getName());
        departmentById.setLocation(department.getLocation());
        return departmentById;
    }

    @Transactional
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    @Transactional
    public List<Employee> getAllEmployeesFromDepartment(Long id) {
        return getDepartmentById(id).getEmployees();
    }

    @Transactional
    public void addEmployeeToDepartment(Long id, Employee newEmployee) {
        Department departmentById = getDepartmentById(id);
        if (departmentById == null) {
            departmentById.getEmployees().add(newEmployee);
        }
    }

    @Transactional
    public void removeEmployeeFromDepartment(Long id, Long employee) {
        List<Employee> allEmployeesFromDepartment = getAllEmployeesFromDepartment(id);
        Employee employeeById = employeeRepository.getReferenceById(employee);
        allEmployeesFromDepartment.remove(employeeById);
    }

}
