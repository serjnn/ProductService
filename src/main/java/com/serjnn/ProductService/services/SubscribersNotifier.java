package com.serjnn.ProductService.services;


import com.serjnn.ProductService.dtos.DiscountNotification;
import com.serjnn.ProductService.dtos.DiscountChangesDto;
import com.serjnn.ProductService.kafka.kafkaProducer.KafkaSender;
import com.serjnn.ProductService.repo.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscribersNotifier {
    private final SubscribersRepository subscribersRepository;
    private final KafkaSender kafkaSender;

    public void notifySubscribers(DiscountChangesDto discountChangesDto) {
        log.info("notifying subscribers " + discountChangesDto);
        Long productId = discountChangesDto.getProductId();
        subscribersRepository.findClientIdsByProductId(productId)
                .map(clientId ->
                        new DiscountNotification(discountChangesDto.getProductId(),
                                clientId,
                                discountChangesDto.getNewDiscount()))
                .doOnNext(dto -> kafkaSender.sendDiscountNotification("discountNotifTopic", dto))
                .subscribe(); //We need either Mono<Void> either subscribe reactive chain

        //.map transforms the items in the stream, while .doOnNext performs side effects without changing items.
    }


}
