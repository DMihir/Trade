package com.barclays.trade;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.barclays.trade.controller.TradeController;

/**
 * @author Mihir
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class ApplicationRunTest {

	@Autowired
	TradeController tradeController;

	/**
	 * Validate context is loaded or not.
	 */
	@Test
	public void contextLoads() {
		Assert.assertNotNull(tradeController);
	}

}
