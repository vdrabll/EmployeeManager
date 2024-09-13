package com.example.EmployeeManager.controller;

import com.example.EmployeeManager.dto.ErrorDTO;
import com.example.EmployeeManager.exceptions.InvalidLeaveDateExeption;
import com.example.EmployeeManager.exceptions.InvalidTaskStatusExeption;
import com.example.EmployeeManager.exceptions.NotFoundException;
import com.example.EmployeeManager.exceptions.RecordExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(InvalidLeaveDateExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO InvalidLeaveDateExeption(Exception e) {
        return new ErrorDTO(HttpStatus.BAD_REQUEST.name(), e.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidTaskStatusExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO InvalidTaskStatusExeption(Exception e) {
        return new ErrorDTO(HttpStatus.BAD_REQUEST.name(), e.getLocalizedMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO NotFoundException(Exception e) {
        return new ErrorDTO(HttpStatus.NOT_FOUND.name(), e.getLocalizedMessage());
    }

    @ExceptionHandler(RecordExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorDTO RecordExistException(Exception e) {
        return new ErrorDTO(HttpStatus.CONFLICT.name(), e.getLocalizedMessage());
    }

}
