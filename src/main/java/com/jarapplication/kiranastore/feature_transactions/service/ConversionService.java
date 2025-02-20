package com.jarapplication.kiranastore.feature_transactions.service;

import com.jarapplication.kiranastore.feature_transactions.enums.CurrencyCode;
import org.json.JSONException;

public interface ConversionService {
    double calculate(CurrencyCode currencyCode) throws JSONException;
}
