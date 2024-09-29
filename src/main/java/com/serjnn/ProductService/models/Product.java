package com.serjnn.ProductService.models;

import com.serjnn.ProductService.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@NoArgsConstructor

@Table(name = "product")

public class Product {
    @Id
    private long id;

    private String name;


    private String description;


    private int price;


    private Category category;

    public Product(String name, String description, int price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}