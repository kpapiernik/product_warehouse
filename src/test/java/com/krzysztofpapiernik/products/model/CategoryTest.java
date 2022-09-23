package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetCategoryDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    void itShouldReturnGetCategoryDto() {
        //Given
        Category categoryToGet = Category
                .builder()
                .id(1L)
                .name("Test Category")
                .build();
        //When
        GetCategoryDto result = categoryToGet.toGetCategoryDto();
        //Then
        assertThat(result.id()).isEqualTo(categoryToGet.id);
        assertThat(result.name()).isEqualTo(categoryToGet.name);
    }
}