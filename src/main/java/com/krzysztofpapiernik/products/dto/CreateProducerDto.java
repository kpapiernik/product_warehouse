package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.exception.ValidationException;
import com.krzysztofpapiernik.products.model.Producer;

import java.util.HashMap;

public record CreateProducerDto(String name) {

    public CreateProducerDto {
        var errors = new HashMap<String, String>();

        if(name == null){
            errors.put("name", "is null");
        } else if (name.isEmpty()) {
            errors.put("name", "is empty String");
        } else if (name.length() > 50){
            errors.put("name", "is too long, max available length is 50 characters");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    public Producer toProducer(){
        return Producer
                .builder()
                .name(name)
                .build();
    }
}
