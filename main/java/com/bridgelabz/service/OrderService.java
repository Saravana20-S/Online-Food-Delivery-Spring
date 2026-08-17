package com.bridgelabz.service;

import com.bridgelabz.dto.OrderItemRequestDTO;
import com.bridgelabz.dto.OrderItemResponseDTO;
import com.bridgelabz.dto.OrderRequestDTO;
import com.bridgelabz.dto.OrderResponseDTO;
import com.bridgelabz.exception.CustomerNotFoundException;
import com.bridgelabz.exception.FoodNotFoundException;
import com.bridgelabz.exception.InvalidOrderException;
import com.bridgelabz.exception.OrderNotFoundException;
import com.bridgelabz.model.Customer;
import com.bridgelabz.model.FoodItem;
import com.bridgelabz.model.Order;
import com.bridgelabz.model.OrderItem;
import com.bridgelabz.repository.CustomerRepository;
import com.bridgelabz.repository.FoodItemRepository;
import com.bridgelabz.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final FoodItemRepository foodItemRepository;

    @Transactional
    public OrderResponseDTO placeOrder(
            OrderRequestDTO request) {

        Customer customer =
                customerRepository.findById(
                                request.getCustomerId()
                        )
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found with id: "
                                                + request.getCustomerId()
                                )
                        );

        if (request.getItems() == null
                || request.getItems().isEmpty()) {

            throw new InvalidOrderException(
                    "Order must contain at least one food item"
            );
        }

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .status("PLACED")
                .totalAmount(BigDecimal.ZERO)
                .customer(customer)
                .orderItems(new ArrayList<>())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemRequest :
                request.getItems()) {

            FoodItem foodItem =
                    foodItemRepository.findById(
                                    itemRequest.getFoodItemId()
                            )
                            .orElseThrow(() ->
                                    new FoodNotFoundException(
                                            "Food item not found with id: "
                                                    + itemRequest
                                                    .getFoodItemId()
                                    )
                            );

            if (itemRequest.getQuantity() == null
                    || itemRequest.getQuantity() <= 0) {

                throw new InvalidOrderException(
                        "Quantity must be greater than zero"
                                + " for food id: "
                                + itemRequest.getFoodItemId()
                );
            }

            BigDecimal subtotal =
                    foodItem.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemRequest.getQuantity()
                                    )
                            );

            OrderItem orderItem =
                    OrderItem.builder()
                            .quantity(
                                    itemRequest.getQuantity()
                            )
                            .price(foodItem.getPrice())
                            .order(order)
                            .foodItem(foodItem)
                            .build();

            order.getOrderItems().add(orderItem);

            total = total.add(subtotal);
        }

        order.setTotalAmount(total);

        Order savedOrder =
                orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrder(Long id) {

        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(order);
    }

    @Transactional
    public OrderResponseDTO updateStatus(
            Long id,
            String status) {

        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with id: "
                                                + id
                                )
                        );

        String normalizedStatus =
                status == null
                        ? ""
                        : status.trim().toUpperCase();

        List<String> allowedStatuses = List.of(
                "PLACED",
                "CONFIRMED",
                "PREPARING",
                "OUT_FOR_DELIVERY",
                "DELIVERED",
                "CANCELLED"
        );

        if (!allowedStatuses.contains(normalizedStatus)) {

            throw new InvalidOrderException(
                    "Invalid order status. Allowed values: "
                            + allowedStatuses
            );
        }

        if ("DELIVERED".equals(order.getStatus())
                || "CANCELLED".equals(order.getStatus())) {

            throw new InvalidOrderException(
                    "Order cannot be updated from status: "
                            + order.getStatus()
            );
        }

        order.setStatus(normalizedStatus);

        return mapToResponse(
                orderRepository.save(order)
        );
    }

    @Transactional
    public void cancelOrder(Long id) {

        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with id: "
                                                + id
                                )
                        );

        if ("DELIVERED".equals(order.getStatus())) {

            throw new InvalidOrderException(
                    "Delivered order cannot be cancelled"
            );
        }

        if ("CANCELLED".equals(order.getStatus())) {

            throw new InvalidOrderException(
                    "Order is already cancelled"
            );
        }

        order.setStatus("CANCELLED");

        orderRepository.save(order);
    }

    private OrderResponseDTO mapToResponse(
            Order order) {

        List<OrderItemResponseDTO> items =
                order.getOrderItems()
                        .stream()
                        .map(item ->
                                OrderItemResponseDTO.builder()
                                        .id(item.getId())
                                        .foodItemId(
                                                item.getFoodItem()
                                                        .getId()
                                        )
                                        .foodName(
                                                item.getFoodItem()
                                                        .getName()
                                        )
                                        .quantity(
                                                item.getQuantity()
                                        )
                                        .price(item.getPrice())
                                        .subtotal(
                                                item.getPrice()
                                                        .multiply(
                                                                BigDecimal
                                                                        .valueOf(
                                                                                item.getQuantity()
                                                                        )
                                                        )
                                        )
                                        .build()
                        )
                        .toList();

        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .customerId(
                        order.getCustomer().getId()
                )
                .customerName(
                        order.getCustomer().getName()
                )
                .items(items)
                .build();
    }
}