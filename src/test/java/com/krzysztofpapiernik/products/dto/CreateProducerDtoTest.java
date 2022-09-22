package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Producer;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateProducerDtoTest {

    @Test
    void itShouldCreateValidDto() {
        //Given
        String name = "Test Producer";
        //When
        CreateProducerDto result = new CreateProducerDto(name);
        //Then
        assertThat(result.name()).isEqualTo(name);
    }

    @Test
    void itShouldNotCreateDtoWhenNameIsNull() {
        assertThatThrownBy(() -> new CreateProducerDto(null))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is null"));
    }

    @Test
    void itShouldNotCreateDtoWhenNameIsEmpty() {
        assertThatThrownBy(() -> new CreateProducerDto(""))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is empty String"));
    }

    @Test
    void itShouldNotCreateDtoWhenNameHasTooBigLength() {
        String tooLongName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertThatThrownBy(() -> new CreateProducerDto(tooLongName))
                .isInstanceOf(ValidationException.class)
                .hasFieldOrPropertyWithValue("errors", Map.of("name", "is too long, max available length is 50 characters"));
    }

    @Test
    void itShouldReturnProducerFromDto() {
        //Given
        CreateProducerDto dto = new CreateProducerDto("Test Producer");
        Producer expected = Producer
                .builder()
                .name("Test Producer")
                .build();
        //When
        Producer result = dto.toProducer();
        //Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}