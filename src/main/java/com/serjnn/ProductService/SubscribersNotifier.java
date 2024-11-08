package com.serjnn.ProductService;


import com.serjnn.ProductService.dtos.DiscountDto;
import com.serjnn.ProductService.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribersNotifier {
    private final ProductRepository productRepository;


    public void notifySubscribers(DiscountDto discountDto) {
        Long productId = discountDto.getProductId();
        //TODO finish that



    }
}
