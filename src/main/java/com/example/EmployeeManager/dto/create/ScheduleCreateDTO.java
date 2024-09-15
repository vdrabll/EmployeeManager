package com.example.EmployeeManager.dto.create;

import com.example.EmployeeManager.enums.LocationType;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record ScheduleCreateDTO(
        @NotEmpty LocalDate date,
        @NotEmpty LocalDateTime startTime,
        @NotEmpty LocalDateTime endTime,
        @NotEmpty LocationType location) {

}