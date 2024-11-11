package com.serjnn.ProductService.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiscountSubscriberDto {
    private Long productId;
    private Long clientId;
    private Double discount;
}
