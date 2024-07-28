package com.example.scrabble.use_case;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class InvalidPlayAdvice {

  @ExceptionHandler(InvalidPlayException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String invalidPlayHandler(InvalidPlayException ex) {
    return ex.getMessage();
  }
}