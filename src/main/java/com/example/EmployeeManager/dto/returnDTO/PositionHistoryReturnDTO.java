package com.example.EmployeeManager.dto.returnDTO;

import java.time.LocalDate;

public record PositionHistoryReturnDTO(
        Long id,
        LocalDate startDate,
        LocalDate endDate) {
}
