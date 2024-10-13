package com.jpsantq.aggregator.dto;

import com.jpsantq.aggregator.domain.Ticker;

public record StockPriceResponse(Ticker ticker,
                                 Integer price) {
}
