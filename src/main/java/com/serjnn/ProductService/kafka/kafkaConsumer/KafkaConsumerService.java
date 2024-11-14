package com.serjnn.ProductService.kafka.kafkaConsumer;


import com.serjnn.ProductService.services.SubscribersNotifier;
import com.serjnn.ProductService.dtos.DiscountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SubscribersNotifier subscribersNotifier;

    @KafkaListener(topics = "newDiscountTopic", groupId = "first_product_group")
    public void newDiscountsListener(DiscountDto discountDto) {
        System.out.println(discountDto);
        subscribersNotifier.notifySubscribers(discountDto);

    }
}
