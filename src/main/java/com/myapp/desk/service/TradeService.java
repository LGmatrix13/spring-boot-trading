package com.myapp.desk.service;

import com.myapp.desk.domain.Instrument;
import com.myapp.desk.domain.Trade;
import com.myapp.desk.respository.InstrumentRepository;
import com.myapp.desk.respository.TradeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final InstrumentRepository instrumentRepository;

    public TradeService(
        TradeRepository tradeRepository,
        InstrumentRepository instrumentRepository
    ) {
        this.tradeRepository = tradeRepository;
        this.instrumentRepository = instrumentRepository;
    }

    @Cacheable("trades") // Cache all trades
    public Iterable<Trade> getAll() {
        return this.tradeRepository.findAll();
    }

    @Cacheable(value = "trade", key = "#id") // Cache individual trade
    public Trade getById(Long id) {
        return this.tradeRepository.findById(id).orElse(null);
    }

    @CachePut(value = "trade", key = "#result.id", condition = "#result != null") // Update cache on save
    @Caching(evict = {
            @CacheEvict(value = "trades", allEntries = true)
    })
    public Trade create(Trade trade) {
        Optional<Instrument> instrument = this.instrumentRepository.findById(trade.getInstrumentId());
        if (instrument.isPresent()) {
            return this.tradeRepository.save(trade);
        }
        return null;
    }

    @Caching(evict = {
            @CacheEvict(value = "trades", allEntries = true),
            @CacheEvict(value = "trade", key = "#id")
    })
    public Trade deleteById(Long id) {
        Optional<Instrument> instrument = this.instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            Optional<Trade> optionalTrade = this.tradeRepository.findById(id);
            if (optionalTrade.isPresent()) {
                this.tradeRepository.deleteById(id);
                return optionalTrade.get();
            }
        }
        return null;
    }
}
