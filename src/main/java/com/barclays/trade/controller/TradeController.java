package com.barclays.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.trade.model.Trade;
import com.barclays.trade.services.TradeService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Mihir
 * This controller is created to handle Trade request.
 */
@RestController
@RequestMapping("/api/trade")
@Log4j2
public class TradeController {

	@Autowired
	TradeService tradeService;

	/**
	 * It will return all the available trade records in response.
	 * 
	 * @return
	 */
	@GetMapping(value = "/getall", headers = "Accept=application/json")
	public ResponseEntity<List<Trade>> getAllTrade() {
		log.info("TradeController.getAllTrade() -- Start");
		List<Trade> tradeList = tradeService.findAll();
		log.info("TradeController.getAllTrade() -- End");
		if (null == tradeList) {
			return new ResponseEntity<List<Trade>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Trade>>(tradeList, HttpStatus.OK);
	}

	/**
	 * It will handle new trade request or updating existing trade request.
	 * 
	 * @param trade
	 * @return
	 */
	@PostMapping(value = "/create", headers = "Accept=application/json")
	public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) {
		log.info("TradeController.createTrade() -- Start");

		Trade tradeResponse = tradeService.saveTrade(trade);
		log.info("TradeController.createTrade() -- End");
		if (null == tradeResponse) {
			return new ResponseEntity<Trade>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Trade>(tradeResponse, HttpStatus.CREATED);
	}
}
