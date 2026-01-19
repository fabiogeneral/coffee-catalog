package com.personal.coffee_catalog.exception;

/**
 * Exception thrown when a requested resource is not found.
 * <p>Results in a 404 Not Found HTTP response.
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
    super(
      String.format("%s %s %s not found", resourceName, fieldName, fieldValue));
  }

}
