package com.example.scrabble.use_case;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class EmployeeNotFoundAdvice {

  @ExceptionHandler(InvalidPlayException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String employeeNotFoundHandler(InvalidPlayException ex) {
    return ex.getMessage();
  }
}