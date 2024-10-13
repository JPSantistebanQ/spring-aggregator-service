package com.jpsantq.aggregator.dto;

import com.jpsantq.aggregator.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}
