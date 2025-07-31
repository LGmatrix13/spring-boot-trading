package com.myapp.desk.resource;

import com.myapp.desk.domain.Instrument;
import com.myapp.desk.service.InstrumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class InstrumentResource {
    private static final Logger logger = LoggerFactory.getLogger(InstrumentResource.class);
    private final InstrumentService instrumentService;

    public InstrumentResource(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping("/api/instruments")
    public Iterable<Instrument> getInstruments() {
        logger.info("Fetching all instruments");
        return this.instrumentService.getAll();
    }

    @GetMapping("/api/instrument/{id}")
    public Instrument getInstrument(@PathVariable("id") Long id) {
        logger.info("Fetching instrument");
        return this.instrumentService.getById(id);
    }

    @DeleteMapping("/api/instrument/{id}")
    public Instrument deleteInstrument(@PathVariable("id") Long id) {
        logger.info("Deleting instrument");
        return this.instrumentService.deleteById(id);
    }

    @PostMapping("/api/instrument")
    public Instrument postInstrument(Instrument instrument) {
        logger.info("Posting a new instrument: {}", instrument);
        return this.instrumentService.create(instrument);
    }
}
