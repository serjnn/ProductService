package com.serjnn.ProductService.models;

import com.serjnn.ProductService.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "product")
@ToString
public class Product {
    @Id
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Category category;

}