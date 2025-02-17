package com.jarapplication.kiranastore.feature_transactions.helper;



import com.jarapplication.kiranastore.feature_products.service.ProductServiceImp;
import com.jarapplication.kiranastore.feature_transactions.model.BillItem;
import com.jarapplication.kiranastore.feature_transactions.model.PurchaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateBill {

    private final ProductServiceImp productService;

    @Autowired
    public CalculateBill( ProductServiceImp productService) {
        this.productService = productService;
    }

    /**
     * Calculate the total bill amount based on the product list.
     * @param purchaseRequest
     * @return
     */
    public double calculateBill(PurchaseRequest purchaseRequest) {
        double totalAmount = 0.0;
        List<BillItem> billItems = purchaseRequest.getBillItems();
        totalAmount += billItems.stream()
                .map(item -> productService.findByName(item.getItemName())
                        .map(product -> product.getPrice() * item.getQuantity())
                        .orElseThrow(() -> new RuntimeException("Product not found: " + item.getItemName())))
                .mapToDouble(Double::doubleValue)
                .sum();

        return totalAmount;
    }
}
