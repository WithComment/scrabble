package com.example.scrabble.controller;

/**
 * Represents an error response with a status code and message.
 */
public class ErrorResponse {
  private final int status;
  private final String message;

  /**
   * Constructs an ErrorResponse with the specified status and message.
   *
   * @param status the HTTP status code
   * @param message the error message
   */
  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  /**
   * Gets the HTTP status code.
   *
   * @return the HTTP status code
   */
  public int getStatus() {
    return status;
  }

  /**
   * Gets the error message.
   *
   * @return the error message
   */
  public String getMessage() {
    return message;
  }
}