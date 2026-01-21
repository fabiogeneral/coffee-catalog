package com.personal.coffee_catalog.utils;

import com.personal.coffee_catalog.exception.ResourceNotFoundException;
import java.util.function.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public class CommonHelper {

  private CommonHelper() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Find model by ID or throw exception if not found
   *
   * @param model      Model name
   * @param repository JPA repository
   * @param <T>        Model type
   * @param id         Model ID
   * @return Model
   * @throws IllegalArgumentException if model not found
   */
  public static <T> T findByIdOrThrow(String model, JpaRepository<T, Long> repository, Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(model, "id", id));
  }

  /**
   * Validate and set value using setter if value is not null
   *
   * @param value      Value to set
   * @param setter     Setter function
   * @param fieldName  Field name for error message
   * @param blankCheck Whether to check for blank strings
   * @param <T>        Value type
   * @throws IllegalArgumentException if value is blank and blankCheck is true
   */
  public static <T> void validateAndSet(T value, Consumer<T> setter, String fieldName,
    Boolean blankCheck) {
    if (value != null) {
      String stringValue = value.toString();
      if (stringValue.isBlank() && blankCheck) {
        throw new IllegalArgumentException(String.format("%s cannot be blank", fieldName));
      }
      setter.accept(value);
    }
  }
}
