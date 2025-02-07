package com.serjnn.ProductService.controller;


import com.serjnn.ProductService.dtos.IdsRequest;
import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import com.serjnn.ProductService.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    Flux<Product> all() {
        return productService.findAll();
    }

    @PostMapping("/all/by-ids")
    Flux<Product> getProductsByIds(@RequestBody IdsRequest idsRequest) {
        return productService.findProductsByIds(idsRequest);
    }

    @GetMapping("/by-cat/{cat}")
    Flux<Product> bucket(@PathVariable("cat") Category category) {
        return productService.findProductsByCategory(category);
    }

    @PostMapping("/add")
    Mono<Void> addProduct(@RequestBody Product product) {
        return productService.add(product);

    }

    @GetMapping("/subscribe/{clientId}/{productId}")
    Mono<Void> subscribe(@PathVariable("clientId") Long clientId, @PathVariable("productId") Long productId) {
        return productService.subscribe(clientId,productId);
    }






}
