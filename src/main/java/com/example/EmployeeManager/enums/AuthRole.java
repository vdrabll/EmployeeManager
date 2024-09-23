package com.example.EmployeeManager.enums;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum AuthRole implements GrantedAuthority {
    CHIEF, EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
