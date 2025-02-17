package com.jarapplication.kiranastore.feature_products.repository;



import com.jarapplication.kiranastore.feature_products.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    Page<ProductEntity> findByType(String type, Pageable pageable);

    Page<ProductEntity> findAll(Pageable pageable);

    Optional<ProductEntity> findProductEntityByName(String name);

}
