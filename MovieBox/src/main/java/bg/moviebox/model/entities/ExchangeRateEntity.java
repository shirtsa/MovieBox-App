package bg.moviebox.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRateEntity extends BaseEntity {

  @NotEmpty
  @Column(unique = true)
  private String currency;

  @Positive
  @NotNull
  private BigDecimal rate;

  public String getCurrency() {
    return currency;
  }

  public ExchangeRateEntity setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public ExchangeRateEntity setRate(BigDecimal rate) {
    this.rate = rate;
    return this;
  }

  @Override
  public String toString() {
    return "ExRateEntity{" +
        "currency='" + currency + '\'' +
        ", rate=" + rate +
        '}';
  }
}
