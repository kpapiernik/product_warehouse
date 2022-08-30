package com.krzysztofpapiernik.products.repository;

import com.krzysztofpapiernik.products.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
