package com.personal.coffee_catalog.exception;

/**
 * Exception thrown when a bad request is made.
 * <p>Results in a 400 Bad Request HTTP response.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
