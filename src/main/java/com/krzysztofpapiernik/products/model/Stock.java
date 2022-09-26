package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetPositionFromStockDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter

@Entity
@Table(name = "stock")
public class Stock extends BaseEntity{
    Integer quantity;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    public Stock withProduct(Product product){
        return Stock
                .builder()
                .id(id)
                .quantity(quantity)
                .product(product)
                .build();
    }

    public Stock withQuantityAdded(Integer quantity){
        return Stock
                .builder()
                .id(id)
                .quantity(this.quantity + quantity)
                .product(product)
                .build();
    }

    public Stock withQuantitySubtracted(Integer quantity){
        return Stock
                .builder()
                .id(id)
                .quantity(this.quantity - quantity)
                .product(product)
                .build();
    }

    public boolean hasQuantityLessThanDemanded(Integer demand){
        return quantity < demand;
    }

    public GetPositionFromStockDto toGetPositionFromStockDto(){
        return new GetPositionFromStockDto(id, product.id, quantity);
    }
}
