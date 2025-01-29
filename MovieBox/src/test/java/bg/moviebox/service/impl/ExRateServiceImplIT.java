package bg.moviebox.service.impl;

import bg.moviebox.config.ForexApiConfig;
import bg.moviebox.model.dtos.ExchangeRatesDTO;
import bg.moviebox.service.ExchangeRateService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@SpringBootTest
@EnableWireMock(@ConfigureWireMock(name = "exchange-rate-service"))
public class ExRateServiceImplIT {

  @InjectWireMock("exchange-rate-service")
  private WireMockServer wireMockServer;

  @Autowired
  private ExchangeRateService exchangeRateService;

  @Autowired
  private ForexApiConfig forexApiConfig;

  @BeforeEach
  void setUp() {
    forexApiConfig.setUrl(wireMockServer.baseUrl() + "/test-currencies");
  }

  @Test
  void testFetchExchangeRates() {
    wireMockServer.stubFor(get("/test-currencies").willReturn(
        aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(
                """
                  {
                    "base": "USD",
                    "rates": {
                      "BGN": 1.86,
                      "EUR": 0.91
                    }
                  }
                """
            )
        )
    );

    ExchangeRatesDTO exchangeRatesDTO = exchangeRateService.fetchExRates();

    System.out.println(exchangeRatesDTO);

    Assertions.assertEquals("USD", exchangeRatesDTO.base());
    Assertions.assertEquals(2, exchangeRatesDTO.rates().size());
    Assertions.assertEquals(new BigDecimal("1.86"), exchangeRatesDTO.rates().get("BGN"));
    Assertions.assertEquals(new BigDecimal("0.91"), exchangeRatesDTO.rates().get("EUR"));
  }
}
