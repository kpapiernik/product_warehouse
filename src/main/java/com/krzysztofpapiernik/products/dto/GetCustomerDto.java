package com.krzysztofpapiernik.products.dto;

import java.time.LocalDate;

public record GetCustomerDto(Long id, String firstName, String lastName, String email, LocalDate dateOfBirth) {
}
