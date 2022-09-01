package com.krzysztofpapiernik.products.exception;

import java.util.Map;

public class ProducerServiceException extends RuntimeException{

    private Map<String, String> errors;

    public ProducerServiceException(String message) {
        super(message);
    }

    public ProducerServiceException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
