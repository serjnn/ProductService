package com.serjnn.ProductService.kafka.kafkaProducer;

import com.serjnn.ProductService.dtos.DiscountSubscriberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Mono<Void> sendDiscountNotification(String topicName, DiscountSubscriberDto discountSubscriberDto) {
        return Mono.fromFuture(() -> kafkaTemplate.send(topicName, discountSubscriberDto))
                .then();

    }


}
