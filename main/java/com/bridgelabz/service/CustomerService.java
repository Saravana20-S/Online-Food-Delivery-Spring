package com.bridgelabz.service;

import com.bridgelabz.dto.CustomerRequestDTO;
import com.bridgelabz.dto.CustomerResponseDTO;
import com.bridgelabz.exception.DuplicateCustomerException;
import com.bridgelabz.model.Customer;
import com.bridgelabz.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDTO createCustomer(
            CustomerRequestDTO request) {

        if (customerRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new DuplicateCustomerException(
                    "Customer already exists with email: "
                            + request.getEmail()
            );
        }

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CustomerResponseDTO mapToResponse(
            Customer customer) {

        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
    }
}