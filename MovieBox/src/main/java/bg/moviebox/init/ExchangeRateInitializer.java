package bg.moviebox.init;

import bg.moviebox.service.ExchangeRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "forex.init-ex-rates", havingValue = "true")
public class ExchangeRateInitializer implements CommandLineRunner {

  private final ExchangeRateService exRateService;

  public ExchangeRateInitializer(ExchangeRateService exRateService) {
    this.exRateService = exRateService;
  }

  @Override
  public void run(String... args) {
    if (!exRateService.hasInitializedExRates()) {
      exRateService.updateRates(
          exRateService.fetchExRates()
      );
    }
  }
}
