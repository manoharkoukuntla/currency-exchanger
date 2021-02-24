package com.nosto.exchanger.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class RedisConfiguration extends RedisStandaloneConfiguration {

    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String redisPassword;

    public RedisPassword getPassword() {
        return RedisPassword.of(redisPassword);
    }
}
