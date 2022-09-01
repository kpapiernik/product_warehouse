package com.krzysztofpapiernik.products.dto;

import java.math.BigDecimal;

public record GetProductDto(Long id, String name, BigDecimal price, Long categoryId, Long producerId) {
}
