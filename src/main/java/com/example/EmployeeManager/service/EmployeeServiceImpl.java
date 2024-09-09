package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;


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
       if (employeeRepository.findByEmail(newEmployee.getEmail()).isEmpty()) {
           return employeeRepository.save(newEmployee);
       } else {
           throw new RecordExistException(newEmployee.getEmail());
       }
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

    @Transactional
    public Page<Employee> getAllEmployeesByDepartment(Long departmentId, Pageable pageable) {
        return employeeRepository.findAllByDepartment_Id(departmentId, pageable);
    }
}
