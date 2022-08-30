package com.krzysztofpapiernik.products.controller.dto;

public record ResponseDataDto<T>(T data, String errors) {
    public ResponseDataDto(T data){
        this(data, null);
    }

    public ResponseDataDto(String errors){
        this(null, errors);
    }
}
