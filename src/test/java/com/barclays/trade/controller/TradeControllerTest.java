package com.barclays.trade.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.barclays.trade.model.Trade;
import com.barclays.trade.model.TradeId;
import com.barclays.trade.services.TradeService;

/**
 * @author Mihir
 *
 */
@RunWith(SpringRunner.class)
public class TradeControllerTest {

	@InjectMocks
	TradeController tradeController;

	@Mock
	TradeService tradeService;

	/**
	 * Test get all trades controller.
	 */
	@Test
	public void getAllTradeTest() {
		// given
		List<Trade> tradeList = new ArrayList<Trade>();
		TradeId tradeId = new TradeId("T1", 1);
		Trade tradeOne = new Trade(tradeId, "C1", "B1", new Date(), Boolean.FALSE);

		TradeId tradeIdTwo = new TradeId("T2", 1);
		Trade tradeTwo = new Trade(tradeIdTwo, "C2", "B2", new Date(), Boolean.FALSE);
		tradeService.saveTrade(tradeTwo);

		tradeList.add(tradeOne);
		tradeList.add(tradeTwo);

		// when
		when(tradeService.findAll()).thenReturn(tradeList);

		ResponseEntity<List<Trade>> resultList = tradeController.getAllTrade();
		// then
		assertEquals(2, resultList.getBody().size());
		assertEquals(tradeOne.getTradeId(), resultList.getBody().get(0).getTradeId());
		assertEquals(tradeTwo.getTradeId(), resultList.getBody().get(1).getTradeId());
		assertEquals(HttpStatus.OK, resultList.getStatusCode());
	}

	/**
	 * Test create trade controller.
	 */
	@Test
	public void createTradeTest() {
		// given
		TradeId tradeId = new TradeId("T1", 1);
		Trade trade = new Trade(tradeId, "C1", "B1", new Date(), Boolean.FALSE);

		// when
		when(tradeService.saveTrade(trade)).thenReturn(trade);

		ResponseEntity<Trade> result = tradeController.createTrade(trade);
		// then
		assertEquals(trade, result.getBody());
		assertEquals(HttpStatus.CREATED, result.getStatusCode());

	}

}
