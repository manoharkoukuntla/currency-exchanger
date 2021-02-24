package com.nosto.exchanger.services;


import com.nosto.exchanger.exceptions.CurrencyExchangeException;
import com.nosto.exchanger.feign.ExchangeRatesFeignClient;
import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import com.nosto.exchanger.payloads.request.CurrencyExchangeRequest;
import com.nosto.exchanger.payloads.response.CurrencyExchangeResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.nosto.exchanger.config.CacheConfig.EXCHANGE_RATES;

@Service
public class CurrencyExchangeService {

    @Autowired
    private ExchangeRatesFeignClient exchangeRatesFeignClient;

    @Cacheable(value = EXCHANGE_RATES, key="'response'", unless = "#result.getRates().size() == 0")
    public ExchangeRatesResponse getExchangeRates() {
        ExchangeRatesResponse exchangeRates = exchangeRatesFeignClient.getExchangeRates();
        if(exchangeRates.getRates().size() > 0)exchangeRates.getRates().put(exchangeRates.getBase(), 1.0F);
        return exchangeRates;
    }

    @SneakyThrows
    public CurrencyExchangeResponse getExchangeValue(CurrencyExchangeRequest request, ExchangeRatesResponse exchangeRates) {
        CurrencyExchangeResponse response = new CurrencyExchangeResponse();
        HashMap<String, Float> rates = exchangeRates.getRates();
        String source = request.getSource();
        String target = request.getTarget();

        if(!rates.containsKey(source) || !rates.containsKey(target)){
            CurrencyExchangeException exception = new CurrencyExchangeException();

            if(!rates.containsKey(source)){
                exception.addError("Did not found source currency exchange value");
            }

            if(!rates.containsKey(target)){
                exception.addError("Did not found target currency exchange value");
            }
            throw exception;
        }


        Float convertedValue = convertCurrency(rates.get(source), rates.get(target), request.getValue());
        response.setSource(source);
        response.setTarget(target);
        response.setSourceValue(request.getValue());
        response.setTargetValue(convertedValue);
        return response;
    }

    private Float convertCurrency(Float source, Float target, Float value){
        return value/source * target;
    }

}
