package com.jarapplication.kiranastore.feature_transactions.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProductDTO {
    private String productId;
    private int count;
}
