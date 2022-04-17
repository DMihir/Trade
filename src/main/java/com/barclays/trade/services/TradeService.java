package com.barclays.trade.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barclays.trade.exception.CustomException;
import com.barclays.trade.model.Trade;
import com.barclays.trade.repository.TradeRepository;
import com.barclays.trade.util.Utility;

import lombok.extern.log4j.Log4j2;

/**
 * @author Mihir This service is created to handle trade business logic.
 */
@Service
@Log4j2
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	/**
	 * This method will save new Trade or update the existing Trade.
	 * 
	 * @param trade
	 * @return
	 */
	@Transactional
	public Trade saveTrade(Trade trade) {
		log.info("Inside TradeService.saveTrade()");
		if (Utility.compareDateWithPresentDate(trade.getMaturityDate()) > 0) {
			throw new CustomException(
					"Maturity date should not be in past. It must be todays date or future date. Trade id : "
							+ trade.getTradeId());
		}
		Optional<Trade> t = tradeRepository
				.findTop1ByTradeIdTradeIdOrderByTradeIdVersionDesc(trade.getTradeId().getTradeId());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (t.isPresent() && trade.getTradeId().getVersion() < t.get().getTradeId().getVersion()) {
			throw new CustomException(
					"Row was updated or deleted by another transaction! Trade id : " + trade.getTradeId());
		}
		trade.setExpired(Boolean.FALSE);
		return tradeRepository.save(trade);
	}

	/**
	 * This method will return all the available trade records.
	 * 
	 * @return
	 */
	@Transactional
	public List<Trade> findAll() {
		log.info("Inside TradeService.findAll()");
		return tradeRepository.findAll();
	}

}
