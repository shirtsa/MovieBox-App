package bg.moviebox.repository;

import bg.moviebox.model.dtos.NewsSummaryDTO;
import bg.moviebox.model.entities.News;
import bg.moviebox.model.enums.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM News n WHERE n.created < :olderThan")
    void deleteOldNews(Instant olderThan);

    List<NewsSummaryDTO> findByNewsTypeOrderByIdDesc(NewsType newsType);

    List<NewsSummaryDTO> findByNewsTypeOrderByIdAsc(NewsType newsType);
}
