package com.krzysztofpapiernik.products.controller.dto;

import java.util.Map;

public record ResponseDataDto<T>(T data, Map<String, String> errors) {
    public ResponseDataDto(T data){
        this(data, null);
    }

    public ResponseDataDto(Map<String, String> errors){
        this(null, errors);
    }
}
