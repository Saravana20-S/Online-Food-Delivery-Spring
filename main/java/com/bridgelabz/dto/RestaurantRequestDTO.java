package com.bridgelabz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequestDTO {

    @NotBlank(message = "Restaurant name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Restaurant name must be between 2 and 100 characters"
    )
    private String name;

    @NotBlank(message = "Location is required")
    @Size(
            max = 200,
            message = "Location must not exceed 200 characters"
    )
    private String location;
}