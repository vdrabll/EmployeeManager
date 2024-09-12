package com.example.EmployeeManager.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionHistoryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
