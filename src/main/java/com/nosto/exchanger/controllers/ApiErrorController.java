package com.nosto.exchanger.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Slf4j
@Hidden
@RestController
@RequestMapping("/error")

public class ApiErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";
    private ErrorAttributes errorAttributes;

    @Autowired
    public ApiErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Deprecated
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> error(WebRequest request){
        ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE,
                ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.EXCEPTION);

        Map<String, Object> body = errorAttributes.getErrorAttributes(request, options);
        log.error("error of request: {} errors: {}", request, body);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
