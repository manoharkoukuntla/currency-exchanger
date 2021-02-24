package com.nosto.exchanger.controllers;


import com.nosto.exchanger.exceptions.CurrencyExchangeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ExceptionController.class)
public class ExceptionControllerTest {

    @Autowired
    private ExceptionController exceptionController;

    @Test
    public void should_return_error_when_internal_server_error() {
        ResponseEntity<Object> response = exceptionController.handleInternalServerError(new Exception());

        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void should_return_error_when_CurrencyExchangeException() {
        ResponseEntity<Object> response = exceptionController.handleCurrencyExchangeException(new CurrencyExchangeException());

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void should_return_error_when_NoHandlerFoundException() {
        ResponseEntity<Object> response = exceptionController.handlerNotFoundException(new NoHandlerFoundException("GET", "DUMMY", HttpHeaders.EMPTY));

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
