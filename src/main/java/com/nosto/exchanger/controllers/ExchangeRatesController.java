package com.nosto.exchanger.controllers;

import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import com.nosto.exchanger.payloads.request.CurrencyExchangeRequest;
import com.nosto.exchanger.payloads.response.CurrencyExchangeResponse;
import com.nosto.exchanger.services.CurrencyExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Converts given source currency to target currency")
    public ResponseEntity<CurrencyExchangeResponse> getExchangeRates(@Valid CurrencyExchangeRequest request) {
        ExchangeRatesResponse exchangeRatesResponse = currencyExchangeService.getExchangeRates();
        CurrencyExchangeResponse response = currencyExchangeService.getExchangeValue(request, exchangeRatesResponse);

        return ResponseEntity.ok(response);
    }
}
