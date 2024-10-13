package com.jpsantq.aggregator.controller;

import com.jpsantq.aggregator.client.StockServiceClient;
import com.jpsantq.aggregator.dto.PriceUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Log4j2
@RestController
@RequestMapping("stock")
public class StockPriceStreamController {

    private final StockServiceClient stockServiceClient;

    public StockPriceStreamController(StockServiceClient stockServiceClient) {
        this.stockServiceClient = stockServiceClient;
    }

    @GetMapping(value = "price-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PriceUpdate> priceUpdatesStream() {
        return this.stockServiceClient.priceUpdatesStream();
    }
}
