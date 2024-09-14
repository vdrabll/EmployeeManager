package com.example.EmployeeManager.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record ErrorDTO(
        @NotEmpty String error,
        @NotEmpty String description) {

}
