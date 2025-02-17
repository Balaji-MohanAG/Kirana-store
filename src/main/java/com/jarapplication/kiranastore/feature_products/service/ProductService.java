package com.jarapplication.kiranastore.feature_products.service;

import com.jarapplication.kiranastore.feature_products.models.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {
    Page<Product> findByType(String category, int page, int size);

    Optional<Product> findByName(String name);

    Page<Product> getAllProducts(int page, int size);

    Product save(Product product);
}
