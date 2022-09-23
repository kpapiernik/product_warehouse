package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.CreateCustomerDto;
import com.krzysztofpapiernik.products.dto.GetCustomerDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void itShouldReturnChangedCustomerFromDto() {
        //Given
        CreateCustomerDto dto = new CreateCustomerDto("John", "Doe", "test@example.com", "01/06/1970");
        Customer customerBeforeChange = Customer
                .builder()
                .id(1L)
                .firstName("Test")
                .lastName("Test")
                .email("email@test.com")
                .dateOfBirth(LocalDate.parse("01/01/1900", DateTimeFormatter.ofPattern("d/MM/yyyy")))
                .build();
        //When
        Customer result = customerBeforeChange.withChangedAttributes(dto);
        //Then
        assertThat(result.id).isEqualTo(customerBeforeChange.id);
        assertThat(result.firstName).isEqualTo(dto.firstName());
        assertThat(result.lastName).isEqualTo(dto.lastName());
        assertThat(result.email).isEqualTo(dto.email());
        assertThat(result.dateOfBirth).isEqualTo(LocalDate.parse(dto.dateOfBirth(), DateTimeFormatter.ofPattern("d/MM/yyyy")));
    }

    @Test
    void itShouldReturnGetCustomerDto() {
        //Given
        Customer customerToGet = Customer
                .builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("test@example.com")
                .dateOfBirth(LocalDate.parse("01/01/1900", DateTimeFormatter.ofPattern("d/MM/yyyy")))
                .build();
        //When
        GetCustomerDto result = customerToGet.toGetCustomerDto();
        //Then
        assertThat(result.id()).isEqualTo(customerToGet.id);
        assertThat(result.firstName()).isEqualTo(customerToGet.firstName);
        assertThat(result.lastName()).isEqualTo(customerToGet.lastName);
        assertThat(result.email()).isEqualTo(customerToGet.email);
        assertThat(result.dateOfBirth()).isEqualTo(customerToGet.dateOfBirth);
    }
}