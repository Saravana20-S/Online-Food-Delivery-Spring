package com.bridgelabz.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String category;
    private Long restaurantId;
    private String restaurantName;
}