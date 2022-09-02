package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.CustomerOrder;
import com.krzysztofpapiernik.products.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

public record CreateCustomerOrderDto(Long customerId, Long productId, Integer quantity) {

    public CreateCustomerOrderDto {
        var errors = new HashMap<String, String>();

        if(customerId == null){
            errors.put("customerId", "is null");
        }
        if (productId == null) {
            errors.put("productId", "is null");
        }
        if (quantity <= 0) {
            errors.put("quantity", "must be positive number");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
    
    
    public CustomerOrder toCustomerOrderBeforeDbCheck(){
        return CustomerOrder
                .builder()
                .quantity(quantity)
                .dateTime(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();
    }
}
