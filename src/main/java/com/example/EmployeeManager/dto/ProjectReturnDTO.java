package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

public record ProjectReturnDTO(
        Long id,
        String name,
        String description) {
}