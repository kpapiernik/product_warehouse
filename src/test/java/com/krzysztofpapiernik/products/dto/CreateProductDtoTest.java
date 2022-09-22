package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateProductDtoTest {

    @Test
    void itShouldCreateValidDto() {
        //Given
        String name = "Test Product";
        BigDecimal price = new BigDecimal("1300.99");
        Long categoryId = 1L;
        Long producerId = 1L;
        //When
        CreateProductDto result = new CreateProductDto(name, price, categoryId, producerId);
        //Then
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.price()).isEqualTo(price);
        assertThat(result.categoryId()).isEqualTo(categoryId);
        assertThat(result.producerId()).isEqualTo(producerId);
    }

    @Test
    void itShouldNotCreateDtoWhenNameIsNull() {
        assertThatThrownBy(() -> new CreateProductDto(null, new BigDecimal("1000"), 1L, 1L))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenNameIsEmpty() {
        assertThatThrownBy(() -> new CreateProductDto("", new BigDecimal("1000"), 1L, 1L))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is empty String"));
    }

    @Test
    void itShouldNotCreateDtoWhenNameIsTooLong() {
        String tooLongName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertThatThrownBy(() -> new CreateProductDto(tooLongName, new BigDecimal("1000"), 1L, 1L))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is too long, max available length is 50 characters"));
    }

    @Test
    void itShouldNotCreateDtoWhenPriceIsNull() {
        assertThatThrownBy(() -> new CreateProductDto("Test Product", null, 1L, 1L))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("price", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenPriceIsNegativeNumber() {
        assertThatThrownBy(() -> new CreateProductDto("Test Product", new BigDecimal("-1000"), 1L, 1L))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("price", "must be positive number"));
    }

    @Test
    void itShouldNotCreateDtoWhenCategoryIdIsNull() {
        assertThatThrownBy(() -> new CreateProductDto("Test Product", new BigDecimal("1000"), null, 1L))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("categoryId", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenProducerIdIsNull() {
        assertThatThrownBy(() -> new CreateProductDto("Test Product", new BigDecimal("1000"), 1L, null))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("producerId", "is null"));
    }

    @Test
    void itShouldReturnProductFromDto() {
        //Given
        CreateProductDto dto = new CreateProductDto("Test Product", new BigDecimal("2000.20"), 1L, 1L);
        Product expected = Product
                .builder()
                .name("Test Product")
                .price(new BigDecimal("2000.20"))
                .build();
        //When
        Product result = dto.toProduct();
        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "categoryId", "producerId")
                .isEqualTo(expected);
    }
}