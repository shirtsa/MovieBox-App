package bg.productions.service.impl;

import bg.productions.service.MonitoringService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final Counter productionSearchCounter;

    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        productionSearchCounter = Counter
                .builder("production.searches")
                .description("How many times productions were searched.")
                .register(meterRegistry);
    }

    @Override
    public void increaseProductionSearches() {
        productionSearchCounter.increment();
    }
}
