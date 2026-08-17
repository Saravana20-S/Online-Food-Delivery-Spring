package com.bridgelabz.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {

    private Long id;
    private Long foodItemId;
    private String foodName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}