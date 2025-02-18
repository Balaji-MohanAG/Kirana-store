package com.jarapplication.kiranastore.feature_transactions.service;

import com.jarapplication.kiranastore.cache.CacheService;
import com.jarapplication.kiranastore.feature_transactions.enums.CurrencyCode;
import com.jarapplication.kiranastore.feature_transactions.util.DateUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;


@Service
public class ConversionServiceImp implements ConversionService {

    private final CacheService cacheService;
    private final FxRatesApiServiceImp fxRatesApiService;

    @Autowired
    public ConversionServiceImp(FxRatesApiServiceImp fxRatesApiService,
                                CacheService cacheService) {
        this.fxRatesApiService = fxRatesApiService;
        this.cacheService = cacheService;
    }

    /**
     * calculates the conversion rate
     * @param currencyCode
     * @return
     * @throws JSONException
     */
    @Override
    public double calculate(CurrencyCode currencyCode) throws JSONException {
            if(currencyCode==null){
                throw new JSONException("currencyCode is null");
            }
            String result = cacheService.getValueFromRedis(currencyCode+"_INR");
            if (result != null) {
                return Double.parseDouble(result);
            }
            String response = (String) fxRatesApiService.fetchData();
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONObject rates = jsonResponse.getJSONObject("rates");

                double baseToINR = rates.getDouble("INR");
                double baseToCurrency = rates.optDouble(currencyCode.name(), -1);
                if (baseToCurrency == -1) {
                    throw new IllegalArgumentException("Invalid Currency Code: " + currencyCode.name());
                }
                double value = baseToCurrency / baseToINR;
                cacheService.setValueToRedis(currencyCode+"_INR",String.valueOf(value), DateUtil.getEndOfMinute());
                return baseToCurrency / baseToINR;
            } else {
                throw new RuntimeException("API call unsuccessful");
            }
        }
}
