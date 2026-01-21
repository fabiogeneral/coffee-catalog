package com.personal.coffee_catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "coffees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "roaster_id", nullable = false)
  private Integer roasterId;

  @Column(nullable = false)
  @NotBlank(message = "Coffee name is required")
  @Size(max = 255, message = "Name must be less than 255 characters")
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "origin_country", nullable = false, length = 100)
  private String originCountry;

  @Column(name = "origin_region")
  private String originRegion;

  @Column(name = "altitude_meters")
  private Integer altitudeMeters;

  @Column
  private String varietal;

  @Column(name = "processing_method", length = 100)
  private String processingMethod;

  @Column(name = "roast_level", nullable = false, length = 50)
  @NotBlank(message = "Roast level is required")
  private String roastLevel;

  @Column(name = "roast_date")
  private LocalDate roastDate;

  @Column(precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "weight_grams")
  private Integer weightGrams;

  @Column(name = "acidity_level")
  @Min(value = 1, message = "Acidity level must be between 1 and 10")
  @Max(value = 10, message = "Acidity level must be between 1 and 10")
  private Integer acidityLevel;

  @Column(name = "sweetness_level")
  @Min(value = 1, message = "Sweetness level must be between 1 and 10")
  @Max(value = 10, message = "Sweetness level must be between 1 and 10")
  private Integer sweetnessLevel;

  @Column(name = "bitterness_level")
  @Min(value = 1, message = "Bitterness level must be between 1 and 10")
  @Max(value = 10, message = "Bitterness level must be between 1 and 10")
  private Integer bitternessLevel;

  @Column(name = "image_url", length = 500)
  private String imageUrl;

  @Column(name = "is_active")
  private Boolean isActive = true;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
