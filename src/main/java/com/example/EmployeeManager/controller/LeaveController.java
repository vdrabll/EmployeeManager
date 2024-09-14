package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.LeaveCreateDTO;
import com.example.EmployeeManager.dto.LeaveReturnDTO;
import com.example.EmployeeManager.representation.LeaveRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/leaves")
public class LeaveController {
    private final LeaveRepresentation leaveRepresentation;

    @Operation(description = "Get leave in database by id", method = "POST")
    @GetMapping("/{id}")
    public LeaveReturnDTO getLeaveById(@PathVariable Long id) {
        return leaveRepresentation.getLeaveById(id);
    }

    @Operation(description = "Create new leave", method = "POST")
    @PostMapping
    public LeaveReturnDTO createLeave(LeaveCreateDTO leave) {
        return leaveRepresentation.createLeave(leave);
    }

    @Operation(description = "Delete leave by giving id", method = "DELETE")
    @DeleteMapping("/{id}")
    public void deleteLeaveById(@PathVariable Long id) {
        leaveRepresentation.deleteLeaveById(id);
    }

    @Operation(description = "Reschedule leave by giving id", method = "PATCH")
    @PatchMapping("/{id}")
    public void rescheduleLeave(@PathVariable Long id, @RequestBody LeaveCreateDTO leave) {
        leaveRepresentation.rescheduleLeave(id, leave);
    }

    @Operation(description = "Return all leaves by giving employee id", method = "PUT")
    @GetMapping("/employee/{id}")
    public Page<LeaveReturnDTO> getAllByEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return leaveRepresentation.getAllByEmployee(id, pageable);
    }
}