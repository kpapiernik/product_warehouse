package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.OrderStatus;
import com.krzysztofpapiernik.products.model.Product;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

public record GetCustomerOrderDto(Long id, ZonedDateTime createdAt, Map<Long, Integer> orderItems, BigDecimal totalPrice, OrderStatus status) {
}
