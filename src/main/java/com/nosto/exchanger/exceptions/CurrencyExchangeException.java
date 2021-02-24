package com.nosto.exchanger.exceptions;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CurrencyExchangeException extends Exception {
    private List<String> errors = new ArrayList<>();

    public void addError(String error) {
        errors.add(error);
    }
}
