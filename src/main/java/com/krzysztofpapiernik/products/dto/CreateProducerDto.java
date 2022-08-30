package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.validation.Validator;
import com.krzysztofpapiernik.products.validation.exception.ValidationException;

import java.util.HashMap;

public record CreateProducerDto(String name) {

    public void validate(){
        var errors = new HashMap<String, String>();

        if(name == null){
            errors.put("name", "is null");
        } else if (name.isEmpty()) {
            errors.put("name", "is empty String");
        } else if (name.length() > 50){
            errors.put("name", "is too long, max available length is 50 characters");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(Validator.validationErrorsToMessage(errors));
        }
    }
}
