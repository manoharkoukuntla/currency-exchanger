package com.nosto.exchanger.config;

import com.github.fppt.jedismock.RedisServer;
import org.springframework.boot.test.context.TestConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@TestConfiguration
public class TestRedisConnection {

    private final RedisServer redisServer;


    public TestRedisConnection() throws IOException {
        this.redisServer = RedisServer.newRedisServer();
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