package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.create.DepartmentCreateDTO;
import com.example.EmployeeManager.dto.DepartmentReturnDTO;
import com.example.EmployeeManager.dto.EmployeeReturnDTO;
import com.example.EmployeeManager.entity.Department;
import com.example.EmployeeManager.service.interfaces.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentRepresentation {
    private final DepartmentService departmentService;
    private final EmployeeRepresentation employeeRepresentation;

    public Page<DepartmentReturnDTO> getAllDepartments(Pageable pageable) {
        return departmentService.getAll(pageable).map(this::toDTO);
    }

    public DepartmentReturnDTO createDepartment(DepartmentCreateDTO department) {
        Department data = fromDTO(department);
        departmentService.save(data);
        return toDTO(data);
    }

    public DepartmentReturnDTO updateDepartment(Long id, DepartmentCreateDTO department) {
        Department data = fromDTO(department);
        data.setId(id);
        return toDTO(departmentService.updateDepartmentById(data));
    }

    public DepartmentReturnDTO getDepartmentById(Long id) {
        return toDTO(departmentService.getDepartmentById(id));
    }

    public void deleteDepartment( Long id) {
        departmentService.delete(departmentService.getDepartmentById(id));
    }

    public Page<EmployeeReturnDTO> getAllEmployeesFromDepartment(Long id, Pageable pageable) {
         return departmentService.getAllEmployeesFromDepartment(id, pageable).map(employeeRepresentation::toDTO);
    }

    public void addEmployeeToDepartment(Long id, Long employeeId) {
        departmentService.addEmployeeToDepartment(id,employeeId);
    }

    public void removeEmployeeFromDepartment(Long id, Long employeeId) {
        departmentService.removeEmployeeFromDepartment(id,employeeId);
    }

    public Department fromDTO(DepartmentCreateDTO dto) {
        Department department = Department.builder()
                .name(dto.name())
                .location(dto.location())
                .build();
        return department;
    }

    public DepartmentReturnDTO toDTO(Department department) {
        return new DepartmentReturnDTO(department.getId(), department.getName(), department.getLocation());
    }
}
