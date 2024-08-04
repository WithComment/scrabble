package com.example.scrabble.use_case;

public class InvalidPlayException extends RuntimeException {

  public InvalidPlayException(String msg) {
    super(msg);
  }

}
