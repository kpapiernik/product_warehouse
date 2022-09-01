package com.krzysztofpapiernik.products.repository;

import com.krzysztofpapiernik.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameAndCategory_IdAndProducer_Id(String name, Long categoryId, Long producerId);
}
