package com.example.EmployeeManager.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @GetMapping("/employee")
    public String employeeLogin() {

        return "";
    }

    @GetMapping("/сhief")
    public String chiefLogin() {
        return "";
    }
}
