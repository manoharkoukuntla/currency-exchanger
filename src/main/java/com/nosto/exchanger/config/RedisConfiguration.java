package com.nosto.exchanger.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RedisConfiguration extends RedisStandaloneConfiguration {

    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String redisPassword;

    public RedisPassword getPassword() {
        if(redisPassword == null || redisPassword.equals(""))return RedisPassword.none();
        return RedisPassword.of(redisPassword);
    }
}
