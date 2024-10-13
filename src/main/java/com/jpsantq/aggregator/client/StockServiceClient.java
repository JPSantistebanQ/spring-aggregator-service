package com.jpsantq.aggregator.client;

import com.jpsantq.aggregator.domain.Ticker;
import com.jpsantq.aggregator.dto.PriceUpdate;
import com.jpsantq.aggregator.dto.StockPriceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

@Log4j2
public class StockServiceClient {
    private final WebClient client;
    private Flux<PriceUpdate> flux;

    public StockServiceClient(WebClient client) {
        this.client = client;
    }

    public Mono<StockPriceResponse> getStockPrice(Ticker ticker) {
        return this.client.get()
                .uri("/stock/{ticker}", ticker)
                .retrieve().bodyToMono(StockPriceResponse.class);
    }

    public Flux<PriceUpdate> priceUpdatesStream() {
        if (Objects.isNull(flux))
            this.flux = this.getPriceUpdates();
        return this.flux;
    }

    private Flux<PriceUpdate> getPriceUpdates() {
        return this.client.get()
                .uri("/stock/price-stream")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(PriceUpdate.class)
                .retryWhen(this.retry())
                .cache(1);
    }

    private Retry retry() {
        return Retry.fixedDelay(100, Duration.ofSeconds(1))
                .doBeforeRetry(s -> log.error("Stock service price stream call failed. retrying {}", s.failure().getMessage()));
    }
}
