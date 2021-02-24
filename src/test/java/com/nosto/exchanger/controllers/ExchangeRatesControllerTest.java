package com.nosto.exchanger.controllers;

import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import com.nosto.exchanger.payloads.request.CurrencyExchangeRequest;
import com.nosto.exchanger.payloads.response.CurrencyExchangeResponse;
import com.nosto.exchanger.services.CurrencyExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ExchangeRatesController.class)
public class ExchangeRatesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    private String source = "INR";
    private String target = "EUR";
    private Float sourceValue = 200F;
    private Float targetValue = 1.5F;
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
    }

    @Test
    public void should_return_exchange_rate_when_getExchangeRates() throws Exception {
        given(currencyExchangeService.getExchangeRates()).willReturn(exchangeRates);
        given(currencyExchangeService.getExchangeValue(request, exchangeRates)).willReturn(response);

        mvc.perform(get("/api/convert?source=" + source + "&target=" + target + "&value=" + sourceValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.targetValue", is(Double.valueOf(targetValue))))
                .andExpect(jsonPath("$.sourceValue", is(Double.valueOf(sourceValue))))
                .andExpect(jsonPath("$.success", is(true)));

    }

    @Test
    public void should_return_error_when_getExchangeRates_called_without_all_params() throws Exception {
        given(currencyExchangeService.getExchangeRates()).willReturn(exchangeRates);
        given(currencyExchangeService.getExchangeValue(request, exchangeRates)).willReturn(response);

        mvc.perform(get("/api/convert?source=" + source + "&target=" + target)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)));

    }
}
