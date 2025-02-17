package com.jarapplication.kiranastore.feature_transactions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.jarapplication.kiranastore.feature_transactions.entity.BillEntity;

@Repository
public interface BillRepository extends MongoRepository<BillEntity, String> {
}
