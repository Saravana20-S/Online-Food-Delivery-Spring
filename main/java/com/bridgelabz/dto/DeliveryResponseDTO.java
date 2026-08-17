package com.bridgelabz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponseDTO {

    private Long id;
    private String deliveryAddress;
    private String status;
    private Long orderId;
}