package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.enums.LocationType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record ScheduleReturnDTO(
        Long id,
        LocalDate date,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocationType location) {
}
