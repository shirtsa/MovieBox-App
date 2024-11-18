package bg.moviebox.init;

import bg.moviebox.service.ExchangeRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
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
