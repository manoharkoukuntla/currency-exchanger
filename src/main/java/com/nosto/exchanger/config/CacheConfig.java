package com.nosto.exchanger.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CacheConfig {
    public static final String EXCHANGE_RATES = "EXCHANGE_RATES";

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager(EXCHANGE_RATES);
        return RedisCacheManager
                .builder(redisConnectionFactory).build();
    }


    @CacheEvict(allEntries = true, value = {EXCHANGE_RATES})
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void clearExchangeRates() {}
}
