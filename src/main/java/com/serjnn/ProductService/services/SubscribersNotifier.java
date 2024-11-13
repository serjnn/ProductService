package com.serjnn.ProductService.services;


import com.serjnn.ProductService.dtos.DiscountDto;
import com.serjnn.ProductService.dtos.DiscountNotification;
import com.serjnn.ProductService.kafka.kafkaProducer.KafkaSender;
import com.serjnn.ProductService.repo.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubscribersNotifier {
    private final SubscribersRepository subscribersRepository;
    private final KafkaSender kafkaSender;

    public void notifySubscribers(DiscountDto discountDto) {
        Long productId = discountDto.getProductId();


        subscribersRepository.findClientIdsByProductId(productId)
                .map(clientId -> new DiscountNotification(discountDto.getProductId(), clientId, discountDto.getDiscount()))
                .doOnNext(dto -> kafkaSender.sendDiscountNotification("discountNotifTopic", dto))
                .subscribe(); //We need either Mono<Void> either subscribe reactive chain

        //.map transforms the items in the stream, while .doOnNext performs side effects without changing items.
    }


}
