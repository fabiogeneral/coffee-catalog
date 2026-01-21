package com.personal.coffee_catalog.service;

import com.personal.coffee_catalog.mapper.CoffeeMapper;
import com.personal.coffee_catalog.model.Coffee;
import com.personal.coffee_catalog.repository.CoffeeRepository;
import com.personal.coffee_catalog.request.CoffeeRequest;
import com.personal.coffee_catalog.response.CoffeeResponse;
import com.personal.coffee_catalog.utils.CommonHelper;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {

  private final CoffeeRepository coffeeRepository;
  private final CoffeeMapper coffeeMapper;
  private static final String MODEL = "Coffee";

  /**
   * Get all active coffees
   */
  public Page<CoffeeResponse> getAllActiveCoffees(Pageable pageable) {
    Page<Coffee> allActiveCoffees = coffeeRepository.findByIsActiveTrue(pageable);

    return allActiveCoffees.map(coffeeMapper::coffeeToResponse);
  }

  /**
   * Get coffee by ID
   */
  public CoffeeResponse getCoffee(Long coffeeId) {
    Coffee coffee = CommonHelper.findByIdOrThrow(MODEL, coffeeRepository, coffeeId);

    if (!coffee.getIsActive()) {
      throw new IllegalArgumentException("Coffee ID " + coffeeId + " is not active");
    }

    return coffeeMapper.coffeeToResponse(coffee);
  }

  /**
   * Create coffee
   */
  public CoffeeResponse createCoffee(CoffeeRequest coffeeRequest) {
    coffeeRequest.setIsActive(true);
    Coffee savedCoffee = coffeeRepository.save(coffeeMapper.requestToCoffee(coffeeRequest));

    return coffeeMapper.coffeeToResponse(savedCoffee);
  }

  /**
   * Update coffee
   */
  public CoffeeResponse updateCoffee(Long coffeeId, CoffeeRequest coffeeRequest) {
    Coffee coffee = CommonHelper.findByIdOrThrow(MODEL, coffeeRepository, coffeeId);

    CommonHelper.validateAndSet(coffeeRequest.getName(), coffee::setName, "Name", true);
    CommonHelper.validateAndSet(coffeeRequest.getOriginCountry(), coffee::setOriginCountry,
      "Origin Country", true);
    CommonHelper.validateAndSet(coffeeRequest.getRoastLevel(), coffee::setRoastLevel,
      "Roast Level", true);
    CommonHelper.validateAndSet(coffeeRequest.getOriginRegion(), coffee::setOriginRegion,
      "Origin Region", false);
    // TODO: add other fields
    if (coffeeRequest.getPrice() != null) {
      if (coffeeRequest.getPrice().doubleValue() <= 0) {
        throw new IllegalArgumentException("Price must be positive");
      }
      coffee.setPrice(coffeeRequest.getPrice().setScale(2, RoundingMode.HALF_UP));
    }
    if (coffeeRequest.getIsActive() != null) {
      coffee.setIsActive(coffeeRequest.getIsActive());
    }

    Coffee updatedCoffee = coffeeRepository.save(coffee);

    return coffeeMapper.coffeeToResponse(updatedCoffee);
  }

  /**
   * Delete coffee
   */
  public CoffeeResponse deleteCoffee(Long coffeeId) {
    Coffee coffee = CommonHelper.findByIdOrThrow(MODEL, coffeeRepository, coffeeId);

    coffeeRepository.delete(coffee);

    return coffeeMapper.coffeeToResponse(coffee);
  }
}
