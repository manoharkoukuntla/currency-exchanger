package com.nosto.exchanger.config;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@TestConfiguration
public class MockRedisServer {

    private final RedisServer redisServer;

    public MockRedisServer(RedisConfiguration configuration) throws IOException {
        this.redisServer = new RedisServer(configuration.getPort());
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