package com.serjnn.ProductService.services;


import com.serjnn.ProductService.dtos.DiscountDto;
import com.serjnn.ProductService.dtos.DiscountSubscriberDto;
import com.serjnn.ProductService.kafka.kafkaProducer.KafkaSender;
import com.serjnn.ProductService.repo.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubscribersNotifier {
    private final SubscribersRepository subscribersRepository;
    private final KafkaSender kafkaSender;

    public Mono<Void> notifySubscribers(DiscountDto discountDto) {
        Long productId = discountDto.getProductId();
        return subscribersRepository.findClientIdsByProductId(productId)
                .map(clientId -> new DiscountSubscriberDto(discountDto.getProductId(), clientId, discountDto.getDiscount()))

                .flatMap(dto -> kafkaSender.sendDiscountNotification("discountNotifTopic", dto)) // Send each notification
                .then(); // Complete when all notifications are sent
    }

}
