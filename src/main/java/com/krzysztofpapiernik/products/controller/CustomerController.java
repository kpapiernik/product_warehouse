package com.krzysztofpapiernik.products.controller;

import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateCustomerDto;
import com.krzysztofpapiernik.products.dto.GetCustomerDto;
import com.krzysztofpapiernik.products.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDataDto<GetCustomerDto> addCustomer(@RequestBody CreateCustomerDto createCustomerDto){
        return new ResponseDataDto<>(customerService.addCustomer(createCustomerDto));
    }

    @GetMapping
    public ResponseDataDto<List<GetCustomerDto>> getAllCustomers(){
        return new ResponseDataDto<>(customerService.getAll());
    }

    //TODO Get specific customer

    //TODO Edit customer

    //TODO Delete customer
}
