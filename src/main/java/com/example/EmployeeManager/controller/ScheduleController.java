package com.example.EmployeeManager.controller;


import com.example.EmployeeManager.dto.ScheduleDTO;
import com.example.EmployeeManager.representation.ScheduleRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleRepresentation scheduleRepresentation;

    @Operation(description = "Get schedule by giving id", method = "GET")
    @GetMapping("/get/{id}")
    public ScheduleDTO getScheduleById(@PathVariable Long id) {
        return scheduleRepresentation.getScheduleById(id);
    }

    @Operation(description = "Delete schedule by giving id", method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public void deleteScheduleById(@PathVariable Long id) {
        scheduleRepresentation.deleteScheduleById(id);
    }

    @Operation(description = "Update schedule by giving id", method = "PUT")
    @PutMapping("/update/{id}")
    public ScheduleDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO schedule) {
        return scheduleRepresentation.updateSchedule(id, schedule);
    }

    @Operation(description = "Get schedule by giving id", method = "GET")
    @GetMapping("/employees/{id}")
    public Page<ScheduleDTO> getScheduleOfEmployee(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return scheduleRepresentation.getScheduleOfEmployee(id, pageable);
    }
}