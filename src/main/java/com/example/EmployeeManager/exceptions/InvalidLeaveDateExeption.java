package com.example.EmployeeManager.exceptions;

public class InvalidLeaveDateExeption extends RuntimeException {
    public InvalidLeaveDateExeption(String message) {
        super(message);
    }
}
