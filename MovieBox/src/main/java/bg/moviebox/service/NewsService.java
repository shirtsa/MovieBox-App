package bg.moviebox.service;

import bg.moviebox.model.dtos.AddNewsDTO;
import bg.moviebox.model.dtos.NewsDetailsDTO;
import bg.moviebox.model.dtos.NewsSummaryDTO;

import java.util.List;

public interface NewsService {

    void createOrUpdateNews(AddNewsDTO addNewsDTO);

    void deleteNews(Long newsId);

    void cleanupOldNews();

    List<NewsSummaryDTO> getAllNewsSummary();

    NewsDetailsDTO getNewsDetails(Long id);

    List<NewsSummaryDTO> getNewsWithNewsTypeComingSoon();

}
