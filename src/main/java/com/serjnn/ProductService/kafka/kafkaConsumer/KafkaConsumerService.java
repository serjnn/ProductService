package com.serjnn.ProductService.kafka.kafkaConsumer;


import com.serjnn.ProductService.dtos.CacheableDiscountDto;
import com.serjnn.ProductService.dtos.DiscountChangesDto;
import com.serjnn.ProductService.redis.DiscountCacheManager;
import com.serjnn.ProductService.services.SubscribersNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SubscribersNotifier subscribersNotifier;

    private final DiscountCacheManager discountCacheManager;

    @KafkaListener(topics = "discountChangesTopic", groupId = "first_product_group")
    public void biggerDiscountsListener(DiscountChangesDto discountChangesDto) {
        System.out.println(discountChangesDto);
        CacheableDiscountDto cacheableDiscountDto = new CacheableDiscountDto(discountChangesDto.getProductId()
        ,discountChangesDto.getNewDiscount());
        discountCacheManager.addToCache(cacheableDiscountDto);

        if (discountChangesDto.getPrevDiscount() == null) {
            return;
        }

        if (Double.compare(discountChangesDto.getPrevDiscount(),
                discountChangesDto.getNewDiscount()) < 0) {
            subscribersNotifier.notifySubscribers(discountChangesDto);
        }


    }


}
