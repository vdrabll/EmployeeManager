package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@NoArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Сотрудник по данному id: %s не найден", id)));
    }

    @Transactional
    public Page<Employee> getAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable);
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
    public Page<Employee> getAllWorkingEmployees(Pageable pageable) {
        return employeeRepository.findAllByIsWorkingNowEquals(true, pageable);
    }

    @Transactional
    public Page<Employee> getAllDismissedEmployees(Pageable pageable) {
        return employeeRepository.findAllByIsWorkingNowEquals(false, pageable);
    }
}
