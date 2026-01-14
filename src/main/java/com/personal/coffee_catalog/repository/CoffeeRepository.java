package com.personal.coffee_catalog.repository;

import com.personal.coffee_catalog.entity.Coffee;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

  // save(Coffee coffee)
  // saveAll(Iterable<Coffee> coffees)
  // findById(Long id)
  // findAll() / findAll(Pageable pageable) / findAll(Sort sort)
  // findAllById(Iterable<Long> ids)
  // count()
  // existsById(Long id)
  // deleteById(Long id)
  // delete(Coffee coffee)
  // deleteAll()

  // Find by origin country
  List<Coffee> findByOriginCountry(String originCountry);

  // Find by roast level
  List<Coffee> findByRoastLevel(String roastLevel);

  // Find by active status
  List<Coffee> findByIsActiveTrue();

  // Find by origin country and roast level
  List<Coffee> findByOriginCountryAndRoastLevel(String originCountry, String roastLevel);

  // Find by name containing (query) level
  List<Coffee> findByNameContainingIgnoreCase(String name);

  // Find by price range
  List<Coffee> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

  // Find by origin country and active status
  List<Coffee> findByOriginCountryAndIsActive(String originCountry, Boolean isActive);

  // Find by active coffees ordered by name
  List<Coffee> findByIsActiveTrueOrderByNameAsc();
}
