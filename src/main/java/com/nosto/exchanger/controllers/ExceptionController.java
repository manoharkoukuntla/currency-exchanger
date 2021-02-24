package com.nosto.exchanger.controllers;


import com.nosto.exchanger.exceptions.CurrencyExchangeException;
import com.nosto.exchanger.payloads.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(HttpServletRequest request,
                                                             BindException ex) {

        List<String> details = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            details.add(fieldName + " " + errorMessage);
        });
        log.error("error of request: {} {} errors: {}", request.getRequestURI(), request.getQueryString(), details);
        BaseResponse error = new BaseResponse(false, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(
            HttpServletRequest request,
            Exception ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        log.error("error of request: {} {} errors: {}", request.getRequestURI(), request.getQueryString(), details);
        BaseResponse error = new BaseResponse(false, details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CurrencyExchangeException.class)
    public ResponseEntity<Object> handleCurrencyExchangeException(HttpServletRequest request,
            CurrencyExchangeException ex) {

        List<String> details = ex.getErrors();
        BaseResponse error = new BaseResponse(false, details);
        log.error("error of request: {} {} errors: {}", request.getRequestURI(), request.getQueryString(), details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(
            HttpServletRequest request,
            NoHandlerFoundException ex) {

        List<String> details = Arrays.asList("resource not found");
        BaseResponse error = new BaseResponse(false, details);
        log.error("error of request: {} {} errors: {}", request.getRequestURI(), request.getQueryString(), details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
}
