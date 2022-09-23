package com.krzysztofpapiernik.products.model;

import com.krzysztofpapiernik.products.dto.GetCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Column(unique = true)
    protected String name;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public GetCategoryDto toGetCategoryDto(){
        return new GetCategoryDto(id, name);
    }

}
