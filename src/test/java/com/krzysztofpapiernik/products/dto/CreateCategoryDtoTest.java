package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Category;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CreateCategoryDtoTest {

    @Test
    void itShouldCreateValidDto() {
        //Given
        String categoryName = "Test Category";
        //When
        CreateCategoryDto result = new CreateCategoryDto(categoryName);
        //Then
        assertThat(result.name()).isEqualTo(categoryName);
    }

    @Test
    void itShouldNotCreateDtoWhenNameIsNull() {
        //Given
        String categoryName = null;
        //When
        //Then
        assertThatThrownBy(() -> {CreateCategoryDto result = new CreateCategoryDto(categoryName);})
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenNameIsEmpty() {
        //Given
        String categoryName = "";
        //When
        //Then
        assertThatThrownBy(() -> {CreateCategoryDto result = new CreateCategoryDto(categoryName);})
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is empty String"));
    }

    @Test
    void itShouldNotCreateDtoWhenNameLengthIsAboveLimit() {
        //Given
        String categoryName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        int limit = CreateCategoryDto.nameLengthLimit;
        //When
        //Then
        assertThatThrownBy(() -> {CreateCategoryDto result = new CreateCategoryDto(categoryName);})
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is too long, max available length is %d characters".formatted(limit)));
    }

    @Test
    void itShouldReturnCategoryFromDto() {
        //Given
        CreateCategoryDto dto = new CreateCategoryDto("Test Category");
        Category expected = Category.builder().name("Test Category").build();
        //When
        Category result = dto.toCategory();
        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}