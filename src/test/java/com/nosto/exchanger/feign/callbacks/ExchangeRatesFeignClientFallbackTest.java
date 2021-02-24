package com.nosto.exchanger.feign.callbacks;


import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExchangeRatesFeignClientFallbackTest {

    private ExchangeRatesFeignClientFallback callback = new ExchangeRatesFeignClientFallback();

    @Test
    public void should_return_empty_exchange_rates(){
        ExchangeRatesResponse exchangeRatesResponse = callback.getExchangeRates();

        assertEquals(exchangeRatesResponse.getRates(), new HashMap<>());
    }
}
