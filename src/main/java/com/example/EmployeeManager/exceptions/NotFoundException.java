package com.example.EmployeeManager.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Not found entity");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
