package com.krzysztofpapiernik.products.validation;

import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.stream.Collectors;

public interface Validator {

    static String validationErrorsToMessage(Map<String, String> errors){
        if(!errors.isEmpty()){
            return errors
                    .entrySet()
                    .stream()
                    .map(entry -> "%s: %s%n".formatted(entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("\n"));
        }
        return "";
    }
}
