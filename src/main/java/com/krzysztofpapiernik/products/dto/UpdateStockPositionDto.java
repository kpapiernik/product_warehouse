package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Stock;

import java.util.HashMap;

public record UpdateStockPositionDto(Long productId, Integer quantity) {

    public UpdateStockPositionDto {
        var errors = new HashMap<String, String>();

        if (productId == null) {
            errors.put("productId", "is null");
        }

        if(quantity == null){
            errors.put("quantity", "is null");
        } else if (quantity <= 0) {
            errors.put("quantity", "must be positive value");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    public Stock toStock(){
        return Stock
                .builder()
                .quantity(quantity)
                .build();
    }
}
