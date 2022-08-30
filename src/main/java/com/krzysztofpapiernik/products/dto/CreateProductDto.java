package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.Product;
import com.krzysztofpapiernik.products.validation.Validator;
import com.krzysztofpapiernik.products.validation.exception.ValidationException;

import java.math.BigDecimal;
import java.util.HashMap;

public record CreateProductDto(String name, BigDecimal price, Long categoryId, Long producerId) {

    public void validate(){
        var errors = new HashMap<String, String>();

        if(name == null){
            errors.put("name", "is null");
        } else if (name.isEmpty()) {
            errors.put("name", "is empty String");
        } else if (name.length() > 50){
            errors.put("name", "is too long, max available length is 50 characters");
        }

        if(price == null){
            errors.put("price", "is null");
        } else if (price.compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("price", "must be positive number");
        }
        if (categoryId == null) {
            errors.put("categoryId", "is null");
        }
        if (producerId == null){
            errors.put("producerId", "is null");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(Validator.validationErrorsToMessage(errors));
        }
    }

    public Product toProduct(){
        return Product
                .builder()
                .name(name)
                .price(price)
                .build();
    }

}
