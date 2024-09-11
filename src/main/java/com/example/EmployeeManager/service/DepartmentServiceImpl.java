package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.DepartmentRepository;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.interfaces.DepartmentService;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(String.format("Департамент по данному id: %s не найден", id)));
    }


    @Transactional
    public Page<Department> getAll( Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Transactional
    public Department save(Department department) {
        if (!departmentRepository.findByName(department.getName()).isPresent()) {
            return departmentRepository.save(department);
        } else {
            throw new RecordExistException(department.getName());
        }
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
    public Page<Employee> getAllEmployeesFromDepartment(Long id, Pageable pageable) {
        return employeeService.getAllEmployeesByDepartment(id, pageable);
    }

    @Transactional
    public void addEmployeeToDepartment(Long id, Long newEmployeeId) {
        Department department = getDepartmentById(id);

        Employee employee = employeeService.getEmployeeById(newEmployeeId);

        if (employee.getDepartment().stream().noneMatch(department1 -> department1.getId().equals(department.getId()))) {
            department.getEmployees().add(employee);
            employee.getDepartment().add(department);
        }
    }

    @Transactional
    public void removeEmployeeFromDepartment(Long id, Long employeeId) {
        Department department = getDepartmentById(id);

        Employee employee = employeeService.getEmployeeById(employeeId);

        if (!employee.getDepartment().stream().noneMatch(department1 -> department1.getId().equals(department.getId()))) {
            employee.getDepartment().remove(department);
            department.getEmployees().remove(employee);
        }
    }
}
