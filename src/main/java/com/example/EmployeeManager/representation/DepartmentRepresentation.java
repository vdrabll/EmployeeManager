package com.example.EmployeeManager.representation;

import com.example.EmployeeManager.dto.DepartmentDTO;
import com.example.EmployeeManager.dto.EmployeeDTO;
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

    public Page<DepartmentDTO> getAllDepartments(Pageable pageable) {
        return departmentService.getAll(pageable).map(this::toDTO);
    }

    public DepartmentDTO createDepartment(DepartmentDTO department) {
        Department data = fromDTO(department);
        return toDTO(departmentService.save(data));
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO department) { //TODO: проверить работает ли
        Department data = fromDTO(department);
        data.setId(id);
        return toDTO(departmentService.updateDepartmentById(id,data));
    }

    public DepartmentDTO getDepartmentById(Long id) {
        return toDTO(departmentService.getDepartmentById(id));
    }

    public void deleteDepartment( Long id) {
        departmentService.delete(departmentService.getDepartmentById(id));
    }

    public Page<EmployeeDTO> getAllEmployeesFromDepartment(Long id, Pageable pageable) {
         return departmentService.getAllEmployeesFromDepartment(id, pageable).map(employeeRepresentation::toDTO);
    }

    public void addEmployeeToDepartment(Long id, Long employeeId) {
        departmentService.addEmployeeToDepartment(id,employeeId);
    }

    public void removeEmployeeFromDepartment(Long id, Long employeeId) {
        departmentService.removeEmployeeFromDepartment(id,employeeId);
    }

    public Department fromDTO(DepartmentDTO dto) {
        Department department = Department.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .build();
        return department;
    }

    public DepartmentDTO toDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO(department.getName(), department.getLocation());
        return dto;
    }
}
