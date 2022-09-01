package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Stock;

import java.util.Map;

public record UpdateStockPositionDto(Long productId, Integer quantity) {

    public UpdateStockPositionDto {
        if(quantity <=0 ){
            throw new ValidationException(Map.of("quantity", "must be positive number"));
        }
    }

    public Stock toStock(){
        return Stock
                .builder()
                .quantity(quantity)
                .build();
    }
}
