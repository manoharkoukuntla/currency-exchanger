package com.nosto.exchanger.feign.callbacks;

import com.nosto.exchanger.feign.ExchangeRatesFeignClient;
import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class ExchangeRatesFeignClientFallback implements ExchangeRatesFeignClient {
    @Override
    public ExchangeRatesResponse getExchangeRates() {
        ExchangeRatesResponse response = new ExchangeRatesResponse();
        response.setRates(new HashMap<>());
        response.setDate(new Date());
        response.setBase("UNKNOWN");
        return response;
    }
}
