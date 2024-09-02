package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    @Operation(description = "Returns leave by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @GetMapping("/leave/{id}")
    public Leave getLeaveById(@PathVariable Long id) {
        return leaveService.getLeaveById(id);
    }

    @Operation(description = "Create new leave in database", method = "POST")
    @PostMapping
    public Leave createLeave(@RequestBody Leave leave) {
        return leaveService.createLeave(leave);
    }

    @Operation(description = "Delete leave by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @DeleteMapping("/delete/leave/{id}")
    public void deleteLeaveById(@PathVariable Long id) {
        leaveService.deleteLeaveById(id);
    }

    @Operation(description = "Reschedule leave by giving id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of leave", required = true)
    })
    @PutMapping("/reschedule/leave/{id}")
    public void rescheduleLeave(@PathVariable Long id, @RequestBody Leave leave) {
        leaveService.RescheduleLeave(id, leave);
    }

    @Operation(description = "Return all leaves by giving employee id", method = "PUT", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employee/{id}")
    public List<Leave> getAllByEmployee(@PathVariable Long employeeId) {
        return leaveService.getAllByEmployee(employeeId);
    }
}