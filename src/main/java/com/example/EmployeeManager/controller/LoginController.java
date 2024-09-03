package com.example.EmployeeManager.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee")
    public String employeeLogin() {
        return "";
    }

    @PreAuthorize("hasRole('CHIEF')")
    @GetMapping("/сhief")
    public String chiefLogin() {
        return "";
    }
}
