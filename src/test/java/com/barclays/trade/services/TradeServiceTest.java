package com.barclays.trade.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.barclays.trade.exception.CustomException;
import com.barclays.trade.model.Trade;
import com.barclays.trade.model.TradeId;
import com.barclays.trade.repository.TradeRepository;

/**
 * @author Mihir
 * Created test class to test trade service logic.
 */
public class TradeServiceTest {

	@InjectMocks
	TradeService tradeService;

	@Mock
	TradeRepository tradeRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	/**
	 * Test get all trade method logic.
	 */
	@Test
	public void testFindAllTradess() {
		List<Trade> tradeList = new ArrayList<Trade>();
		TradeId tradeId = new TradeId("T1", 1);
		Trade tradeOne = new Trade(tradeId, "c1", "b1", new Date(), Boolean.FALSE);

		TradeId tradeIdTwo = new TradeId("T2", 1);
		Trade tradeTwo = new Trade(tradeIdTwo, "C2", "B2", new Date(), Boolean.FALSE);
		tradeService.saveTrade(tradeTwo);

		tradeList.add(tradeOne);
		tradeList.add(tradeTwo);

		when(tradeRepository.findAll()).thenReturn(tradeList);

		// test
		List<Trade> empList = tradeService.findAll();

		assertEquals(2, empList.size());
		verify(tradeRepository, times(1)).findAll();
	}

	/**
	 * Test trade create logic.
	 */
	@Test
	public void testSaveTrade() {
		TradeId tradeId = new TradeId("T1", 1);
		Trade trade = new Trade(tradeId, "C1", "B1", new Date(), Boolean.FALSE);
		tradeService.saveTrade(trade);

		verify(tradeRepository, times(1)).save(trade);
	}

	/**
	 * Test maturity date validation logic while creating and updating trade
	 * record.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testSaveTradeMaturityDateException() throws ParseException {
		TradeId tradeId = new TradeId("T1", 1);
		Date maturityDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-17");
		Trade trade = new Trade(tradeId, "C2", "B1", maturityDate, Boolean.FALSE);

		exceptionRule.expect(CustomException.class);
		exceptionRule
				.expectMessage("Maturity date should not be in past. It must be todays date or future date. Trade id : "
						+ trade.getTradeId());
		tradeService.saveTrade(trade);
	}

	/**
	 * Test version control logic of trade record.
	 */
	@Test
	public void testSaveTradeOldVersionException() {
		TradeId tradeId = new TradeId("T1", 1);
		Trade trade = new Trade(tradeId, "C1", "B1", new Date(), Boolean.FALSE);
		tradeService.saveTrade(trade);

		given(tradeRepository.findTop1ByTradeIdTradeIdOrderByTradeIdVersionDesc(tradeId.getTradeId()))
				.willReturn(Optional.of(trade));

		TradeId tradeIdNew = new TradeId("T1", 0);
		Trade tradeNew = new Trade(tradeIdNew, "C1", "B1", new Date(), Boolean.FALSE);

		exceptionRule.expect(CustomException.class);
		exceptionRule.expectMessage(
				"Row was updated or deleted by another transaction! Trade id : " + tradeNew.getTradeId());
		tradeService.saveTrade(tradeNew);
	}
}
