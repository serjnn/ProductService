package com.serjnn.ProductService.services;

import com.serjnn.ProductService.dtos.DiscountDto;
import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import com.serjnn.ProductService.repo.ProductRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class ProductService {
    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;


    public ProductService(ProductRepository productRepository, WebClient.Builder webClientBuilder
    ) {
        this.productRepository = productRepository;
        this.webClientBuilder = webClientBuilder;

    }

    public Mono<List<Product>> findProductsByCategory(Category category) {
        List<Product> products = productRepository.findProductsByCategory(category);
        return countDiscount(products);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Mono<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();
        return countDiscount(products);
    }


    public Mono<List<Product>> findProductsByIds(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        return countDiscount(products);
    }


    private Mono<List<Product>> countDiscount(List<Product> products) {
        return webClientBuilder.build()
                .get()
                .uri("lb://discount/api/v1")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DiscountDto>>() {
                })
                .map(this::convertToMap)

                .flatMap(discountsMap -> {

                    products.forEach(product -> {
                        Double discount = discountsMap.get(product.getId());
                        if (discount != null) {
                            product.setPrice((int) (product.getPrice() * (1 - (discount / 100))));
                        }
                    });
                    return Mono.just(products);
                });
    }

    private Map<Long, Double> convertToMap(List<DiscountDto> discountList) {
        return discountList.stream()
                .collect(Collectors.toMap(DiscountDto::getProductId, DiscountDto::getDiscount));
    }


}