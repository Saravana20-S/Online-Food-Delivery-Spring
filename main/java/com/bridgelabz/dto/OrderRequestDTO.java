package com.bridgelabz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotEmpty(message = "Order must contain at least one food item")
    @Valid
    private List<OrderItemRequestDTO> items;
}