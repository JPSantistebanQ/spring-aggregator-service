package com.jpsantq.aggregator;

import com.jpsantq.aggregator.dto.PriceUpdate;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import reactor.test.StepVerifier;

@Log4j2
class StockPriceStreamTest extends AbstractIntegrationTest {

    @Test
    void priceStream() {
        // * given
        var responseBody = this.resourceToString("stock-service/stock-price-stream-200.jsonl");
        mockServerClient
                .when(HttpRequest.request("/stock/price-stream"))
                .respond(HttpResponse.response(responseBody)
                        .withStatusCode(200)
                        .withContentType(MediaType.parse("application/x-ndjson")));

        this.client.get()
                .uri("/stock/price-stream")
                .accept(org.springframework.http.MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(PriceUpdate.class)
                .getResponseBody()
                .doOnNext(price -> log.info("price: {}", price))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(53, p.price()))
                .assertNext(p -> Assertions.assertEquals(54, p.price()))
                .assertNext(p -> Assertions.assertEquals(55, p.price()))
                .expectComplete()
                .verify();

    }

}
