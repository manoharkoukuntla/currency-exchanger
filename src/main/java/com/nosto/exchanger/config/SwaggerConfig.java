package com.nosto.exchanger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Currency exchange converter API",
                version = "v1",
                description = "This app provides REST APIs for converting currencies. " +
                        "App uses <a href='https://api.exchangeratesapi.io/latest'>https://api.exchangeratesapi.io</a> as source of exchange values.",
                contact = @Contact(
                        name = "Manohar",
                        email = "manoharkoukuntla3@gmail.com"
                )
        )
)
public class SwaggerConfig {
}
