package com.example.EmployeeManager.service;

import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.DepartmentRepository;
import com.example.EmployeeManager.service.interfaces.DepartmentService;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;

    @Transactional
    public Department save(Department data) {
        if (!departmentRepository.existsByName(data.getName())) {
            return departmentRepository.save(data);
        } else {
            log.error("Record with {} already exists", data.getId());
            throw new RecordExistException(data.getName());
        }
    }

    @Transactional
    public void addEmployeeToDepartment(Long departmentId, Long employeeId) {
        Department department = getDepartmentById(departmentId);
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee.getDepartment().stream().noneMatch(department1 -> department1.getId().equals(department.getId()))) {
            department.getEmployees().add(employee);
            departmentRepository.save(department);
        }
    }

    @Transactional(readOnly = true)
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Департамент по данному id: %s не найден", id)));
    }

    @Transactional(readOnly = true)
    public Page<Employee> getAllEmployeesFromDepartment(Long id, Pageable pageable) {
        return employeeService.getAllEmployeesByDepartment(id, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Department> getAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }


    @Transactional
    public Department updateDepartmentById(Department department) {
        Department departmentById = getDepartmentById(department.getId());
        departmentById.setName(department.getName());
        departmentById.setLocation(department.getLocation());
        return departmentById;
    }

    @Transactional
    public void delete(Department department) {
        if (departmentRepository.findById(department.getId()).isPresent()) {
            departmentRepository.delete(department);
        } else {
            throw new NotFoundException("Нельзя удалить запись, которой нет в базе данных.");
        }
    }

    @Transactional
    public void removeEmployeeFromDepartment(Long id, Long employeeId) {
        Department department = getDepartmentById(id);
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee.getDepartment().stream().anyMatch(department1 -> department1.getId().equals(department.getId()))) {
            department.getEmployees().remove(employee);
            departmentRepository.save(department);
        }
    }
}
