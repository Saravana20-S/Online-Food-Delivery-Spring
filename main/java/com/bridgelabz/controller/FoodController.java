package com.bridgelabz.controller;

import com.bridgelabz.dto.ExternalFoodDTO;
import com.bridgelabz.dto.FoodRequestDTO;
import com.bridgelabz.dto.FoodResponseDTO;
import com.bridgelabz.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/api/restaurants/{id}/foods")
    public ResponseEntity<FoodResponseDTO> addFood(
            @PathVariable Long id,
            @Valid @RequestBody FoodRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        foodService.addFood(id, request)
                );
    }

    @GetMapping("/api/restaurants/{id}/foods")
    public ResponseEntity<List<FoodResponseDTO>>
    getRestaurantMenu(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                foodService.getRestaurantMenu(id)
        );
    }

    @GetMapping("/api/foods/{id}/external-details")
    public ResponseEntity<ExternalFoodDTO>
    getExternalFoodDetails(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                foodService.getExternalFoodDetails(id)
        );
    }
}