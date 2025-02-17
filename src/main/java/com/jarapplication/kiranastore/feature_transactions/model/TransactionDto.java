package com.jarapplication.kiranastore.feature_transactions.model;

import com.jarapplication.kiranastore.feature_transactions.enums.CurrencyCode;
import com.jarapplication.kiranastore.feature_transactions.enums.TransactionType;
import lombok.Data;

import java.util.List;

@Data
public class TransactionDto {
    private String userId;
    private String billId;
    private CurrencyCode currencyCode;
    private double amount;
    private TransactionType transactionType;
    private double amountInINR;
    private List<BillItem> billItems;

}
