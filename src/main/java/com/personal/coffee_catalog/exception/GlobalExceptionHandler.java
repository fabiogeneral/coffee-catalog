package com.personal.coffee_catalog.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the entire application Catches exceptions from all controllers and
 * returns proper HTTP responses
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Handle ResourceNotFoundException Returns 404 Not Found
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
    ResourceNotFoundException ex,
    HttpServletRequest request) {

    log.error("Resource not found: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .status(HttpStatus.NOT_FOUND.value())
      .error("Not Found")
      .message(ex.getMessage())
      .path(request.getRequestURI())
      .build();

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(errorResponse);
  }

  /**
   * Handle BadRequestException Returns 400 Bad Request
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(
    BadRequestException ex,
    HttpServletRequest request) {

    log.error("Bad request: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .status(HttpStatus.BAD_REQUEST.value())
      .error("Bad Request")
      .message(ex.getMessage())
      .path(request.getRequestURI())
      .build();

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(errorResponse);
  }


  /**
   * Handle validation errors from @Valid annotation Returns 400 Bad Request with field-specific
   * errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationException(
    MethodArgumentNotValidException ex,
    HttpServletRequest request) {

    log.error("Validation failed: {}", ex.getMessage());

    // Extract field errors
    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      fieldErrors.put(fieldName, errorMessage);
    });

    ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .status(HttpStatus.BAD_REQUEST.value())
      .error("Validation Failed")
      .message("Invalid input data")
      .fieldErrors(fieldErrors)
      .path(request.getRequestURI())
      .build();

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(errorResponse);
  }

  /**
   * Handle IllegalArgumentException Returns 400 Bad Request
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
    IllegalArgumentException ex,
    HttpServletRequest request) {

    log.error("Illegal argument: {}", ex.getMessage());

    ErrorResponse errorResponse = ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .status(HttpStatus.BAD_REQUEST.value())
      .error("Bad Request")
      .message(ex.getMessage())
      .path(request.getRequestURI())
      .build();

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(errorResponse);
  }

  /**
   * Handle all other unexpected exceptions Returns 500 Internal Server Error
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(
    Exception ex,
    HttpServletRequest request) {

    log.error("Unexpected error occurred", ex);

    ErrorResponse errorResponse = ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
      .error("Internal Server Error")
      .message("An unexpected error occurred. Please try again later.")
      .path(request.getRequestURI())
      .build();

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(errorResponse);
  }
}
