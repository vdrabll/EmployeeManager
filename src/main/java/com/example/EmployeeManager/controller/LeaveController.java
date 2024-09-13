package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.LeaveDTO;
import com.example.EmployeeManager.representation.LeaveRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaves")
public class LeaveController {
    private final LeaveRepresentation leaveRepresentation;

    @Operation(description = "Get leave in database by id", method = "POST")
    @GetMapping("/get/{id}")
    public LeaveDTO getLeaveById(@PathVariable Long id) {
        return leaveRepresentation.getLeaveById(id);
    }

    @Operation(description = "Create new leave in database", method = "POST")
    @PostMapping("/create/")
    public LeaveDTO createLeave(LeaveDTO leave) {
        return leaveRepresentation.createLeave(leave);
    }

    @Operation(description = "Delete leave by giving id", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public void deleteLeaveById(@PathVariable Long id) {
        leaveRepresentation.deleteLeaveById(id);
    }

    @Operation(description = "Reschedule leave by giving id", method = "PUT")
    @PutMapping("/update/{id}")
    public void rescheduleLeave(@PathVariable Long id, @RequestBody LeaveDTO leave) {
        leaveRepresentation.rescheduleLeave(id, leave);
    }

    @Operation(description = "Return all leaves by giving employee id", method = "PUT")
    @GetMapping("/employee/{id}")
    public Page<LeaveDTO> getAllByEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return leaveRepresentation.getAllByEmployee(id, pageable);
    }
}