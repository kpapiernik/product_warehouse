package com.krzysztofpapiernik.products.exception;

import java.util.Map;

public class ProductServiceException extends RuntimeException{

    private Map<String, String> errors;

    public ProductServiceException(String message) {
        super(message);
    }

    public ProductServiceException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
