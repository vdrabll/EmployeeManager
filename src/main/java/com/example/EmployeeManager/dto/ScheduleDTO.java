package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.enums.LocationType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocationType location;
}
