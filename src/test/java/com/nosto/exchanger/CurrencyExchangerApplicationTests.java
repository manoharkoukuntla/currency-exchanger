package com.nosto.exchanger;

import com.nosto.exchanger.config.TestRedisConnection;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {TestRedisConnection.class})
class CurrencyExchangerApplicationTests {

	@Test
	void contextLoads() {
	}

}
