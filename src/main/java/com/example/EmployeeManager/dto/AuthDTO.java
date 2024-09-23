package com.example.EmployeeManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthDTO {
    private String email;
    private String password;
    private String fullName;
}
