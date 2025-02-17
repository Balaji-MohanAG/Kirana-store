package com.jarapplication.kiranastore.feature_transactions.model;

import com.jarapplication.kiranastore.feature_transactions.enums.TransactionType;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseResponse {
    private String userName;
    private String billId;
    private double amount;
    private List<BillItem> billItems;
    private TransactionType transactionType;
}
