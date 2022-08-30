package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.Customer;
import com.krzysztofpapiernik.products.validation.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public record CreateCustomerDto(String firstName, String lastName, String email, String dateOfBirth) {

    public void validate(){
        var errors = new HashMap<String, String>();

        if(firstName == null){
            errors.put("firstName", "is null");
        }else if (!firstName.matches("([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+")){
            errors.put("firstName", "contains not allowed characters or does not match required pattern");
        } else if (firstName.length() > 30) {
            errors.put("firstName", "is too long, max lenght is 30 characters");
        }

        //log.info(Validator.validationErrorsToMessage(errors));

        if(lastName == null){
            errors.put("lastName", "is null");
        }else if (!lastName.matches("([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+")){
            errors.put("lastName", "contains not allowed characters or does not match regex");
        } else if (lastName.length() > 30) {
            errors.put("lastName", "is too long, max lenght is 30 characters");
        }
        
        if(email == null){
            errors.put("email", "is null");
        } else if (!email.toLowerCase().matches("([a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            errors.put("email", "does not match required pattern");
        }
        
        if(dateOfBirth == null){
            errors.put("dateOfBirth", "is null");
        } else if (isNotValidDate(dateOfBirth)) {
            errors.put("dateOfBirth", "does not match required pattern (d/MM/yyyy)");
        } else if (LocalDate.now().isBefore(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("d/MM/yyyy")))) {
            errors.put("dateOfBirth", "must be date from the past");
        }

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }

    }

    private boolean isNotValidDate(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        try{
            var parsedDate = LocalDate.parse(date, dateTimeFormatter);
        }catch (DateTimeParseException e){
            return true;
        }
        return false;
    }

    public Customer toCustomer(){
        return Customer
                .builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .dateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("d/MM/yyyy")))
                .build();
    }
}
