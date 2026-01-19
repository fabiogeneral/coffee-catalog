package com.personal.coffee_catalog.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard error response for API errors
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  /**
   * Timestamp when the error occurred
   */
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime timestamp;

  /**
   * HTTP status code (404, 400, 500, etc.)
   */
  private int status;

  /**
   * Error type (Not Found, Bad Request, etc.)
   */
  private String error;

  /**
   * Human-readable error message
   */
  private String message;

  /**
   * Request path where error occurred (optional)
   */
  private String path;
}
