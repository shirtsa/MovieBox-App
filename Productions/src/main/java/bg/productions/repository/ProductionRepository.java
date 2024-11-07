package bg.productions.repository;

import bg.productions.model.entities.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Production o WHERE o.created < :olderThan")
//    void deleteOldOffers(Instant olderThan);
}
