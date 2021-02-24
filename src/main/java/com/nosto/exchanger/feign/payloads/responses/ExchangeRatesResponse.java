package com.nosto.exchanger.feign.payloads.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
public class ExchangeRatesResponse implements Serializable {
    private HashMap<String, Float> rates;
    private String base;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
}
