package com.nosto.exchanger.feign;

import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "exchange-rate-api-client", url = "${exchangeRatesApiUrl}", fallback = ExchangeRatesResponse.class)
public interface ExchangeRatesFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/latest", produces = "application/json")
    ExchangeRatesResponse getExchangeRates();
}
