package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.AuthDTO;
import com.example.EmployeeManager.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AutenticationController {
    private final EmployeeService employeeService;
    @GetMapping("/login")
    public void login(@ParameterObject AuthDTO authDTO) {

    }

    @GetMapping("/registration")
    public void registration(@ParameterObject AuthDTO authDTO) {

    }

}
