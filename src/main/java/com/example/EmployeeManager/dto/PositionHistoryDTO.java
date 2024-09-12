package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.entity.Position;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PositionHistoryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
