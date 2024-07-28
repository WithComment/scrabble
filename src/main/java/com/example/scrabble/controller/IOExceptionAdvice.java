package com.example.scrabble.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IOExceptionAdvice {

  @ExceptionHandler(IOException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String ioExceptionAdvice(IOException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(ClassNotFoundException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String classNotFoundAdvice(ClassNotFoundException ex) {
    return ex.getMessage();
  }
}