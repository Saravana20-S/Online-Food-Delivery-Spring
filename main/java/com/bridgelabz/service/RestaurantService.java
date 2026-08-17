package com.bridgelabz.service;

import com.bridgelabz.dto.RestaurantRequestDTO;
import com.bridgelabz.dto.RestaurantResponseDTO;
import com.bridgelabz.exception.RestaurantNotFoundException;
import com.bridgelabz.model.Restaurant;
import com.bridgelabz.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantResponseDTO createRestaurant(
            RestaurantRequestDTO request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();

        Restaurant savedRestaurant =
                restaurantRepository.save(restaurant);

        return mapToResponse(savedRestaurant);
    }

    public Restaurant getRestaurantEntity(Long id) {

        return restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(
                                "Restaurant not found with id: " + id
                        )
                );
    }

    private RestaurantResponseDTO mapToResponse(
            Restaurant restaurant) {

        return RestaurantResponseDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .build();
    }
}