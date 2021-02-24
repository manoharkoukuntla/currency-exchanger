package com.nosto.exchanger.services;


import com.nosto.exchanger.config.MockRedisServer;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {MockRedisServer.class})
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
    private ExchangeRatesResponse exchangeRates;
    private CurrencyExchangeRequest request;
    private CurrencyExchangeResponse response;

    @BeforeEach
    public void setUpRequestAndResponse() {
        request = new CurrencyExchangeRequest();
        response = new CurrencyExchangeResponse();
        exchangeRates = new ExchangeRatesResponse();
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
    public void should_add_base_and_return_exchange_rates_when_getExchangeRates_is_called() {
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        ExchangeRatesResponse exchangeRatesResponse = currencyExchangeService.getExchangeRates();

        assertEquals(exchangeRates, exchangeRatesResponse);
    }

    @Test
    public void should_return_exchange_rates_with_out_base_when_getExchangeRates_is_called() {
        exchangeRates.setRates(new HashMap<>());
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        ExchangeRatesResponse exchangeRatesResponse = currencyExchangeService.getExchangeRates();

        assertEquals(exchangeRates, exchangeRatesResponse);
    }

    @Test
    public void should_return_exchange_rates_value_when_getExchangeValue_is_called_with_request() {
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        CurrencyExchangeResponse exchangeRateResponse = currencyExchangeService.getExchangeValue(request, exchangeRates);

        assertEquals(response.getTargetValue(), exchangeRateResponse.getTargetValue());
    }

    @Test
    public void should_throw_CurrencyExchangeException_when_exchange_is_not_found() {
        request.setSource("DUMMY");
        request.setTarget("DUMMY");
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        try {
           currencyExchangeService.getExchangeValue(request, exchangeRates);
        }catch(Exception e){
            assertNotNull(e);
        }
    }

    @Test
    public void should_throw_CurrencyExchangeException_when_source_exchange_is_not_found() throws Exception {
        request.setSource("DUMMY");
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        try {
            currencyExchangeService.getExchangeValue(request, exchangeRates);
        }catch(Exception e){
            assertNotNull(e);
        }
    }

    @Test
    public void should_throw_CurrencyExchangeException_when_target_exchange_is_not_found() throws Exception {
        request.setTarget("DUMMY");
        when(exchangeRatesFeignClient.getExchangeRates()).thenReturn(exchangeRates);

        try {
            currencyExchangeService.getExchangeValue(request, exchangeRates);
        }catch(Exception e){
            assertNotNull(e);
        }
    }
}
