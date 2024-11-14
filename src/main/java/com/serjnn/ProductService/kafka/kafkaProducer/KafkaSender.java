package com.serjnn.ProductService.kafka.kafkaProducer;

import com.serjnn.ProductService.dtos.DiscountNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, DiscountNotification> kafkaTemplate;

    public void sendDiscountNotification(String topicName, DiscountNotification discountNotification) {
        System.out.println("this " +  discountNotification);
        kafkaTemplate.send(topicName, discountNotification);


    }


}
