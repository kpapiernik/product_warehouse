package com.krzysztofpapiernik.products.exception;

import java.util.Map;

public class CustomerOrderServiceException extends RuntimeException{

    private Map<String, String> errors;

    public CustomerOrderServiceException(Map<String, String> errors){
        this.errors = errors;
    }

    public CustomerOrderServiceException(String message){
        super(message);
    }

    public Map<String, String> getErrors(){
        return errors;
    }
}
