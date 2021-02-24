package com.nosto.exchanger.controllers;

import com.nosto.exchanger.exceptions.CurrencyExchangeException;
import com.nosto.exchanger.feign.payloads.responses.ExchangeRatesResponse;
import com.nosto.exchanger.payloads.request.CurrencyExchangeRequest;
import com.nosto.exchanger.payloads.response.CurrencyExchangeResponse;
import com.nosto.exchanger.services.CurrencyExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRatesController.class)
public class ExceptionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    private String source = "INR";
    private String target = "EUR";
    private Float sourceValue = 200F;
    private ExchangeRatesResponse exchangeRates = new ExchangeRatesResponse();
    private CurrencyExchangeRequest request = new CurrencyExchangeRequest();

//    @Test
//    public void should_return_error_when_getExchangeRates_when_CurrencyExchangeException_is_thrown() throws Exception {
//        given(currencyExchangeService.getExchangeRates()).willReturn(exchangeRates);
//        when(currencyExchangeService.getExchangeValue(request, exchangeRates)).thenThrow(new CurrencyExchangeException());
//
//        mvc.perform(get("/api/convert?source=" + source + "&target=" + target + "&value=" + sourceValue)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is5xxServerError())
//                .andExpect(jsonPath("$.success", is(false)));
//    }
}
