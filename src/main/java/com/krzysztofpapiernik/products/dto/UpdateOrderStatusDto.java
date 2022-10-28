package com.krzysztofpapiernik.products.dto;

import com.krzysztofpapiernik.products.model.OrderStatus;

public record UpdateOrderStatusDto(Long orderId, OrderStatus status) {
}
