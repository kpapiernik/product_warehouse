package com.krzysztofpapiernik.products.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

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
}
