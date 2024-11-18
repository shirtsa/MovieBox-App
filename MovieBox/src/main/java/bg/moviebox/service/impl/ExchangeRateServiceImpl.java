package bg.moviebox.service.impl;

import bg.moviebox.config.ForexApiConfig;
import bg.moviebox.model.dtos.ExchangeRatesDTO;
import bg.moviebox.model.entities.ExchangeRateEntity;
import bg.moviebox.repository.ExchangeRateRepository;
import bg.moviebox.service.ExchangeRateService;
import bg.moviebox.service.exception.ApiObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestClient restClient;
    private final ForexApiConfig forexApiConfig;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository,
                                   @Qualifier("genericRestClient") RestClient restClient,
                                   ForexApiConfig forexApiConfig) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.restClient = restClient;
        this.forexApiConfig = forexApiConfig;
    }

    @Override
    public List<String> allSupportedCurrencies() {
        return exchangeRateRepository
                .findAll()
                .stream()
                .map(ExchangeRateEntity::getCurrency)
                .toList();
    }

    @Override
    public boolean hasInitializedExRates() {
        return exchangeRateRepository.count() > 0;
    }

    @Override
    public ExchangeRatesDTO fetchExRates() {
        return restClient
                .get()
                .uri(forexApiConfig.getUrl(), forexApiConfig.getKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ExchangeRatesDTO.class);
    }

    @Override
    public void updateRates(ExchangeRatesDTO exchangeRatesDTO) {
        LOGGER.info("Updating {} rates.", exchangeRatesDTO.rates().size());

        if (!forexApiConfig.getBase().equals(exchangeRatesDTO.base())) {
            throw new IllegalArgumentException("The exchange rates that should be updated are not based on " +
                    forexApiConfig.getBase() + " but rather on " + exchangeRatesDTO.base());
        }

        exchangeRatesDTO.rates().forEach((currency, rate) -> {
            var exchangeRateEntity = exchangeRateRepository.findByCurrency(currency)
                    .orElseGet(() -> new ExchangeRateEntity().setCurrency(currency));

            exchangeRateEntity.setRate(rate);
            exchangeRateRepository.save(exchangeRateEntity);
        });
    }

    private Optional<BigDecimal> findExRate(String from, String to) {
        if (Objects.equals(from, to)) {
            return Optional.of(BigDecimal.ONE);
        }

        // USD/BGN=x
        // USD/EUR=y

        // USD = x * BGN
        // USD = y * EUR

        // EUR/BGN = x / y

        Optional<BigDecimal> fromOpt = forexApiConfig.getBase().equals(from) ?
                Optional.of(BigDecimal.ONE) :
                exchangeRateRepository.findByCurrency(from).map(ExchangeRateEntity::getRate);

        Optional<BigDecimal> toOpt = forexApiConfig.getBase().equals(to) ?
                Optional.of(BigDecimal.ONE) :
                exchangeRateRepository.findByCurrency(to).map(ExchangeRateEntity::getRate);

        if (fromOpt.isEmpty() || toOpt.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(toOpt.get().divide(fromOpt.get(), 2, RoundingMode.HALF_DOWN));
        }
    }

    @Override
    public BigDecimal convert(String from, String to, BigDecimal amount) {
        return findExRate(from, to)
                .orElseThrow(() -> new ApiObjectNotFoundException(
                        "Conversion from " + from + " to " + to + " not possible!", from + "~" + to))
                .multiply(amount);
    }
}
