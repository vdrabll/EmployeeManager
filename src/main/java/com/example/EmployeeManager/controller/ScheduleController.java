package com.example.EmployeeManager.controller;


import com.example.EmployeeManager.dto.ScheduleDTO;
import com.example.EmployeeManager.representation.ScheduleRepresentation;
import com.example.EmployeeManager.service.ScheduleServiceImpl;
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
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleRepresentation scheduleRepresentation;

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Returns Schedule by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of salary Schedule", required = true)
    })
    @GetMapping("/{id}")
    public ScheduleDTO getScheduleById(@PathVariable Long id) {
        return scheduleRepresentation.getScheduleById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Delete schedule by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of schedule", required = true)
    })
    @DeleteMapping("/{id}")
    public void deleteScheduleById(@PathVariable Long id) {
        scheduleRepresentation.deleteScheduleById(id);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @Operation(description = "Update schedule by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of schedule", required = true)
    })
    @PutMapping("/{id}")
    public ScheduleDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO schedule) {
        return scheduleRepresentation.updateSchedule(id, schedule);
    }

    @PreAuthorize("hasRole('ROLE_CHIEF') or hasRole('ROLE_EMPLOYEE')")
    @Operation(description = "Return all schedule by employee", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employees/{id}")
    public Page<ScheduleDTO> getScheduleOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return scheduleRepresentation.getScheduleOfEmployee(id, pageable);
    }
}