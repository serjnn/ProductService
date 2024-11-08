package com.serjnn.ProductService.kafka.kafkaConsumer;


import com.serjnn.ProductService.SubscribersNotifier;
import com.serjnn.ProductService.dtos.DiscountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SubscribersNotifier subscribersNotifier;

    @KafkaListener(topics = "newDiscountTopic", groupId = "first_group_id")
    public void newDiscountsListener(DiscountDto discountDto) {
        subscribersNotifier.notifySubscribers(discountDto);

    }
}
