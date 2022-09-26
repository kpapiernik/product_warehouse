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
        Map<Long, Integer> orderItems = Map.of(productId, quantity);
        //When
        CreateCustomerOrderDto result = new CreateCustomerOrderDto(customerId, orderItems);
        //Then
        assertThat(result.customerId()).isEqualTo(customerId);
        assertThat(result.products()).isEqualTo(orderItems);
    }

    @Test
    void itShouldNotCreateDtoWhenCustomerIdIsNull() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(null, Map.of(1L, 10)))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("customerId", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenProductIdIsNull() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(1L, Map.of(null, 10)))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("productId", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenQuantityIsNull() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(1L, Map.of(1L, null)))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("quantity", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenQuantityIsNotPositive() {
        assertThatThrownBy(() -> new CreateCustomerOrderDto(1L, Map.of(1L, -2)))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("quantity", "must be positive number"));
    }

    @Test
    void itShouldReturnCustomerOrderFromDto() {
        //Given
        CreateCustomerOrderDto dto = new CreateCustomerOrderDto(1L, Map.of(1L, 100));
        CustomerOrder expected = CustomerOrder
                .builder()
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