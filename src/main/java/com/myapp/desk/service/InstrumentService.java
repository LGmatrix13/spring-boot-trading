package com.myapp.desk.service;

import com.myapp.desk.domain.Instrument;
import com.myapp.desk.respository.InstrumentRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @Cacheable("instruments") // Cache the list of instruments
    public Iterable<Instrument> getAll() {
        return this.instrumentRepository.findAll();
    }

    @Cacheable(value = "instrument", key = "#id") // Cache individual instrument
    public Instrument getById(Long id) {
        return this.instrumentRepository.findById(id).orElse(null);
    }

    @CachePut(value = "instrument", key = "#result.id", condition = "#result != null") // Update cache on create
    @Caching(evict = {
            @CacheEvict(value = "instruments", allEntries = true)
    })
    public Instrument create(Instrument instrument) {
        return this.instrumentRepository.save(instrument);
    }

    @Caching(evict = {
        @CacheEvict(value = "instrument", key = "#id"),
        @CacheEvict(value = "instruments", allEntries = true)
    })
    public Instrument deleteById(Long id) {
        Optional<Instrument> instrument = this.instrumentRepository.findById(id);
        if (instrument.isPresent()) {
            this.instrumentRepository.deleteById(id);
            return instrument.get();
        }
        return null;
    }
}
