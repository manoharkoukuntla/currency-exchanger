package com.nosto.exchanger.services;


import com.nosto.exchanger.config.TestRedisConnection;
import com.nosto.exchanger.feign.ExchangeRatesFeignClient;
import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import com.nosto.exchanger.payloads.request.CurrencyExchangeRequest;
import com.nosto.exchanger.payloads.response.CurrencyExchangeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {TestRedisConnection.class})
public class CurrencyExchangeServiceTest {

    @Mock
    private ExchangeRatesFeignClient exchangeRatesFeignClient;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    private String base = "EUR";
    private String source = base;
    private String target = "INR";
    private Float sourceValue = 200F;
    private Float targetRate = 0.2F;
    private Float targetValue = sourceValue * targetRate;
    private ExchangeRatesResponse exchangeRates = new ExchangeRatesResponse();
    private CurrencyExchangeRequest request = new CurrencyExchangeRequest();
    private CurrencyExchangeResponse response = new CurrencyExchangeResponse();

    @BeforeEach
    public void setUpRequestAndResponse() {
        request.setSource(source);
        request.setTarget(target);
        request.setValue(sourceValue);
        response.setSource(source);
        response.setTarget(target);
        response.setSourceValue(sourceValue);
        response.setTargetValue(targetValue);

        HashMap<String, Float> rates = new HashMap<>();
        rates.put(target, targetRate);
        rates.put(base, 1F);
        exchangeRates.setBase(source);
        exchangeRates.setRates(rates);
    }

    @Test
    public void should_return_exchange_rates_when_getExchangeRates_is_called() {
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        ExchangeRatesResponse exchangeRatesResponse = currencyExchangeService.getExchangeRates();

        assertEquals(exchangeRates, exchangeRatesResponse);
    }

    @Test
    public void should_return_exchange_rates_value_when_getExchangeValue_is_called_with_request() {
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        CurrencyExchangeResponse exchangeRateResponse = currencyExchangeService.getExchangeValue(request, exchangeRates);

        assertEquals(response, exchangeRateResponse);
    }
}
