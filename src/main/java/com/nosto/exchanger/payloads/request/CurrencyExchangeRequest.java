package com.nosto.exchanger.payloads.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CurrencyExchangeRequest {

    @NotBlank(message = "should be provided")
    private String source;

    @NotBlank(message = "should be provided")
    private String target;

    @NotNull(message = "should be provided")
    private Float value;
}
