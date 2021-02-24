package com.nosto.exchanger;

import com.nosto.exchanger.config.MockRedisServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {MockRedisServer.class})
class CurrencyExchangerApplicationTests {

	@Test
	void contextLoads() {
	}

}
