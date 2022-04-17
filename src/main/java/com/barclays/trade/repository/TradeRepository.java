package com.barclays.trade.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.barclays.trade.model.Trade;
import com.barclays.trade.model.TradeId;

/**
 * @author Mihir
 * Repository is created to serve trade record transaction.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, TradeId> {

	/**
	 * It will fetch all the expired records.
	 * 
	 * @param presentDate
	 * @param expired
	 * @return
	 */
	public Optional<List<Trade>> findByMaturityDateLessThanAndExpired(Date presentDate, Boolean expired);

	/**
	 * It will fetch top most version id for the given trade id and apply
	 * pessimistic lock on it.
	 * 
	 * @param tradeId
	 * @return
	 */
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	public Optional<Trade> findTop1ByTradeIdTradeIdOrderByTradeIdVersionDesc(String tradeId);

}