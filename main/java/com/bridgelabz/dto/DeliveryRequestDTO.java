package com.bridgelabz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRequestDTO {

    @NotBlank(message = "Delivery address is required")
    @Size(
            min = 5,
            max = 250,
            message = "Delivery address must be between 5 and 250 characters"
    )
    private String deliveryAddress;
}