package com.personal.coffee_catalog.controller;

import com.personal.coffee_catalog.request.CoffeeRequest;
import com.personal.coffee_catalog.response.CoffeeResponse;
import com.personal.coffee_catalog.response.GenericResponse;
import com.personal.coffee_catalog.service.CoffeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@CrossOrigin
//@Tag(name = "Coffee", description = "Coffee APIs")
public class CoffeeController {

  private final CoffeeService coffeeService;

  /**
   * Retrieves a paginated list of all active coffees.
   *
   * @param pageable Pageable object containing pagination and sorting information [page: page
   *                 number (default: 0), size: number of items per page (default: 10), sort:
   *                 sorting criteria (default: id,asc)]
   * @return ResponseEntity containing a GenericResponse with a Page of CoffeeResponse objects
   */
  @GetMapping(value = "/coffee", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<GenericResponse<Page<CoffeeResponse>>> getAllActiveCoffees(
    @PageableDefault(sort = "id") Pageable pageable) {
    return ResponseEntity.ok(
      GenericResponse.<Page<CoffeeResponse>>builder()
        .data(coffeeService.getAllActiveCoffees(pageable))
        .message(HttpStatus.OK.getReasonPhrase())
        .build()
    );
  }

  /**
   * Retrieves a specific coffee by its ID.
   *
   * @param coffeeId ID of the coffee to be retrieved
   * @return ResponseEntity containing a GenericResponse with the CoffeeResponse object
   */
  @GetMapping(value = "/coffee/{coffeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<GenericResponse<CoffeeResponse>> getCoffee(@PathVariable Long coffeeId) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.getCoffee(coffeeId))
        .message(HttpStatus.OK.getReasonPhrase())
        .build()
    );
  }

  /**
   * Creates a new coffee entry in the catalog.
   *
   * @param coffeeRequest CoffeeRequest object containing the details of the coffee to be created
   * @return ResponseEntity containing a GenericResponse with the created CoffeeResponse object
   */
  @PostMapping(value = "/coffee", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {
    MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<GenericResponse<CoffeeResponse>> createCoffee(
    @Valid @RequestBody CoffeeRequest coffeeRequest) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.createCoffee(coffeeRequest))
        .message(HttpStatus.CREATED.getReasonPhrase())
        .build()
    );
  }

  /**
   * Updates an existing coffee entry in the catalog.
   *
   * @param coffeeId      ID of the coffee to be updated
   * @param coffeeRequest CoffeeRequest object containing the updated details of the coffee
   * @return ResponseEntity containing a GenericResponse with the updated CoffeeResponse object
   */
  @PatchMapping(value = "/coffee/{coffeeId}", produces = {
    MediaType.APPLICATION_JSON_VALUE}, consumes = {
    MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<GenericResponse<CoffeeResponse>> updateCoffee(@PathVariable Long coffeeId,
    @RequestBody CoffeeRequest coffeeRequest) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.updateCoffee(coffeeId, coffeeRequest))
        .message(HttpStatus.OK.getReasonPhrase())
        .build()
    );
  }

  /**
   * Deactivates a coffee entry in the catalog (Soft Delete).
   *
   * @param coffeeId ID of the coffee to be deactivated
   * @return Empty response object
   */
  @PatchMapping("/coffee/{coffeeId}/deactivate")
  public ResponseEntity<GenericResponse<CoffeeResponse>> deactivateCoffee(
    @PathVariable Long coffeeId) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.updateCoffee(coffeeId, CoffeeRequest.builder().isActive(false).build()))
        .message("Coffee deactivated successfully")
        .build()
    );
  }

  /**
   * Deletes a coffee entry from the catalog (Hard Delete).
   *
   * @param coffeeId ID of the coffee to be deleted
   * @return ResponseEntity containing a GenericResponse with the deleted CoffeeResponse object
   */
  @DeleteMapping(value = "/coffee/{coffeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<GenericResponse<CoffeeResponse>> deleteCoffee(@PathVariable Long coffeeId) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.deleteCoffee(coffeeId))
        .message("Coffee deleted successfully")
        .build()
    );
  }
}
