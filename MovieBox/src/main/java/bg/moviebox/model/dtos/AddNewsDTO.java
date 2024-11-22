package bg.moviebox.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddNewsDTO(

        Long id,

        @NotBlank(message = "{add.news.name.length}")
        @Size(message = "{add.news.name.length}", min = 5, max = 100)
        String name,

        @NotBlank(message = "{empty_field_message}")
        String firstImageUrl,

        @NotBlank(message = "{empty_field_message}")
        String secondImageUrl,

        @NotBlank(message = "{empty_field_message}")
        String trailerUrl,

        @NotBlank(message = "{add.news.description.length}")
        @Size(message = "{add.news.description.length}", min = 50, max = 5000)
        String description,

        @NotNull(message = "{empty_field_message}")
        String newsType) {

    public static AddNewsDTO empty() {
        return new AddNewsDTO(null, null, null, null, null, null, null);
    }
}
