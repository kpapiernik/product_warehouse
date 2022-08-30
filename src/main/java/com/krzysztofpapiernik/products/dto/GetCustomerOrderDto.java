package com.krzysztofpapiernik.products.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record GetCustomerOrderDto(Long id, ZonedDateTime dateTime, BigDecimal totalPrice) {
}
