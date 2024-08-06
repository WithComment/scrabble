package com.example.scrabble.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.scrabble.data_access.GameDataAccess.GameDaoException;
import com.example.scrabble.use_case.InvalidPlayException;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void handleException_ShouldReturnInternalServerError() {
    Exception ex = new Exception("An unexpected error occured");
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unexpected error occurred", response.getBody().getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
  }

  @Test
  void handleGameDaoException_ShouldReturnInternalServerError() {
    GameDaoException ex = new GameDaoException("An error occurred while accessing the game data", null);
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An error occurred while accessing the game data", response.getBody().getMessage());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
  }

  @Test
  void handleNoResourceFoundException_ShouldReturnNotFound() {
    NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "Nonexistent URL");
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(ex);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(ex.getMessage(), response.getBody().getMessage());
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
  }

  @Test
  void handleInvalidPlayException_ShouldReturnBadRequest() {
    InvalidPlayException ex = new InvalidPlayException("Invalid move");
    ResponseEntity<ErrorResponse> response = exceptionHandler.handleException(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(ex.getMessage(), response.getBody().getMessage());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
  }
}