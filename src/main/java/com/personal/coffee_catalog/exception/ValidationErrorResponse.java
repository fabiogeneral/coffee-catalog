package com.personal.coffee_catalog.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error response for validation failures Contains field-specific error messages
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

  /**
   * Timestamp when the error occurred
   */
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime timestamp;

  /**
   * HTTP status code (usually 400)
   */
  private int status;

  /**
   * Error type
   */
  private String error;

  /**
   * General error message
   */
  private String message;

  /**
   * Field-specific validation errors Key = field name, Value = error message
   */
  private Map<String, String> fieldErrors;

  /**
   * Request path where error occurred (optional)
   */
  private String path;
}
