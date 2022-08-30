package com.krzysztofpapiernik.products.repository;

import com.krzysztofpapiernik.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
