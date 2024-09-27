package com.serjnn.ProductService.repo;

import com.serjnn.ProductService.enums.Category;
import com.serjnn.ProductService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategory(Category category);

    List<Product> findAllById(Iterable<Long> ids);

}