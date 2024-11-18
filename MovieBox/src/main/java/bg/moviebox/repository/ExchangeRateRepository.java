package bg.moviebox.repository;

import bg.moviebox.model.entities.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {

  Optional<ExchangeRateEntity> findByCurrency(String currency);

}

