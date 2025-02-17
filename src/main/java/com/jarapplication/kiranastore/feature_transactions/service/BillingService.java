package com.jarapplication.kiranastore.feature_transactions.service;

import com.jarapplication.kiranastore.feature_transactions.model.PurchaseRequest;
import com.jarapplication.kiranastore.feature_transactions.model.TransactionDto;

public interface BillingService {
    TransactionDto generateBills(PurchaseRequest purchaseRequest);
}
