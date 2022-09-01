package com.krzysztofpapiernik.products.exception;

import java.util.Map;

public class StockServiceException extends RuntimeException{

    private Map<String, String> errors;

    public StockServiceException(String message) {
        super(message);
    }

    public StockServiceException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
