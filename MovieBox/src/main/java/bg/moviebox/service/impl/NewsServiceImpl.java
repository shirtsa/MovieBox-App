package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.AddNewsDTO;
import bg.moviebox.model.dtos.NewsDetailsDTO;
import bg.moviebox.model.dtos.NewsSummaryDTO;
import bg.moviebox.model.entities.News;
import bg.moviebox.model.enums.NewsType;
import bg.moviebox.repository.NewsRepository;
import bg.moviebox.service.NewsService;
import bg.moviebox.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Period;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final Logger LOGGER = LoggerFactory.getLogger(NewsServiceImpl.class);
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final Period retentionPeriod;

    public NewsServiceImpl(NewsRepository newsRepository,
                           ModelMapper modelMapper,
                           @Value("${news.retention.period}") Period retentionPeriod) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
        this.retentionPeriod = retentionPeriod;
    }

    @Override
    public void createOrUpdateNews(AddNewsDTO addNewsDTO) {
        if (addNewsDTO.id() != null) {
            // Fetch existing news from the repository by id
            News existingNews = newsRepository.findById(addNewsDTO.id())
                    .orElseThrow(() -> new ObjectNotFoundException("News not found!", addNewsDTO.id()));

            // Update the existing news with the values from the DTO
            existingNews.setName(addNewsDTO.name());
            existingNews.setFirstImageUrl(addNewsDTO.firstImageUrl());
            existingNews.setSecondImageUrl(addNewsDTO.secondImageUrl());
            existingNews.setTrailerUrl(addNewsDTO.trailerUrl());
            existingNews.setDescription(addNewsDTO.description());
            existingNews.setNewsType(NewsType.valueOf(addNewsDTO.newsType()));

            // Save the updated news entity
            newsRepository.save(existingNews);
        } else {
            newsRepository.save(map(addNewsDTO)); // Create new news
        }
    }

    @Override
    public void deleteNews(Long newsId) {
        newsRepository.deleteById(newsId);
    }

    @Override
    public void cleanupOldNews() {
        Instant now = Instant.now();
        Instant deleteBefore = now.minus(retentionPeriod);
        LOGGER.info("Removing all news older than {}", deleteBefore);
        newsRepository.deleteOldNews(deleteBefore);
        LOGGER.info("Old news were removed");
    }

    @Override
    public List<NewsSummaryDTO> getAllNewsSummary() {
        return newsRepository
                .findAll()
                .stream()
                .map(NewsServiceImpl::toNewsSummary)
                .toList();
    }

    @Override
    public NewsDetailsDTO getNewsDetails(Long id) {
        return this.newsRepository
                .findById(id)
                .map(this::toNewsDetailsDTO)
                .orElseThrow(() -> new ObjectNotFoundException("News not found!", id));
    }

    @Override
    public List<NewsSummaryDTO> getNewsWithNewsTypeComingSoon() {
        return newsRepository
                .findByNewsTypeOrderByIdDesc(NewsType.COMING_SOON)
                .stream().limit(1)
                .toList();
    }

    private static NewsSummaryDTO toNewsSummary(News news) {
        return new NewsSummaryDTO(
                news.getId(),
                news.getName(),
                news.getCreated(),
                news.getFirstImageUrl(),
                news.getSecondImageUrl(),
                news.getTrailerUrl(),
                news.getDescription(),
                news.getNewsType()
        );
    }

    private NewsDetailsDTO toNewsDetailsDTO(News news) {
        return new NewsDetailsDTO(
                news.getId(),
                news.getName(),
                news.getCreated(),
                news.getFirstImageUrl(),
                news.getSecondImageUrl(),
                news.getTrailerUrl(),
                news.getDescription(),
                news.getNewsType());
    }

    private News map(AddNewsDTO addNewsDTO) {
        return modelMapper.map(addNewsDTO, News.class);
    }
}
