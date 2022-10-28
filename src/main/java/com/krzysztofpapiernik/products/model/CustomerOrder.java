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
    private ZonedDateTime createdAt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    protected Customer customer;

    @ElementCollection
    @CollectionTable(name = "order_items")
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    @Builder.Default
    protected Map<Product, Integer> products = new HashMap<>();

    @Enumerated(EnumType.STRING)
    protected OrderStatus status;

    public CustomerOrder withCustomer(Customer customer){
        return CustomerOrder
                .builder()
                .id(id)
                .createdAt(createdAt)
                .products(products)
                .customer(customer)
                .build();
    }

    public CustomerOrder withProducts(Map<Product, Integer> products){
        return CustomerOrder
                .builder()
                .id(id)
                .createdAt(createdAt)
                .products(products)
                .customer(customer)
                .build();
    }

    public CustomerOrder withChangedStatus(OrderStatus status){
        return CustomerOrder
                .builder()
                .id(id)
                .createdAt(createdAt)
                .products(products)
                .customer(customer)
                .status(status)
                .build();
    }

    public GetCustomerOrderDto toGetCustomerOrderDto(){
        var productsIdWithQuantity = products.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().id, Map.Entry::getValue));
        return new GetCustomerOrderDto(id, createdAt, productsIdWithQuantity, getTotalPrice(), status);
    }

    private BigDecimal getTotalPrice(){
        return products.entrySet()
                .stream()
                .map(entry -> entry.getKey().totalPrice(entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
