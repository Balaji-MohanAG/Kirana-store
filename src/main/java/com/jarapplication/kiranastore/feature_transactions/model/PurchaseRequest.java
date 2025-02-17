package com.jarapplication.kiranastore.feature_transactions.model;


import com.jarapplication.kiranastore.feature_transactions.enums.CurrencyCode;
import com.jarapplication.kiranastore.feature_transactions.enums.TransactionType;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequest {

    private String userId;
    private CurrencyCode currencyCode;
    private List<BillItem> billItems;

}

