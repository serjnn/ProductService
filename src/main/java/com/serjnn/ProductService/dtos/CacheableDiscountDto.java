package com.serjnn.ProductService.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CacheableDiscountDto implements Serializable {
    private Long productId;
    private Double discount;
}