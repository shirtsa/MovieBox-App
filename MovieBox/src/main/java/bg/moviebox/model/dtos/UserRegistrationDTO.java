package bg.moviebox.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format!")
    private String email;

    @NotEmpty(message = "First Name is required")
    @Size(min = 2, max = 20, message = "Size must be between 2 and 20 symbols!")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    @Size(min = 2, max = 20, message = "Size must be between 2 and 20 symbols!")
    private String lastName;

    @NotEmpty(message = "{empty_field_message}")
    private String password;

    @NotBlank(message = "Confirm Password is required!")
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public UserRegistrationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + (password == null ? "N/A" : "[PROVIDED]") + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
