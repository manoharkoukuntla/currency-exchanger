package com.nosto.exchanger.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;

@Data
@AllArgsConstructor
public class CurrencyExchangeResponse extends BaseResponse {
    public CurrencyExchangeResponse(){
        super(true, Collections.emptyList());
    }

    private String source;
    private String target;
    private Float sourceValue;
    private Float targetValue;
}
