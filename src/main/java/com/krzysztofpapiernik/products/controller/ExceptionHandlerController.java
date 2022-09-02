package com.krzysztofpapiernik.products.controller;

import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDataDto<?> validationExceptionHandler(ValidationException err){
        return new ResponseDataDto<>(err.getErrors());
    }
    @ExceptionHandler(CategoryServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDataDto<?> categoryServiceException(CategoryServiceException err){
        return new ResponseDataDto<>(err.getErrors());
    }
    @ExceptionHandler(CustomerOrderServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDataDto<?> customerOrderServiceException(CustomerOrderServiceException err){
        return new ResponseDataDto<>(err.getErrors());
    }

    @ExceptionHandler(CustomerServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDataDto<?> customerServiceException(CustomerServiceException err){
        return new ResponseDataDto<>(err.getErrors());
    }

    @ExceptionHandler(ProducerServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDataDto<?> producerServiceException(ProducerServiceException err){
        return new ResponseDataDto<>(err.getErrors());
    }

    @ExceptionHandler(ProductServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDataDto<?> productServiceException(ProductServiceException err){
        return new ResponseDataDto<>(err.getErrors());
    }

    @ExceptionHandler(StockServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDataDto<?> stockServiceException(StockServiceException err){
        return new ResponseDataDto<>(err.getErrors());
    }



}
