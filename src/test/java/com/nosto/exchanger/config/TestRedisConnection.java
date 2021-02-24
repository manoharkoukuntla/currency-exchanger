package com.nosto.exchanger.config;

import com.github.fppt.jedismock.RedisServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@TestConfiguration
public class TestRedisConnection {

    private final RedisServer redisServer;

    @Value("${spring.redis.port}")
    private int redisPort;

    public TestRedisConnection() throws IOException {
        this.redisServer = RedisServer.newRedisServer(redisPort);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}