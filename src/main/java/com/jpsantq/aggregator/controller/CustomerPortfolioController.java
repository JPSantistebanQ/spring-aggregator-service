package com.jpsantq.aggregator.controller;

import com.jpsantq.aggregator.dto.CustomerInformation;
import com.jpsantq.aggregator.dto.StockTradeResponse;
import com.jpsantq.aggregator.dto.TradeRequest;
import com.jpsantq.aggregator.service.CustomerPortfolioService;
import com.jpsantq.aggregator.validator.RequestValidator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerPortfolioController {
    private final CustomerPortfolioService customerPortfolioService;

    public CustomerPortfolioController(CustomerPortfolioService portfolioService) {
        this.customerPortfolioService = portfolioService;
    }

    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId) {
        return this.customerPortfolioService.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable Integer customerId, @RequestBody Mono<TradeRequest> mono) {
        return mono.transform(RequestValidator.validate())
                .flatMap(req -> this.customerPortfolioService.trade(customerId, req));
    }
}
