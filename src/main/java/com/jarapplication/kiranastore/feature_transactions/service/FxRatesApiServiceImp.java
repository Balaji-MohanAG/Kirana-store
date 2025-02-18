package com.jarapplication.kiranastore.feature_transactions.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FxRatesApiServiceImp implements FxRatesApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Fetches the exchange rate
     * @return
     */
    @Override
    public Object fetchData() {
        String url = "https://api.fxratesapi.com/latest";
        return restTemplate.getForObject(url, String.class);

    }



}

