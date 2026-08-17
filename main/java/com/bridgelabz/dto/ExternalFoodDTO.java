package com.bridgelabz.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalFoodDTO {

    private Long id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private String source;
}