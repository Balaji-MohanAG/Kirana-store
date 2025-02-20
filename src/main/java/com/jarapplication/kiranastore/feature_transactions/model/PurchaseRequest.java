package com.jarapplication.kiranastore.feature_transactions.model;

import com.jarapplication.kiranastore.feature_transactions.enums.CurrencyCode;
import java.util.List;
import lombok.Data;

@Data
public class PurchaseRequest {

    private String userId;
    private CurrencyCode currencyCode;
    private List<BillItem> billItems;
}
