package com.nosto.exchanger.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Hidden
@RestController
public class IndexController {

    @GetMapping("/")
    public HashMap<String, Object> index() {
        String message = "Welcome to Currency converter Application. " +
                "Please use `/api/convert` to use application" +
                "pass request parameters `source` source currency, `target` target currency and `value` value of source currency. " +
                "Please open `/documentation` for documentation.";

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        return map;
    }
}
