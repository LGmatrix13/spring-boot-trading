package com.myapp.desk.resource;

import com.myapp.desk.domain.Trade;
import com.myapp.desk.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class TradeResource {
    private static final Logger logger = LoggerFactory.getLogger(TradeResource.class);
    private final TradeService tradeService;

    public TradeResource(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/api/trades")
    public Iterable<Trade> getAll() {
        logger.info("Fetching all trades");
        return this.tradeService.getAll();
    }

    @PostMapping("/api/trade")
    public Trade postTradeInstrumentById(Trade trade) {
        logger.info("Trading instrument");
        return this.tradeService.create(trade);
    }

    @GetMapping("/api/trade/{id}")
    public Trade getTradeById(@PathVariable("id") Long id) {
        logger.info("Fetching trade");
        return this.tradeService.getById(id);
    }

    @DeleteMapping("/api/trade/{id}")
    public Trade deleteTrade(@PathVariable("id") Long id) {
        logger.info("Deleting trade");
        return this.tradeService.deleteById(id);
    }
}
