package com.jpsantq.aggregator.dto;

import com.jpsantq.aggregator.domain.Ticker;
import com.jpsantq.aggregator.domain.TradeAction;

public record StockTradeRequest(Ticker ticker,
                                Integer price,
                                Integer quantity,
                                TradeAction action) {
}
