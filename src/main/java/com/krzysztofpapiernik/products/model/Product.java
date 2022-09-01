package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetProductDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    private String name;
    private BigDecimal price;

    //TODO Spróbować przerobić na relację many to many
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    Producer producer;

    @OneToOne(mappedBy = "product")
    private Stock stock;

    public Product withCategory(Category category){
        return Product
                .builder()
                .id(id)
                .name(name)
                .price(price)
                .category(category)
                .producer(producer)
                .build();
    }

    public Product withProducer(Producer producer){
        return Product
                .builder()
                .id(id)
                .name(name)
                .price(price)
                .category(category)
                .producer(producer)
                .build();
    }

    public BigDecimal totalPrice(Integer quantity){
        return price
                .multiply(BigDecimal.valueOf(quantity));
    }

    public GetProductDto toGetProductDto(){
        return new GetProductDto(id, name, price, category.id, producer.id);
    }
}
