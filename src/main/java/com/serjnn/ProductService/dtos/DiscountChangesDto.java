package com.serjnn.ProductService.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiscountChangesDto implements Serializable {

    private Long productId;
    private Double newDiscount;
    private Double prevDiscount;

    public Long getProductId() {
        return productId;
    }

    public Double getNewDiscount() {
        return newDiscount;
    }

    public Double getPrevDiscount() {
        return prevDiscount;
    }
}
