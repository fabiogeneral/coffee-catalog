package com.personal.coffee_catalog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.personal.coffee_catalog.model.Coffee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("dev")
@Transactional // Rollback after each test
class CoffeeRepositoryTest {

  @Autowired
  private CoffeeRepository coffeeRepository;

  @Test
  void shouldSaveAndFindCoffee() {
    // Given - Create a coffee
    Coffee coffee = Coffee.builder()
      .name("Ethiopian Yirgacheffe")
      .originCountry("Ethiopia")
      .originRegion("Yirgacheffe")
      .roastLevel("Light")
      .price(new BigDecimal("18.99"))
      .acidityLevel(8)
      .sweetnessLevel(7)
      .bitternessLevel(2)
      .isActive(true)
      .build();

    // When - Save to database
    Coffee savedCoffee = coffeeRepository.save(coffee);

    // Then - Verify it was saved
    assertThat(savedCoffee.getId()).isNotNull();
    assertThat(savedCoffee.getName()).isEqualTo("Ethiopian Yirgacheffe");
    assertThat(savedCoffee.getCreatedAt()).isNotNull();
    assertThat(savedCoffee.getUpdatedAt()).isNotNull();
  }


  @Test
  void shouldFindById() {
    // Given - Save a coffee
    Coffee coffee = Coffee.builder()
      .name("Colombian Supremo")
      .originCountry("Colombia")
      .roastLevel("Medium")
      .price(new BigDecimal("16.99"))
      .isActive(true)
      .build();

    Coffee savedCoffee = coffeeRepository.save(coffee);

    // When - Find by ID
    Optional<Coffee> foundCoffee = coffeeRepository.findById(savedCoffee.getId());

    // Then - Verify it was found
    assertThat(foundCoffee).isPresent();
    assertThat(foundCoffee.get().getName()).isEqualTo("Colombian Supremo");
  }

  @Test
  void shouldFindByOriginCountry() {
    // Given - Save multiple coffees
    Coffee ethiopian = Coffee.builder()
      .name("Ethiopian Yirgacheffe")
      .originCountry("Ethiopia")
      .roastLevel("Light")
      .isActive(true)
      .build();

    Coffee colombian = Coffee.builder()
      .name("Colombian Supremo")
      .originCountry("Colombia")
      .roastLevel("Medium")
      .isActive(true)
      .build();

    coffeeRepository.save(ethiopian);
    coffeeRepository.save(colombian);

    // When - Find by origin country
    List<Coffee> ethiopianCoffees = coffeeRepository.findByOriginCountry("Ethiopia");

    // Then - Verify results
    assertThat(ethiopianCoffees).hasSize(1);
    assertThat(ethiopianCoffees.getFirst().getName()).isEqualTo("Ethiopian Yirgacheffe");
  }

  @Test
  void shouldFindByOriginCountryAndRoastLevel() {
    // Given
    Coffee lightEthiopian = Coffee.builder()
      .name("Ethiopian Light")
      .originCountry("Ethiopia")
      .roastLevel("Light")
      .isActive(true)
      .build();

    Coffee darkEthiopian = Coffee.builder()
      .name("Ethiopian Dark")
      .originCountry("Ethiopia")
      .roastLevel("Dark")
      .isActive(true)
      .build();

    coffeeRepository.save(lightEthiopian);
    coffeeRepository.save(darkEthiopian);

    // When
    List<Coffee> results = coffeeRepository
      .findByOriginCountryAndRoastLevel("Ethiopia", "Light");

    // Then
    assertThat(results).hasSize(1);
    assertThat(results.getFirst().getName()).isEqualTo("Ethiopian Light");
  }

  @Test
  void shouldFindByNameContaining() {
    // Given
    Coffee coffee1 = Coffee.builder()
      .name("Ethiopian Yirgacheffe")
      .originCountry("Ethiopia")
      .roastLevel("Light")
      .isActive(true)
      .build();

    Coffee coffee2 = Coffee.builder()
      .name("Ethiopian Sidamo")
      .originCountry("Ethiopia")
      .roastLevel("Medium")
      .isActive(true)
      .build();

    Coffee coffee3 = Coffee.builder()
      .name("Colombian Supremo")
      .originCountry("Colombia")
      .roastLevel("Medium")
      .isActive(true)
      .build();

    coffeeRepository.save(coffee1);
    coffeeRepository.save(coffee2);
    coffeeRepository.save(coffee3);

    // When - Search for "ethiopian" (case-insensitive)
    List<Coffee> results = coffeeRepository.findByNameContainingIgnoreCase("ethiopian");

    // Then
    assertThat(results).hasSize(2);
  }

  @Test
  void shouldUpdateCoffee() {
    // Given - Save a coffee
    Coffee coffee = Coffee.builder()
      .name("Test Coffee")
      .originCountry("Brazil")
      .roastLevel("Medium")
      .price(new BigDecimal("15.99"))
      .isActive(true)
      .build();

    Coffee savedCoffee = coffeeRepository.save(coffee);
    Long coffeeId = savedCoffee.getId();

    // When - Update the coffee
    savedCoffee.setPrice(new BigDecimal("17.99"));
    savedCoffee.setRoastLevel("Dark");
    coffeeRepository.save(savedCoffee);

    // Then - Verify update
    Coffee updatedCoffee = coffeeRepository.findById(coffeeId).orElseThrow();
    assertThat(updatedCoffee.getPrice()).isEqualByComparingTo("17.99");
    assertThat(updatedCoffee.getRoastLevel()).isEqualTo("Dark");

    // updatedAt should be different from createdAt
    assertThat(updatedCoffee.getUpdatedAt()).isAfter(updatedCoffee.getCreatedAt());
  }

  @Test
  void shouldDeleteCoffee() {
    // Given
    Coffee coffee = Coffee.builder()
      .name("Coffee to Delete")
      .originCountry("Vietnam")
      .roastLevel("Dark")
      .isActive(true)
      .build();

    Coffee savedCoffee = coffeeRepository.save(coffee);
    Long coffeeId = savedCoffee.getId();

    // When
    coffeeRepository.deleteById(coffeeId);

    // Then
    Optional<Coffee> deletedCoffee = coffeeRepository.findById(coffeeId);
    assertThat(deletedCoffee).isEmpty();
  }

  @Test
  void shouldCountCoffees() {
    // Given
    coffeeRepository.save(Coffee.builder()
      .name("Coffee 1")
      .originCountry("Brazil")
      .roastLevel("Light")
      .isActive(true)
      .build());

    coffeeRepository.save(Coffee.builder()
      .name("Coffee 2")
      .originCountry("Colombia")
      .roastLevel("Medium")
      .isActive(true)
      .build());

    // When
    long count = coffeeRepository.count();

    // Then
    assertThat(count).isEqualTo(2);
  }
}
