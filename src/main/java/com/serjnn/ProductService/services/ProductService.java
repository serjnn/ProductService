package com.serjnn.ProductService.services;

import com.serjnn.ProductService.dtos.CacheableDiscountDto;
import com.serjnn.ProductService.dtos.IdsRequest;
import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import com.serjnn.ProductService.models.Subscriber;
import com.serjnn.ProductService.redis.DiscountCacheManager;
import com.serjnn.ProductService.repo.ProductRepository;
import com.serjnn.ProductService.repo.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;
    private final SubscribersRepository subscribersRepository;
    private final DiscountCacheManager discountCacheManager;

    public Flux<Product> findProductsByCategory(Category category) {
        Flux<Product> products = productRepository.findProductsByCategory(category);
        return countDiscount(products);
    }

    public Flux<Product> findAll() {
        Flux<Product> products = productRepository.findAll();
        return countDiscount(products);
    }

    public Flux<Product> findProductsByIds(IdsRequest idsRequest) {
        List<Long> ids = idsRequest.getIds();
        Flux<Product> products = productRepository.findAllById(ids);
        return countDiscount(products);
    }

    private Flux<Product> countDiscount(Flux<Product> products) {
        return products.flatMap(product ->
                getDiscountByAnyCost(product.getId())
                        .map(cacheableDiscountDto -> {
                            BigDecimal discount = BigDecimal.valueOf(cacheableDiscountDto.getDiscount());

                            BigDecimal newPrice = product.getPrice()
                                    .multiply(
                                            BigDecimal.ONE.subtract(
                                                    discount.divide(BigDecimal.valueOf(100))))
                                    .setScale(2, RoundingMode.HALF_UP) ;
                            product.setPrice(newPrice);
                            return product;
                        })
                        .switchIfEmpty(Mono.just(product))
        );
    }


    public Mono<CacheableDiscountDto> getDiscountByAnyCost(Long id) {
        return discountCacheManager.getDiscountByProductId(id)
                .switchIfEmpty(
                        askDiscountService(id)
                                .flatMap(cacheableDiscountDto -> {
                                    discountCacheManager.addToCache(cacheableDiscountDto);
                                    return Mono.just(cacheableDiscountDto);
                                })
                                .switchIfEmpty(Mono.defer(() -> {
                                            discountCacheManager.addToCache(
                                                    new CacheableDiscountDto(id,0.0)
                                            );

                                            return Mono.empty();
                                        }
                                ))
                );
    }



    private Mono<CacheableDiscountDto> askDiscountService(Long productId) {
        return webClientBuilder.build()
                .get()
                .uri("lb://discount/api/v1/byProductId/" + productId)
                .retrieve()
                .bodyToMono(CacheableDiscountDto.class)
                .doOnError(e -> {
                    log.info("Error while fetching discount for product " + productId + ": " + e.getMessage());
                });
    }


    public Mono<Void> add(Product product) {
        return productRepository.save(product).then();

    }

    public Mono<Void> subscribe(Long clientId, Long productId) {
        return subscribersRepository.save(new Subscriber(productId, clientId)).then();
    }


}