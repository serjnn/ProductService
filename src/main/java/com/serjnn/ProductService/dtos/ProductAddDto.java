package com.serjnn.ProductService.dtos;

import com.serjnn.ProductService.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link com.serjnn.ProductService.models.Product}
 */
@AllArgsConstructor
@Getter
public class ProductAddDto {
    private final String name;
    private final String description;
    private final int price;
    private final Category category;
}