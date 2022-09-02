package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.CreateCustomerDto;
import com.krzysztofpapiernik.products.dto.GetCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    public Customer withChangedAttributes(CreateCustomerDto createCustomerDto){
        return Customer
                .builder()
                .id(id)
                .firstName(createCustomerDto.firstName())
                .lastName(createCustomerDto.lastName())
                .email(createCustomerDto.email())
                .dateOfBirth(LocalDate.parse(createCustomerDto.dateOfBirth(), DateTimeFormatter.ofPattern("d/MM/yyyy")))
                .build();
    }

    public GetCustomerDto toGetCustomerDto(){
        return new GetCustomerDto(id, firstName, lastName, email, dateOfBirth);
    }
}
