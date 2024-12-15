package com.serjnn.ProductService.kafka.kafkaConsumer;


import com.serjnn.ProductService.dtos.DiscountChangesDto;
import com.serjnn.ProductService.services.IncomingDiscountsProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final IncomingDiscountsProcessor incomingDiscountsProcessor;

    @KafkaListener(topics = "discountChangesTopic", groupId = "first_product_group")
    public void discountListener(DiscountChangesDto discountChangesDto) {
        incomingDiscountsProcessor.process(discountChangesDto);


    }


}
