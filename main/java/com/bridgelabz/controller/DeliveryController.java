package com.bridgelabz.controller;

import com.bridgelabz.dto.DeliveryRequestDTO;
import com.bridgelabz.dto.DeliveryResponseDTO;
import com.bridgelabz.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/{id}/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO>
    createDelivery(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        deliveryService.createDelivery(
                                id,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<DeliveryResponseDTO>
    getDelivery(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                deliveryService.getDelivery(id)
        );
    }
}