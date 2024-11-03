package bg.moviebox.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserLoginDTO(

        @NotNull(message = "{empty_field_message}")
        @Email(message = "Email is required")
        String email,

        @NotEmpty(message = "{empty_field_message}")
        String password) {
}
