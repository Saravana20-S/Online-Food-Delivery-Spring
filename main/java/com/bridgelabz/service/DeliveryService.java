package com.bridgelabz.service;

import com.bridgelabz.dto.DeliveryRequestDTO;
import com.bridgelabz.dto.DeliveryResponseDTO;
import com.bridgelabz.exception.DeliveryNotFoundException;
import com.bridgelabz.exception.InvalidOrderException;
import com.bridgelabz.exception.OrderNotFoundException;
import com.bridgelabz.model.Delivery;
import com.bridgelabz.model.Order;
import com.bridgelabz.repository.DeliveryRepository;
import com.bridgelabz.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public DeliveryResponseDTO createDelivery(
            Long orderId,
            DeliveryRequestDTO request) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with id: "
                                                + orderId
                                )
                        );

        if (deliveryRepository
                .findByOrderId(orderId)
                .isPresent()) {

            throw new InvalidOrderException(
                    "Delivery already exists for order id: "
                            + orderId
            );
        }

        if ("CANCELLED".equals(order.getStatus())) {

            throw new InvalidOrderException(
                    "Cannot create delivery for a cancelled order"
            );
        }

        Delivery delivery =
                Delivery.builder()
                        .deliveryAddress(
                                request.getDeliveryAddress()
                        )
                        .status("ASSIGNED")
                        .order(order)
                        .build();

        Delivery savedDelivery =
                deliveryRepository.save(delivery);

        return mapToResponse(savedDelivery);
    }

    @Transactional(readOnly = true)
    public DeliveryResponseDTO getDelivery(
            Long orderId) {

        Delivery delivery =
                deliveryRepository
                        .findByOrderId(orderId)
                        .orElseThrow(() ->
                                new DeliveryNotFoundException(
                                        "Delivery not found for order id: "
                                                + orderId
                                )
                        );

        return mapToResponse(delivery);
    }

    private DeliveryResponseDTO mapToResponse(
            Delivery delivery) {

        return DeliveryResponseDTO.builder()
                .id(delivery.getId())
                .deliveryAddress(
                        delivery.getDeliveryAddress()
                )
                .status(delivery.getStatus())
                .orderId(
                        delivery.getOrder().getId()
                )
                .build();
    }
}