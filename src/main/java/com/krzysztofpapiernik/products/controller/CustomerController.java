package com.krzysztofpapiernik.products.controller;

import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateCustomerDto;
import com.krzysztofpapiernik.products.dto.GetCustomerDto;
import com.krzysztofpapiernik.products.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
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

    @GetMapping("/{customerId}")
    public ResponseDataDto<GetCustomerDto> getCustomer(@PathVariable("customerId") Long id){
        return new ResponseDataDto<>(customerService.getCustomerById(id));
    }

    @PutMapping("/{customerId}")
    public ResponseDataDto<GetCustomerDto> editCustomer(@PathVariable("customerId") Long id, @RequestBody CreateCustomerDto createCustomerDto){

        return new ResponseDataDto<>(customerService.editCustomer(id, createCustomerDto));
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long id){
        customerService.deleteCustomer(id);
    }
}
