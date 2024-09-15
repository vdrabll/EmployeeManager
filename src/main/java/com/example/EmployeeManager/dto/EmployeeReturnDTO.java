package com.example.EmployeeManager.dto;

public record EmployeeReturnDTO(
        Long id,
        String fullName,
        String email,
        String Role) {
}
