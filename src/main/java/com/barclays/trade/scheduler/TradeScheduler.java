package com.barclays.trade.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.barclays.trade.model.Trade;
import com.barclays.trade.repository.TradeRepository;
import com.barclays.trade.services.TradeService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Mihir
 *
 */
@Component
@ConditionalOnProperty(name = "trade_scheduler", havingValue = "true")
@Log4j2
public class TradeScheduler {

	@Autowired
	TradeRepository tradeRepository;

	@Autowired
	TradeService tradeService;

	/**
	 * This scheduler will execute every day 12AM and will fetch all the
	 * available expired records and mark it as expired by updating its expired
	 * flag.
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void tradeExpiredScheduler() {
		Optional<List<Trade>> tradeList = tradeRepository.findByMaturityDateLessThanAndExpired(new Date(),
				Boolean.FALSE);
		if (tradeList.isPresent() && !tradeList.get().isEmpty()) {

			for (Trade trade : tradeList.get()) {
				try {
					trade.setExpired(Boolean.TRUE);
					tradeRepository.save(trade);
				} catch (RuntimeException e) {
					log.error("Error while updating Trade from TradeScheduler. Trade Id : " + trade.getTradeId(), e);
				}
			}
		}
	}
}
