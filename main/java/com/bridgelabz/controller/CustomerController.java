package com.bridgelabz.controller;

import com.bridgelabz.dto.CustomerRequestDTO;
import com.bridgelabz.dto.CustomerResponseDTO;
import com.bridgelabz.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        customerService.createCustomer(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>>
    getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }
}