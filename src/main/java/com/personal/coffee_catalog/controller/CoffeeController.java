package com.personal.coffee_catalog.controller;

import com.personal.coffee_catalog.request.CoffeeRequest;
import com.personal.coffee_catalog.response.CoffeeResponse;
import com.personal.coffee_catalog.response.GenericResponse;
import com.personal.coffee_catalog.service.CoffeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/coffee")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Coffee", description = "Coffee endpoints for managing the coffee catalog")
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
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @Operation(summary = "Get all active coffees",
    description = "Retrieves a paginated list of all active coffees in the catalog")
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
   * @param id ID of the coffee to be retrieved
   * @return ResponseEntity containing a GenericResponse with the CoffeeResponse object
   */
  @Operation(summary = "Get coffee by ID",
    description = "Retrieves a specific coffee from the catalog by its ID")
  @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<GenericResponse<CoffeeResponse>> getCoffee(@PathVariable Long id) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.getCoffee(id))
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
  @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {
    MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Create a new coffee",
    description = "Creates a new coffee entry in the catalog (Admin only)")
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
   * @param id            ID of the coffee to be updated
   * @param coffeeRequest CoffeeRequest object containing the updated details of the coffee
   * @return ResponseEntity containing a GenericResponse with the updated CoffeeResponse object
   */
  @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {
    MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Update an existing coffee",
    description = "Updates an existing coffee entry in the catalog (Admin only)")
  public ResponseEntity<GenericResponse<CoffeeResponse>> updateCoffee(@PathVariable Long id,
    @RequestBody CoffeeRequest coffeeRequest) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.updateCoffee(id, coffeeRequest))
        .message(HttpStatus.OK.getReasonPhrase())
        .build()
    );
  }

  /**
   * Deactivates a coffee entry in the catalog (Soft Delete).
   *
   * @param id ID of the coffee to be deactivated
   * @return Empty response object
   */
  @PatchMapping("/{id}/deactivate")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Deactivate a coffee",
    description = "Deactivates a coffee entry in the catalog [Soft Delete] (Admin only)")
  public ResponseEntity<GenericResponse<CoffeeResponse>> deactivateCoffee(@PathVariable Long id) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.updateCoffee(id, CoffeeRequest.builder().isActive(false).build()))
        .message("Coffee deactivated successfully")
        .build()
    );
  }

  /**
   * Deletes a coffee entry from the catalog (Hard Delete).
   *
   * @param id ID of the coffee to be deleted
   * @return ResponseEntity containing a GenericResponse with the deleted CoffeeResponse object
   */
  @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Delete a coffee",
    description = "Deletes a coffee entry from the catalog [Hard Delete] (Admin only)")
  public ResponseEntity<GenericResponse<CoffeeResponse>> deleteCoffee(@PathVariable Long id) {
    return ResponseEntity.ok(
      GenericResponse.<CoffeeResponse>builder()
        .data(coffeeService.deleteCoffee(id))
        .message("Coffee deleted successfully")
        .build()
    );
  }
}
