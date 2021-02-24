package com.nosto.exchanger.controllers;


import com.nosto.exchanger.exceptions.CurrencyExchangeException;
import com.nosto.exchanger.payloads.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(
            BindException ex) {

        List<String> details = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            details.add(fieldName + " " + errorMessage);
        });
        BaseResponse error = new BaseResponse(false, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(
            Exception ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        BaseResponse error = new BaseResponse(false, details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CurrencyExchangeException.class)
    public ResponseEntity<Object> handleCurrencyExchangeException(
            CurrencyExchangeException ex) {

        BaseResponse error = new BaseResponse(false, ex.getErrors());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(
            NoHandlerFoundException ex) {

        BaseResponse error = new BaseResponse(false, Arrays.asList("resource not found"));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
}
