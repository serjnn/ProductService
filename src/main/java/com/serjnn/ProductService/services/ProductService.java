package com.serjnn.ProductService.services;

import com.serjnn.ProductService.dtos.DiscountDto;
import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import com.serjnn.ProductService.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service

public class ProductService {
    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;


    public ProductService(ProductRepository productRepository, WebClient.Builder webClientBuilder
    ) {
        this.productRepository = productRepository;
        this.webClientBuilder = webClientBuilder;

    }

    public Flux<Product> findProductsByCategory(Category category) {
        Flux<Product> products = productRepository.findProductsByCategory(category);
        return countDiscount(products);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Flux<Product> findAll() {
        Flux<Product> products = productRepository.findAll();
        return countDiscount(products);
    }


    public Flux<Product> findProductsByIds(List<Long> ids) {
        Flux<Product> products = productRepository.findAllById(ids);
        return countDiscount(products);
    }


    private Flux<Product> countDiscount(Flux<Product> products) {
        return webClientBuilder.build()
                .get()
                .uri("lb://discount/api/v1")
                .retrieve()
                .bodyToFlux(DiscountDto.class)
                .collectMap(DiscountDto::getProductId, DiscountDto::getDiscount)
                .flatMapMany(discountsMap ->
                        products.map(product -> {
                            Double discount = discountsMap.get(product.getId());
                            if (discount != null) {
                                product.setPrice((int) (product.getPrice() * (1 - (discount / 100))));
                            }
                            return product;
                        })
                );
    }


}