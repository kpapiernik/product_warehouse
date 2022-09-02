package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetCustomerOrderDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.ZonedDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customer_orders")
public class CustomerOrder extends BaseEntity{
    private ZonedDateTime dateTime;
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    public CustomerOrder withCustomer(Customer customer){
        return CustomerOrder
                .builder()
                .id(id)
                .dateTime(dateTime)
                .quantity(quantity)
                .product(product)
                .customer(customer)
                .build();
    }

    public CustomerOrder withProduct(Product product){
        return CustomerOrder
                .builder()
                .id(id)
                .dateTime(dateTime)
                .quantity(quantity)
                .product(product)
                .customer(customer)
                .build();
    }

    public GetCustomerOrderDto toGetCustomerOrderDto(){
        return new GetCustomerOrderDto(id, dateTime, product.totalPrice(quantity));
    }

}
