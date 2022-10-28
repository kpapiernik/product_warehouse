package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateCustomerOrderDto;
import com.krzysztofpapiernik.products.dto.GetCustomerOrderDto;
import com.krzysztofpapiernik.products.exception.CustomerOrderServiceException;
import com.krzysztofpapiernik.products.model.CustomerOrder;
import com.krzysztofpapiernik.products.model.OrderStatus;
import com.krzysztofpapiernik.products.repository.CustomerOrderRepository;
import com.krzysztofpapiernik.products.repository.CustomerRepository;
import com.krzysztofpapiernik.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

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

        var productsFromDto = createCustomerOrderDto.products();

        var products = productsFromDto.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productRepository.findById(entry.getKey())
                        .orElseThrow(() -> new CustomerOrderServiceException(Map.of("product", " does not exist"))), Map.Entry::getValue));

        products.forEach((key, value) -> stockService.decreaseStockPositionQuantity(key.getId(), value));

        var customerOrder = createCustomerOrderDto
                .toCustomerOrderBeforeDbCheck()
                .withCustomer(customer)
                .withProducts(products);

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

    public GetCustomerOrderDto changeOrderStatus(Long id, OrderStatus status){
        var changedOrder = customerOrderRepository
                .findById(id)
                .map(order -> order.withChangedStatus(status))
                .orElseThrow(() -> new CustomerOrderServiceException(Map.of("order", "does not exist")));

        return customerOrderRepository
                .save(changedOrder)
                .toGetCustomerOrderDto();
    }
}
