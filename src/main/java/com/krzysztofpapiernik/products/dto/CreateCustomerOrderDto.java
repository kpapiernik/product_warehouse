package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.CustomerOrder;
import com.krzysztofpapiernik.products.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record CreateCustomerOrderDto(Long customerId, Map<Long, Integer> products) {

    public CreateCustomerOrderDto {
        var errors = new HashMap<String, String>();

        if(customerId == null){
            errors.put("customerId", "is null");
        }

        if(products == null || products.isEmpty()){
            errors.put("Order items list", "is null or empty");
        } else {
            var counter = new Object() {
                int value = 0;
            };
            for(Map.Entry<Long, Integer> orderItem : products.entrySet()){
                if(orderItem.getKey() == null) {
                    errors.put("productId [%d]".formatted(counter.value), "is null");
                }

                Optional<Integer> quantityOptional = Optional.ofNullable(orderItem.getValue());

                if(quantityOptional.isEmpty()) {
                    errors.put("quantity [%d]".formatted(counter.value), "is null");
                }

                quantityOptional.ifPresent(quantityValue -> {
                    if(quantityValue <= 0){
                        errors.put("quantity [%d]".formatted(counter.value), "must be positive number");
                    }
                });
                counter.value++;
            }
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
    
    
    public CustomerOrder toCustomerOrderBeforeDbCheck(){
        return CustomerOrder
                .builder()
                .dateTime(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();
    }
}
