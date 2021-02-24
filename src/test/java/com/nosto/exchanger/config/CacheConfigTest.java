package com.nosto.exchanger.config;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.cache.CacheManager;

import static com.nosto.exchanger.config.CacheConfig.EXCHANGE_RATES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(classes = {MockRedisServer.class})
public class CacheConfigTest {

    @Autowired
    private CacheConfig cacheConfig;

    @Autowired
    private CacheManager cacheManager;

    private String response = "response";
    private String key = "key";

    @BeforeEach
    public void cacheData() {
        cacheManager.getCache(EXCHANGE_RATES).put(key, response);
    }

    @Test
    public void should_cache_data() {
        String cacheValue = cacheManager.getCache(EXCHANGE_RATES).get(key, String.class);

        assertEquals(response, cacheValue);
    }

    @Test
    public void exchange_rates_should_be_removed_from_cache_when_clearExchangeRates_is_called(){
        cacheConfig.clearExchangeRates();

        String cacheValue = cacheManager.getCache(EXCHANGE_RATES).get(key, String.class);

        assertNull(cacheValue);
    }
}
