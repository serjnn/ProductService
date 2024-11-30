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
public class DiscountChangesDto implements Serializable {

    private Long productId;
    private Double newDiscount;
    private Double prevDiscount;


}
