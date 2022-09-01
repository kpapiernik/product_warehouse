package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetProducerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "producers")
public class Producer extends BaseEntity{

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "producer")
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public GetProducerDto toGetProducerDto(){
        return new GetProducerDto(id, name);
    }
}
