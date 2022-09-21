package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.CustomerOrder;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateCustomerOrderDtoTest {

    @Test
    void itShouldCreateValidDto() {
        //Given
        Long customerId = 1L;
        Long productId = 1L;
        Integer quantity = 10;
        //When
        CreateCustomerOrderDto result = new CreateCustomerOrderDto(customerId, productId, quantity);
        //Then
        assertThat(result.customerId()).isEqualTo(customerId);
        assertThat(result.productId()).isEqualTo(productId);
        assertThat(result.quantity()).isEqualTo(quantity);
    }

    @Test
    void itShouldNotCreateDtoWhenCustomerIdIsNull() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(null, 1L, 10))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("customerId", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenProductIdIsNull() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(1L, null, 10))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("productId", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenQuantityIsNull() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(1L, 1L, null))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("quantity", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenQuantityIsNotPositive() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(1L, 1L, -2))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("quantity", "must be positive number"));
    }

    @Test
    void itShouldReturnCustomerOrderFromDto() {
        //Given
        CreateCustomerOrderDto dto = new CreateCustomerOrderDto(1L, 1L, 10);
        CustomerOrder expected = CustomerOrder
                .builder()
                .quantity(10)
                .build();
        //When
        CustomerOrder result = dto.toCustomerOrderBeforeDbCheck();
        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "dateTime")
                .isEqualTo(expected);
    }
}