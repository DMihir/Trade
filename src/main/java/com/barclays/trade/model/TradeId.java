package com.barclays.trade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mihir
 *
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "trade_id")
	private String tradeId;

	@Column(name = "version")
	private Integer version;

}