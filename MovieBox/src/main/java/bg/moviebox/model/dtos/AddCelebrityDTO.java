package bg.moviebox.model.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddCelebrityDTO(
        Long id,

        @NotEmpty(message = "{add.celebrity.name.length}")
        @Size(message = "{add.celebrity.name.length}", min = 1, max = 200)
        String name,

        @NotEmpty(message = "{empty_field_message}")
        String imageUrl,

        @NotNull(message = "{add.celebrity.biography.length}")
        @Size(message = "{add.celebrity.biography.length}", min = 20, max = 5000)
        String biography) {

    public static AddCelebrityDTO empty() {
        return new AddCelebrityDTO(null,null, null, null);
    }
}
