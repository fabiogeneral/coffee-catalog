package com.personal.coffee_catalog.mapper;

import com.personal.coffee_catalog.entity.Coffee;
import com.personal.coffee_catalog.request.CoffeeRequest;
import com.personal.coffee_catalog.response.CoffeeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {

  CoffeeResponse coffeeToResponse(Coffee coffee);

  Coffee requestToCoffee(CoffeeRequest coffeeRequest);
}
