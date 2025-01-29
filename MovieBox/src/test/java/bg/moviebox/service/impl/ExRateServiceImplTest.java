package bg.moviebox.service.impl;

import bg.moviebox.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExRateServiceImplTest {

  private ExchangeRateServiceImpl toTest;

  @Mock(strictness = Strictness.LENIENT)
  private ExchangeRateRepository mockRepository;

  @BeforeEach
  void setUp() {
    toTest = new ExchangeRateServiceImpl(
        mockRepository,
        null,
        null
    );
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
