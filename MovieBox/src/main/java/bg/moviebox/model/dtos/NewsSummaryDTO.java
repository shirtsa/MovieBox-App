package bg.moviebox.model.dtos;

import bg.moviebox.model.enums.NewsType;

import java.time.Instant;

public record NewsSummaryDTO(
        Long id,
        String name,
        Instant created,
        String firstImageUrl,
        String secondImageUrl,
        String trailerUrl,
        String description,
        NewsType newsType
) {
}
