package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Stock;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateStockPositionDtoTest {

    @Test
    void itShouldCreateValidDto() {
        //Given
        Long productId = 1L;
        Integer quantity = 100;
        //When
        CreateStockPositionDto result = new CreateStockPositionDto(productId, quantity);
        //Then
        assertThat(result.productId()).isEqualTo(productId);
        assertThat(result.quantity()).isEqualTo(quantity);
    }

    @Test
    void itShouldNotCreateDtoWhenProductIdIsNull() {
        assertThatThrownBy(() -> new CreateStockPositionDto(null, 10))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("productId", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenQuantityIsNull() {
        assertThatThrownBy(() -> new CreateStockPositionDto(1L, null))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("quantity", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenQuantityIsNegativeNumber() {
        assertThatThrownBy(() -> new CreateStockPositionDto(1L, -2))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("quantity", "must be positive value"));
    }

    @Test
    void itShouldNotCreateDtoWhenQuantityIsZero() {
        assertThatThrownBy(() -> new CreateStockPositionDto(1L, 0))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("quantity", "must be positive value"));
    }

    @Test
    void itShouldReturnStockPositionFromDto() {
        //Given
        CreateStockPositionDto dto = new CreateStockPositionDto(1L, 100);
        Stock expected = Stock
                .builder()
                .quantity(100)
                .build();
        //When
        Stock result = dto.toStock();
        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "productId")
                .isEqualTo(expected);
    }
}