package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.LeaveDTO;
import com.example.EmployeeManager.representation.LeaveRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaves")
public class LeaveController {
    private final LeaveRepresentation leaveRepresentation;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns leave by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @GetMapping("/{id}")
    public LeaveDTO getLeaveById(@PathVariable Long id) {
        return leaveRepresentation.getLeaveById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Create new leave in database", method = "POST")
    @PostMapping()
    public LeaveDTO createLeave(LeaveDTO leave) {
        return leaveRepresentation.createLeave(leave);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete leave by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @DeleteMapping("/{id}")
    public void deleteLeaveById(@PathVariable Long id) {
        leaveRepresentation.deleteLeaveById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Reschedule leave by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @PutMapping("/{id}")
    public void rescheduleLeave(@PathVariable Long id, @RequestBody LeaveDTO leave) {
        leaveRepresentation.rescheduleLeave(id, leave);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Return all leaves by giving employee id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employee/{id}")
    public Page<LeaveDTO> getAllByEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return leaveRepresentation.getAllByEmployee(id, pageable);
    }
}