package com.serjnn.ProductService.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.serjnn.ProductService.dtos.CacheableDiscountDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class DiscountCacheManager {
    private final ReactiveRedisOperations<String, Object> redisOperations;

    private static final String DISCOUNTS_HASH_KEY = "discounts_hash";


    public void addToCache(CacheableDiscountDto cacheableDiscountDto) {
        redisOperations
                .opsForHash()
                .put(DISCOUNTS_HASH_KEY, String.valueOf(cacheableDiscountDto.getProductId())
                        ,mapToJsonString(cacheableDiscountDto))
                .subscribe();
    }
    @SneakyThrows
    private CacheableDiscountDto readFromJsonString(Object value) {
        return new ObjectMapper().readValue(value.toString(), CacheableDiscountDto.class);
    }

    @SneakyThrows
    private String mapToJsonString(CacheableDiscountDto discountEntity) {
        return new ObjectMapper().writeValueAsString(discountEntity);
    }


    public Mono<CacheableDiscountDto> getDiscountByProductId(Long productId) {
        return redisOperations
                .opsForHash()
                .get(DISCOUNTS_HASH_KEY, productId.toString())
                .map(this::readFromJsonString);


    }
}
