package com.serjnn.ProductService.controller;


import com.serjnn.ProductService.dtos.IdsRequest;
import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import com.serjnn.ProductService.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    Flux<Product> all() {
        return productService.findAll();
    }

    @PostMapping("/by-ids")
    public Flux<Product> getProductsByIds(@RequestBody IdsRequest idsRequest) {
        List<Long> ids = idsRequest.getIds();
        return productService.findProductsByIds(ids);
    }

    @PostMapping("/add")
    void add(@RequestBody Product product) {
        productService.save(product);

    }


    @GetMapping("/{cat}")
    public Flux<Product> bucket(@PathVariable("cat") Category category) {
        return productService.findProductsByCategory(category);


    }


}
