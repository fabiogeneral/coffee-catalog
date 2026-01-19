package com.personal.coffee_catalog.service;

import com.personal.coffee_catalog.request.CoffeeRequest;
import com.personal.coffee_catalog.response.CoffeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoffeeService {

  Page<CoffeeResponse> getAllActiveCoffees(Pageable pageable);

  CoffeeResponse getCoffee(Long coffeeId);

  CoffeeResponse createCoffee(CoffeeRequest coffeeRequest);

  CoffeeResponse updateCoffee(Long coffeeId, CoffeeRequest coffeeRequest);

  CoffeeResponse deleteCoffee(Long coffeeId);
}
