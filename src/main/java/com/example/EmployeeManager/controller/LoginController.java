package com.example.EmployeeManager.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/employee")
    public String employeeLogin() {
        return "employee-login";
    }

    @PreAuthorize("hasRole('ROLE_CHIEF')")
    @GetMapping("/сhief")
    public String chiefLogin() {
        return "chief-login";
    }
}
