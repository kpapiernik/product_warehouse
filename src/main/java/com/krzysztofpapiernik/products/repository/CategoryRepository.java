package com.krzysztofpapiernik.products.repository;

import com.krzysztofpapiernik.products.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
