package com.bridgelabz.service;

import com.bridgelabz.dto.ExternalFoodDTO;
import com.bridgelabz.dto.FoodRequestDTO;
import com.bridgelabz.dto.FoodResponseDTO;
import com.bridgelabz.exception.ExternalFoodException;
import com.bridgelabz.exception.FoodNotFoundException;
import com.bridgelabz.model.FoodItem;
import com.bridgelabz.model.Restaurant;
import com.bridgelabz.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodItemRepository foodItemRepository;
    private final RestaurantService restaurantService;
    private final HttpClient httpClient;
    private final JsonMapper jsonMapper;

    public FoodResponseDTO addFood(
            Long restaurantId,
            FoodRequestDTO request) {

        Restaurant restaurant =
                restaurantService.getRestaurantEntity(restaurantId);

        FoodItem foodItem = FoodItem.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .restaurant(restaurant)
                .build();

        FoodItem savedFood =
                foodItemRepository.save(foodItem);

        return mapToResponse(savedFood);
    }

    public List<FoodResponseDTO> getRestaurantMenu(
            Long restaurantId) {

        restaurantService.getRestaurantEntity(restaurantId);

        return foodItemRepository
                .findByRestaurantId(restaurantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public FoodItem getFoodEntity(Long id) {

        return foodItemRepository.findById(id)
                .orElseThrow(() ->
                        new FoodNotFoundException(
                                "Food item not found with id: " + id
                        )
                );
    }

    public ExternalFoodDTO getExternalFoodDetails(
            Long foodId) {

        String url =
                "https://dummyjson.com/products/" + foodId;

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(5))
                        .GET()
                        .build();

        try {

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            int statusCode = response.statusCode();

            if (statusCode >= 400 && statusCode < 500) {

                throw new ExternalFoodException(
                        "External service returned client error: "
                                + statusCode
                );
            }

            if (statusCode >= 500) {

                throw new ExternalFoodException(
                        "External service returned server error: "
                                + statusCode
                );
            }

            if (statusCode != 200) {

                throw new ExternalFoodException(
                        "Unexpected external service status: "
                                + statusCode
                );
            }

            JsonNode node =
                    jsonMapper.readTree(response.body());

            return ExternalFoodDTO.builder()
                    .id(node.path("id").asLong())
                    .name(node.path("title").asString())
                    .description(
                            node.path("description").asString()
                    )
                    .category(
                            node.path("category").asString()
                    )
                    .price(
                            BigDecimal.valueOf(
                                    node.path("price").asDouble()
                            )
                    )
                    .source("dummyjson.com")
                    .build();

        } catch (ExternalFoodException ex) {

            throw ex;

        } catch (IOException ex) {

            throw new ExternalFoodException(
                    "Unable to connect to external food service",
                    ex
            );

        } catch (InterruptedException ex) {

            Thread.currentThread().interrupt();

            throw new ExternalFoodException(
                    "External food service request was interrupted",
                    ex
            );

        } catch (Exception ex) {

            throw new ExternalFoodException(
                    "Failed to process external food service response",
                    ex
            );
        }
    }

    private FoodResponseDTO mapToResponse(
            FoodItem foodItem) {

        return FoodResponseDTO.builder()
                .id(foodItem.getId())
                .name(foodItem.getName())
                .price(foodItem.getPrice())
                .category(foodItem.getCategory())
                .restaurantId(
                        foodItem.getRestaurant().getId()
                )
                .restaurantName(
                        foodItem.getRestaurant().getName()
                )
                .build();
    }
}