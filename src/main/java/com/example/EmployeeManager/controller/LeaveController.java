package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.service.LeaveServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveServiceImpl leaveService;

    @Operation(description = "Returns leave by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @GetMapping("/{id}")
    public Leave getLeaveById(@PathVariable Long id) {
        return leaveService.getLeaveById(id);
    }

    @Operation(description = "Create new leave in database", method = "POST")
    @PostMapping()
    public Leave createLeave(@RequestBody Leave leave) {
        return leaveService.createLeave(leave);
    }

    @Operation(description = "Delete leave by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @DeleteMapping("/{id}")
    public void deleteLeaveById(@PathVariable Long id) {
        leaveService.deleteLeaveById(id);
    }

    @Operation(description = "Reschedule leave by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @PutMapping("/{id}")
    public void rescheduleLeave(@PathVariable Long id, @RequestBody Leave leave) {
        leaveService.rescheduleLeave(id, leave);
    }

    @Operation(description = "Return all leaves by giving employee id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employee/{id}")
    public Page<Leave> getAllByEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return leaveService.getAllByEmployee(id, pageable);
    }
}