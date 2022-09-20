package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.Category;
import com.krzysztofpapiernik.products.exception.ValidationException;

import java.util.HashMap;

public record CreateCategoryDto(String name) {
    static final int nameLengthLimit = 50;
    public CreateCategoryDto{
        var errors = new HashMap<String, String>();

        if(name == null){
            errors.put("name", "is null");
        } else if (name.isEmpty()) {
            errors.put("name", "is empty String");
        } else if (name.length() > nameLengthLimit){
            errors.put("name", "is too long, max available length is %d characters".formatted(nameLengthLimit));
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    public Category toCategory(){
        return Category
                .builder()
                .name(name)
                .build();
    }
}
