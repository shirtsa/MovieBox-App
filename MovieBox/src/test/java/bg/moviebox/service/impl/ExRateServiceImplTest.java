package bg.moviebox.service.impl;

import bg.moviebox.config.ForexApiConfig;
import bg.moviebox.model.entities.ExchangeRateEntity;
import bg.moviebox.repository.ExchangeRateRepository;
import bg.moviebox.service.exception.ApiObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExRateServiceImplTest {

  private static final class TestRates {
    // LEV -> base
    // CUR1 -> 4
    // CUR2 -> 0.5
    private static final String BASE_CURRENCY = "LEV";

    private static final ExchangeRateEntity CUR1 = new ExchangeRateEntity()
            .setCurrency("CUR1").setRate(new BigDecimal("4"));

    private static final ExchangeRateEntity CUR2 = new ExchangeRateEntity()
            .setCurrency("CUR2").setRate(new BigDecimal("0.5"));
  }

  private ExchangeRateServiceImpl toTest;

  /* Mockito tries to enforce strict validation, ensuring that unused stubs
   (methods mocked but never called) cause test failures.
   @Mock(strictness = Strictness.LENIENT) prevent parameterized tests to fail
   where not every stubbed method is always used. */
  @Mock(strictness = Strictness.LENIENT)
  private ExchangeRateRepository mockRepository;

  @BeforeEach
  void setUp() {
    toTest = new ExchangeRateServiceImpl(
        mockRepository,
        null,
            new ForexApiConfig().setBase(TestRates.BASE_CURRENCY)
    );
  }

  // 1 LEV ->   4 CUR1
  // 1 LEV -> 0.5 CUR2
  @ParameterizedTest(name = "Converting {2} {0} to {1}. Expected {3}")
  @CsvSource(
          textBlock = """
          LEV, CUR1, 1, 4.00
          LEV, CUR1, 2, 8.00
          LEV, LEV,  1, 1
          CUR1,CUR2, 1, 0.12
          CUR2,CUR1, 1, 8.00
          EUR, EUR, 1, 1
          """
  )
  void testConvert(String from,  //Argument 0
                   String to, //Argument 1
                   BigDecimal amount, //Argument 2
                   BigDecimal expected)  { //Argument 3

//    System.out.println(from + " " + to + " " + amount + " " + expected);

    initExRates();

    BigDecimal result = toTest.convert(from, to, amount);
    Assertions.assertEquals(expected, result);
  }

  @Test
  void testApiObjectNotFoundException() {
    Assertions.assertThrows(ApiObjectNotFoundException.class,
            () -> toTest.convert("DOESN'T_EXIST_1", "DOESN'T_EXIST_2", BigDecimal.ONE)
    );
  }


  private void initExRates() {
    when(mockRepository.findByCurrency(TestRates.CUR1.getCurrency()))
            .thenReturn(Optional.of(TestRates.CUR1));
    when(mockRepository.findByCurrency(TestRates.CUR2.getCurrency()))
            .thenReturn(Optional.of(TestRates.CUR2));
  }

  @Test
  void testHasInitializedExRates() {
    when(mockRepository.count()).thenReturn(0L);
    Assertions.assertFalse(toTest.hasInitializedExRates());

    when(mockRepository.count()).thenReturn(-5L);
    Assertions.assertFalse(toTest.hasInitializedExRates());

    when(mockRepository.count()).thenReturn(6L);
    Assertions.assertTrue(toTest.hasInitializedExRates());
  }
}
