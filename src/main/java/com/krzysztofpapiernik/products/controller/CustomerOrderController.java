package com.krzysztofpapiernik.products.controller;

import com.krzysztofpapiernik.products.controller.dto.ResponseDataDto;
import com.krzysztofpapiernik.products.dto.CreateCustomerOrderDto;
import com.krzysztofpapiernik.products.dto.GetCustomerOrderDto;
import com.krzysztofpapiernik.products.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDataDto<GetCustomerOrderDto> addOrder(@RequestBody CreateCustomerOrderDto createCustomerOrderDto){
        return new ResponseDataDto<>(customerOrderService.createOrder(createCustomerOrderDto));
    }

    @GetMapping("/{orderId}")
    public ResponseDataDto<GetCustomerOrderDto> getOrder(@PathVariable("orderId") Long id){
        return new ResponseDataDto<>(customerOrderService.getOrder(id));
    }
}
