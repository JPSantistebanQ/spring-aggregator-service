package com.jpsantq.aggregator.dto;

import com.jpsantq.aggregator.domain.Ticker;
import com.jpsantq.aggregator.domain.TradeAction;

public record TradeRequest(Ticker ticker,
                           TradeAction action,
                           Integer quantity) {
}
