package com.example.EmployeeManager.controller;


import com.example.EmployeeManager.entity.Schedule;
import com.example.EmployeeManager.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(description = "Returns Schedule by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of salary Schedule", required = true)
    })
    @GetMapping("/schedule/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @Operation(description = "Create Schedule", method = "POST")
    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @Operation(description = "Delete schedule by giving id", method = "DELETE", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of schedule", required = true)
    })
    @DeleteMapping("delete/schedule/{id}")
    public void deleteScheduleById(@PathVariable Long id) {
        scheduleService.deleteScheduleById(id);
    }

    @Operation(description = "Update schedule by giving id", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of schedule", required = true)
    })
    @PutMapping("update/schedule/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        return scheduleService.updateSchedule(id, schedule);
    }

    @Operation(description = "Return all schedule by employee", method = "GET", parameters = {
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Unique identifier of employee", required = true)
    })
    @GetMapping("/employee/{id}")
    public List<Schedule> getScheduleOfEmployee(@PathVariable Long id) {
        return scheduleService.getScheduleOfEmployee(id);
    }
}