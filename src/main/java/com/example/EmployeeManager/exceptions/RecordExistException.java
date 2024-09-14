package com.example.EmployeeManager.exceptions;

public class RecordExistException extends RuntimeException {

    public RecordExistException() {
        super("Record already exists");
    }

    public RecordExistException(String message) {
        super(message);
    }
}
