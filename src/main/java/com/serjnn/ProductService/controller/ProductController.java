package com.serjnn.ProductService.controller;


import com.serjnn.ProductService.dtos.IdsRequest;
import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import com.serjnn.ProductService.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    Mono<List<Product>> all() {
        return productService.findAll();
    }

    @PostMapping("/by-ids")
    public Mono<List<Product>> getProductsByIds(@RequestBody IdsRequest idsRequest) {
        List<Long> ids = idsRequest.getIds();
        return productService.findProductsByIds(ids);
    }

    @PostMapping("/add")
    void add(@RequestBody Product product) {
        productService.save(product);

    }


    @GetMapping("/{cat}")
    public Mono<List<Product>> bucket(@PathVariable("cat") Category category) {
        return productService.findProductsByCategory(category);


    }


}
