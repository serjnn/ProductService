package com.serjnn.ProductService.repo;

import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import lombok.NonNull;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findProductsByCategory(Category category);

    @NonNull
    Flux<Product> findAllById(@NonNull Iterable<Long> ids);


}