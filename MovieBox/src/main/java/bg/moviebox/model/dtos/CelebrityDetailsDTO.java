package bg.moviebox.model.dtos;

public record CelebrityDetailsDTO(
        Long id,
        String name,
        String imageUrl,
        String biography) {
}
