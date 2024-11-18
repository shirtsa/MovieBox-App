package bg.moviebox.model.dtos;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRatesDTO(
        String base,
        Map<String,
        BigDecimal> rates) {


    /*
 {
  "base": "USD",
  "rates": {
    ...
    "BGN": 1.8266,
    ....
    "EUR": 0.934216,
     ...
  }
}
 */
}
