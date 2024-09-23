package com.example.EmployeeManager.service;

import com.example.EmployeeManager.dto.AuthDTO;
import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import com.example.EmployeeManager.repository.EmployeeRepository;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private AuthenticationManager authenticationManager;

    @Transactional
    public Employee addEmployee(Employee newEmployee) {
        if (!employeeRepository.existsByEmail(newEmployee.getEmail())) {
            newEmployee.setRole(AuthRole.EMPLOYEE);
            newEmployee.setIsWorkingNow(true);
            return employeeRepository.save(newEmployee);
        } else {
            log.error("Record with {} already exists", newEmployee.getEmail());
            throw new RecordExistException(newEmployee.getEmail());
        }
    }

    @Transactional
    public Employee addChief(Employee newEmployee) {
        if (!employeeRepository.existsByEmail(newEmployee.getEmail())) {
            newEmployee.setRole(AuthRole.CHIEF);
            newEmployee.setIsWorkingNow(true);
            return employeeRepository.save(newEmployee);
        } else {
            log.error("Record with {} already exists", newEmployee.getId());
            throw new RecordExistException(newEmployee.getEmail());
        }
    }

    @Transactional
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()
                -> new NotFoundException(String.format("Сотрудник по данному id: %s не найден", id)));
    }

    @Transactional(readOnly = true)
    public Page<Employee> getAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Employee> getAllWorkingEmployees(Pageable pageable) {
        return employeeRepository.findAllByIsWorkingNowTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Employee> getAllDismissedEmployees(Pageable pageable) {
        return employeeRepository.findAllByIsWorkingNowFalse(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Employee> getAllEmployeesByDepartment(Long departmentId, Pageable pageable) {
        return employeeRepository.findAllByDepartment_Id(departmentId, pageable);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employee) {
        Employee employeeById = getEmployeeById(id);
        employeeById.setIsWorkingNow(employee.getIsWorkingNow());
        employeeById.setFullName(employee.getFullName());
        employeeById.setEmail(employee.getEmail());
        return employeeById;
    }

    @Transactional
    public Employee dismissEmployee(Long id) {
        Employee employeeById = getEmployeeById(id);
        employeeById.setIsWorkingNow(false);
        return employeeById;
    }

    @Transactional(readOnly = true)
    private Employee findByEmail(String username) {
        return employeeRepository.findByEmail(username).orElseThrow(NotFoundException::new);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }

    @Transactional
    private void singIn(AuthDTO auth) {
        try {
            var authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(auth.getEmail(), auth.getPassword()));
            var ctx = SecurityContextHolder.createEmptyContext();
            ctx.setAuthentication(authentication);
        } catch (AuthenticationException e) {
            log.error("Ошибка аутентификации:", e);
        }
    }

    @Transactional(readOnly = true)
    private void registration(AuthDTO auth) {

    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
