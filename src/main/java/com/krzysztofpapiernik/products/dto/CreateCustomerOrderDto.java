package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.CustomerOrder;
import com.krzysztofpapiernik.products.validation.Validator;
import com.krzysztofpapiernik.products.validation.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

public record CreateCustomerOrderDto(Long customerId, Long productId, Integer quantity, BigDecimal discount) {
    
    public void validate(){
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
        if (discount.compareTo(BigDecimal.ZERO) <= 0 || discount.compareTo(BigDecimal.ONE) >= 0){
            errors.put("discount", "must be decimal value in range from 0.0 to 0.99");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(Validator.validationErrorsToMessage(errors));
        }
    }
    
    
    public CustomerOrder toCustomerOrderBeforeDbCheck(){
        return CustomerOrder
                .builder()
                .quantity(quantity)
                .discount(discount)
                .dateTime(LocalDateTime.now().atZone(ZoneId.systemDefault()))
                .build();
    }
}
