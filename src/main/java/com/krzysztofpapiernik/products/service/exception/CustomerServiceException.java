package com.krzysztofpapiernik.products.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomerServiceException extends RuntimeException{
    public CustomerServiceException(String message) {
        super(message);
    }
}
