package com.nosto.exchanger.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CacheConfig {
    public static final String EXCHANGE_RATES = "EXCHANGE_RATES";

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisConfiguration);
    }

    @CacheEvict(allEntries = true, value = {EXCHANGE_RATES})
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void clearExchangeRates() {}
}
