package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetCustomerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customer_orders")
public class CustomerOrder extends BaseEntity{
    @Column(name = "created_at")
    private ZonedDateTime dateTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    protected Customer customer;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "product_id")
//    private Product product;

    @ElementCollection
    @CollectionTable(name = "order_items")
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    @Builder.Default
    protected Map<Product, Integer> products = new HashMap<>();

    public CustomerOrder withCustomer(Customer customer){
        return CustomerOrder
                .builder()
                .id(id)
                .dateTime(dateTime)
                .products(products)
                .customer(customer)
                .build();
    }

    public CustomerOrder withProducts(Map<Product, Integer> products){
        return CustomerOrder
                .builder()
                .id(id)
                .dateTime(dateTime)
                .products(products)
                .customer(customer)
                .build();
    }

    public GetCustomerOrderDto toGetCustomerOrderDto(){
        var productsIdWithQuantity = products.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().id, Map.Entry::getValue));
        return new GetCustomerOrderDto(id, dateTime, productsIdWithQuantity, getTotalPrice());
    }

    private BigDecimal getTotalPrice(){
        return products.entrySet()
                .stream()
                .map(entry -> entry.getKey().totalPrice(entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
