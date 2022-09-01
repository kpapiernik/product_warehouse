package com.krzysztofpapiernik.products.exception;

import java.util.Map;

public class CategoryServiceException extends RuntimeException{

    private Map<String, String> errors;

    public CategoryServiceException(Map<String, String> errors){
        this.errors = errors;
    }

    public CategoryServiceException(String message) {
        super(message);
    }

    public Map<String, String> getErrors(){
        return this.errors;
    }
}
