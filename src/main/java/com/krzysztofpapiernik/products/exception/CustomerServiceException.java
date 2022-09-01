package com.krzysztofpapiernik.products.exception;


import java.util.Map;

public class CustomerServiceException extends RuntimeException{

    private Map<String, String> errors;

    public CustomerServiceException(Map<String, String> errors){
        this.errors = errors;
    }

    public CustomerServiceException(String message) {
        super(message);
    }

    public Map<String, String> getErrors(){
        return errors;
    }
}
