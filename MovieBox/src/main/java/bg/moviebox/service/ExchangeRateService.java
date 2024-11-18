package bg.moviebox.service;

import bg.moviebox.model.dtos.ExchangeRatesDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    List<String> allSupportedCurrencies();

    boolean hasInitializedExRates();

    ExchangeRatesDTO fetchExRates();

    void updateRates(ExchangeRatesDTO exRatesDTO);

    BigDecimal convert(String from, String to, BigDecimal amount);
}
