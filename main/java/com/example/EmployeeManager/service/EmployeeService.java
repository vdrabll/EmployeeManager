package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.repository.EmployeeRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@NoArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Сотрудник по данному id: %s не найден", id)));
    }

    @Transactional
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee addEmployee(Employee newEmployee) {
       employeeRepository.findByEmail(newEmployee.getEmail()).orElseThrow(() ->
               new NoSuchElementException("Такого сотрудника не существует"));
       return employeeRepository.save(newEmployee);
    }

   @Transactional
    public Employee updateEmployee(Long id, Employee employee) {
        Employee employeeById = getEmployeeById(id);
        employeeById.setFullName(employeeById.getFullName());
        employeeById.setEmail(employeeById.getEmail());
        return  employeeById;
    }

    @Transactional
    public Employee dismissEmployee(Long id) {
        Employee employeeById = getEmployeeById(id);
        employeeById.setIsWorkingNow(false);
        return employeeById;
    }

    @Transactional
    public List<Employee> getAllWorkingEmployees() {
        return employeeRepository.findAllByIsWorkingNowEquals(true);
    }

    @Transactional
    public List<Employee> getAllDismissedEmployees() {
        return employeeRepository.findAllByIsWorkingNowEquals(false);
    }
}
