package com.serjnn.ProductService.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DiscountNotification {
    private Long productId;
    private Long clientId;
    private Double discount;
}
