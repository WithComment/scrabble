package com.example.scrabble.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.scrabble.data_access.GameDataAccess;

@RestControllerAdvice
public class GlobalExceptionHandler {

  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    logger.error("An unexpected error occurred", ex);
    ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(error);
  }

  @ExceptionHandler(GameDataAccess.GameDaoException.class)
  public ResponseEntity<ErrorResponse> handleException(GameDataAccess.GameDaoException ex) {
    logger.error(ex.getMessage(), ex);
    ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while accessing the game data");
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(error);
  }
}
