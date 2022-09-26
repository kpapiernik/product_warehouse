package com.krzysztofpapiernik.products.repository;

import com.krzysztofpapiernik.products.model.Product;
import com.krzysztofpapiernik.products.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductId(Long productId);

    @Modifying
    @Query("update Stock u set u.quantity = :quantity where u.product = :product")
    void updateStockQuantity(@Param(value = "product") Product product, @Param(value = "quantity") Integer quantity);
}
