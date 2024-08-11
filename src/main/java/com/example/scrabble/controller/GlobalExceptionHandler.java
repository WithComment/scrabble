package com.example.scrabble.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.scrabble.data_access.GameDataAccess.GameDaoException;
import com.example.scrabble.use_case.InvalidPlayException;

/**
 * Global exception handler for handling various exceptions across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles generic exceptions.
   *
   * @param ex the exception to handle
   * @return a ResponseEntity containing the error response
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    logger.error("An unexpected error occurred", ex);
    ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(error);
  }

  /**
   * Handles GameDaoException.
   *
   * @param ex the exception to handle
   * @return a ResponseEntity containing the error response
   */
  @ExceptionHandler(GameDaoException.class)
  public ResponseEntity<ErrorResponse> handleException(GameDaoException ex) {
    logger.error(ex.getMessage(), ex);
    ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while accessing the game data");
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(error);
  }

  /**
   * Handles NoResourceFoundException.
   *
   * @param ex the exception to handle
   * @return a ResponseEntity containing the error response
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(NoResourceFoundException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON)
        .body(error);
  }

  /**
   * Handles InvalidPlayException.
   *
   * @param ex the exception to handle
   * @return a ResponseEntity containing the error response
   */
  @ExceptionHandler(InvalidPlayException.class)
  public ResponseEntity<ErrorResponse> handleException(InvalidPlayException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    logger.error(ex.getMessage(), ex);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(error);
  }
}