package com.nosto.exchanger.controllers;

import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import com.nosto.exchanger.payloads.request.CurrencyExchangeRequest;
import com.nosto.exchanger.payloads.response.CurrencyExchangeResponse;
import com.nosto.exchanger.services.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class ExchangeRatesController extends BaseController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @GetMapping("/convert")
    public CurrencyExchangeResponse getExchangeRates(@Valid CurrencyExchangeRequest request) {
        ExchangeRatesResponse exchangeRatesResponse = currencyExchangeService.getExchangeRates();
        return currencyExchangeService.getExchangeValue(request, exchangeRatesResponse);
    }
}
