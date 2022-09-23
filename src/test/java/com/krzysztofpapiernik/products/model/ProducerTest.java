package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetProducerDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProducerTest {

    @Test
    void itShouldReturnGetProducerDto() {
        //Given
        Producer producerToGet = Producer
                .builder()
                .id(1L)
                .name("Test Producer")
                .build();
        //When
        GetProducerDto result = producerToGet.toGetProducerDto();
        //Then
        assertThat(result.id()).isEqualTo(producerToGet.id);
        assertThat(result.name()).isEqualTo(producerToGet.name);
    }
}