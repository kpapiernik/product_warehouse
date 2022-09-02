package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateCustomerOrderDto;
import com.krzysztofpapiernik.products.dto.GetCustomerOrderDto;
import com.krzysztofpapiernik.products.exception.CustomerOrderServiceException;
import com.krzysztofpapiernik.products.model.CustomerOrder;
import com.krzysztofpapiernik.products.repository.CustomerOrderRepository;
import com.krzysztofpapiernik.products.repository.CustomerRepository;
import com.krzysztofpapiernik.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerOrderService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerOrderRepository customerOrderRepository;

    private final StockService stockService;

    public GetCustomerOrderDto createOrder(CreateCustomerOrderDto createCustomerOrderDto){
        var customerId = createCustomerOrderDto.customerId();
        var customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerOrderServiceException(Map.of("customer", "does not exist")));

        var productId = createCustomerOrderDto.productId();
        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new CustomerOrderServiceException(Map.of("product", " does not exist")));

        var customerOrder = createCustomerOrderDto
                .toCustomerOrderBeforeDbCheck()
                .withCustomer(customer)
                .withProduct(product);

        var quantity = createCustomerOrderDto.quantity();

        stockService.decreaseStockPositionQuantity(productId, quantity);

        return customerOrderRepository
                .save(customerOrder)
                .toGetCustomerOrderDto();
    }

    public GetCustomerOrderDto getOrder(Long id){

        return customerOrderRepository
                .findById(id)
                .map(CustomerOrder::toGetCustomerOrderDto)
                .orElseThrow(() -> new CustomerOrderServiceException(Map.of("order", "does not exist")));
    }
}
