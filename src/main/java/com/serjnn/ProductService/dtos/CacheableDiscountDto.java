package com.serjnn.ProductService.dtos;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CacheableDiscountDto implements Serializable {
    private Long productId;
    private Double discount;

    public Long getProductId() {
        return productId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
