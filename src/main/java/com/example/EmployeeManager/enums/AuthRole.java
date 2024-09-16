package com.example.EmployeeManager.enums;

import org.apache.coyote.BadRequestException;

import java.util.Arrays;

public enum AuthRole {
    CHIEF, EMPLOYEE;

    public static AuthRole fromString(String role) {
        for (AuthRole value: values()) {
            if (value.name().equalsIgnoreCase(role)) return value;
        }
        return null;
    }
}
