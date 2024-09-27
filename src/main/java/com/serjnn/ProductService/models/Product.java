package com.serjnn.ProductService.models;

import com.serjnn.ProductService.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    public Product(String name, String description, int price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}