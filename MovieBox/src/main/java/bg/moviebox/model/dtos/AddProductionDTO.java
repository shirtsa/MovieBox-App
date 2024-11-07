package bg.moviebox.model.dtos;

import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.ProductionType;
import jakarta.validation.constraints.*;

public record AddProductionDTO(
        Long id,
        @NotEmpty(message = "{add.production.name.length}")
        @Size(message = "{add.production.name.length}", min = 1, max = 200)
        String name,
        @NotEmpty(message = "{empty_field_message}")
        String imageUrl,
        @NotEmpty(message = "{empty_field_message}")
        String videoUrl,
        @NotNull(message = "{empty_field_message}")
        @Positive(message = "Released year is required and should be at least 1906.")
        @Min(value = 1906, message = "Released year must be between 1906 and 2024.")
        @Max(value = 2024, message = "Released year must be between 1906 and 2024.")
        Integer year,
        @NotNull(message = "{empty_field_message}")
        @Positive
        Integer length,
        @PositiveOrZero(message = "Rating must be zero or positive.")
        @NotNull(message = "Rating must be zero or positive.")
        Integer rating,

        @PositiveOrZero
        @NotNull(message = "{empty_field_message}")
        Integer rentPrice,
        @NotEmpty(message = "{add.production.description.length}")
        @Size(message = "{add.production.description.length}", min = 50, max = 5000)
        String description,

        @NotNull(message = "Production type is required!")
        ProductionType productionType,

        @NotNull(message = "Genre type is required!")
        Genre genre) {

    public static AddProductionDTO empty() {
        return new AddProductionDTO(null, null, null, null, null, null, null, null, null, null, null);
    }
}
