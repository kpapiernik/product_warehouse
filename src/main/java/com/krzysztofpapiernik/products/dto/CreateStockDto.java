package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.Stock;
import com.krzysztofpapiernik.products.validation.Validator;
import com.krzysztofpapiernik.products.validation.exception.ValidationException;

import java.util.HashMap;

public record CreateStockDto(Long productId, Integer quantity) {

    public void validate() {
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
            throw new ValidationException(Validator.validationErrorsToMessage(errors));
        }
    }

    public Stock toStock(){
        return Stock
                .builder()
                .quantity(quantity)
                .build();
    }
}
