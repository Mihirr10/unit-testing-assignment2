package com.unittesting.assignment2.exception;

public class PlayerNotFound extends RuntimeException {

  public PlayerNotFound(String message) {
    super(message);
  }
}
