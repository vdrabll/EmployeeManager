package com.example.EmployeeManager.exceptions;

public class InvalidTaskStatusExeption extends RuntimeException {
    public InvalidTaskStatusExeption() {
        super("Неккоректный статус созданной задачи");
    }

    public InvalidTaskStatusExeption(String message) {
        super(message);
    }

}
