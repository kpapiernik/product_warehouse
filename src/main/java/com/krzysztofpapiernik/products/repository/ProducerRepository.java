package com.krzysztofpapiernik.products.repository;

import com.krzysztofpapiernik.products.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(String name);

}
